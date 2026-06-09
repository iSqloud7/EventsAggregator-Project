<template>
  <div class="theaters-page">
    <div class="container">
      <div class="page-header fade-up-1">
        <h1 class="page-title">THEATER <span class="accent">SHOWS</span></h1>
        <RouterLink v-if="auth.isAdmin || auth.isDeveloper" to="/theaters/add" class="btn btn-primary">+ Add Show</RouterLink>
      </div>

      <TheaterFilters class="fade-up-2" @filter="handleFilter" />

      <div v-if="theaterStore.loading" class="loading-spinner">
        <div class="spinner"></div>
      </div>

      <div v-else-if="theaterStore.error" class="alert alert-error">
        {{ theaterStore.error }}
      </div>

      <template v-else>
        <p class="results-count fade-up">
          {{ filteredShows.length }} show{{ filteredShows.length !== 1 ? 's' : '' }} found
        </p>

        <div v-if="filteredShows.length" class="theaters-grid">
          <TheaterCard
              v-for="(show, i) in filteredShows"
              :key="show.id"
              :show="show"
              :style="{ animationDelay: `${i * 0.05}s` }"
              class="fade-up"
          />
        </div>

        <div v-else class="empty-state">
          <p>No theater shows found matching your filters.</p>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useAuthStore }  from '@/stores/authStore'
import { useTheaterStore } from '@/stores/theaterStore'
import TheaterCard    from '@/components/theater/TheaterCard.vue'
import TheaterFilters from '@/components/theater/TheaterFilters.vue'

const auth = useAuthStore()
const theaterStore = useTheaterStore()

const activeFilters = ref({ keyword: '', city: '', month: '' })

function transliterate(text) {
  const map = {
    'а':'a','б':'b','в':'v','г':'g','д':'d','ѓ':'gj','е':'e',
    'ж':'zh','з':'z','ѕ':'dz','и':'i','ј':'j','к':'k','л':'l',
    'љ':'lj','м':'m','н':'n','њ':'nj','о':'o','п':'p','р':'r',
    'с':'s','т':'t','ќ':'kj','у':'u','ф':'f','х':'h','ц':'c',
    'ч':'ch','џ':'dzh','ш':'sh'
  }
  return text.toLowerCase().split('').map(c => map[c] || c).join('')
}

const filteredShows = computed(() => {
  let list = theaterStore.shows
  const { keyword, city, month } = activeFilters.value

  if (keyword) {
    const q = transliterate(keyword.toLowerCase())
    list = list.filter(s =>
        transliterate(s.title?.toLowerCase() || '').includes(q) ||
        transliterate(s.description?.toLowerCase() || '').includes(q) ||
        transliterate(s.location?.toLowerCase() || '').includes(q)
    )
  }

  if (city) {
    list = list.filter(s =>
        transliterate(s.city?.toLowerCase() || '').includes(transliterate(city.toLowerCase())) ||
        s.city?.toLowerCase().includes(city.toLowerCase())
    )
  }

  if (month) {
    list = list.filter(s => {
      if (!s.dateStart) return false
      const parts = s.dateStart.split('-')
      return parts[1] === month
    })
  }

  return list
})

function handleFilter(filters) {
  activeFilters.value = filters
}

onMounted(() => theaterStore.fetchAll())
</script>

<style scoped>
.theaters-page { padding: 3rem 0 5rem; }
.page-header { display: flex; align-items: flex-end; justify-content: space-between; margin-bottom: 2rem; gap: 1rem; flex-wrap: wrap; }
.accent { color: var(--accent); }
.results-count { font-size: 0.85rem; color: var(--text-muted); margin-bottom: 1.5rem; }
.theaters-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 1.5rem; }
</style>