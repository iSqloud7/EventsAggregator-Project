<template>
  <div class="movies-page">
    <div class="container">
      <div class="page-header fade-up-1">
        <h1 class="page-title">NOW <span class="accent">SHOWING</span></h1>
        <RouterLink v-if="auth.isAdmin || auth.isDeveloper" to="/movies/add" class="btn btn-primary">+ Add Movie</RouterLink>
      </div>

      <MovieFilters class="fade-up-2" @filter="handleFilter" />

      <div v-if="movieStore.loading" class="loading-spinner"><div class="spinner"></div></div>
      <div v-else-if="movieStore.error" class="alert alert-error">{{ movieStore.error }}</div>

      <template v-else>
        <p class="results-count fade-up">
          {{ filteredMovies.length }} movie{{ filteredMovies.length !== 1 ? 's' : '' }} found
        </p>

        <div v-if="filteredMovies.length" class="movies-grid">
          <MovieCard
            v-for="(movie, i) in filteredMovies"
            :key="movie.id"
            :movie="movie"
            :style="{ animationDelay: `${i * 0.05}s` }"
            class="fade-up"
          />
        </div>

        <div v-else class="empty-state">
          <div class="icon">🎬</div>
          <p>No movies found matching your filters.</p>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useAuthStore }  from '@/stores/authStore'
import { useMovieStore } from '@/stores/movieStore'
import MovieCard    from '@/components/movies/MovieCard.vue'
import MovieFilters from '@/components/movies/MovieFilters.vue'

const auth       = useAuthStore()
const movieStore = useMovieStore()
const activeFilters = ref({ keyword: '', genre: '', month: '' })

const filteredMovies = computed(() => {
  let list = movieStore.movies
  const { keyword, genre, month } = activeFilters.value

  if (keyword) {
    const q = keyword.toLowerCase()
    list = list.filter(m => m.title?.toLowerCase().includes(q) || m.description?.toLowerCase().includes(q))
  }
  if (genre) {
    list = list.filter(m => m.genre?.toLowerCase().includes(genre.toLowerCase()))
  }
  if (month) {
    list = list.filter(m => {
      if (!m.dateStart) return false
      const parts = m.dateStart.split('-')
      // поддржува и DD-MM-YYYY и YYYY-MM-DD
      return parts[1] === month || parts[0] === month
    })
  }
  return list
})

function handleFilter(filters) { activeFilters.value = filters }
onMounted(() => movieStore.fetchAll())
</script>

<style scoped>
.movies-page { padding: 3rem 0 5rem; }
.page-header { display: flex; align-items: flex-end; justify-content: space-between; margin-bottom: 2rem; gap: 1rem; flex-wrap: wrap; }
.accent { color: var(--accent); }
.results-count { font-size: 0.85rem; color: var(--text-muted); margin-bottom: 1.5rem; }
.movies-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); gap: 1.5rem; }
</style>
