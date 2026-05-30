<template>
  <div class="detail-page">
    <div v-if="movieStore.loading" class="loading-spinner"><div class="spinner"></div></div>

    <div v-else-if="!movie" class="empty-state">
      <div class="icon">❌</div>
      <p>Movie not found.</p>
      <RouterLink to="/movies" class="btn btn-ghost" style="margin-top:1rem">← Back to Movies</RouterLink>
    </div>

    <template v-else>
      <div class="detail-hero">
        <img v-if="movie.image" :src="movie.image" :alt="movie.title" class="hero-img" />
        <div v-else class="hero-placeholder">🎬</div>
        <div class="hero-overlay"></div>
        <div class="hero-meta container">
          <span class="genre-badge" v-if="movie.genre">{{ movie.genre }}</span>
          <h1 class="page-title movie-title">{{ movie.title }}</h1>
        </div>
      </div>

      <div class="container detail-body">
        <div class="detail-grid">
          <div class="detail-main fade-up-1">
            <h2 class="section-title" style="margin-bottom:1.5rem">ABOUT THIS FILM</h2>
            <p class="description">{{ movie.description || 'No description available.' }}</p>
          </div>

          <aside class="detail-sidebar fade-up-2">
            <div class="info-card">
              <div class="info-row" v-if="movie.genre">
                <span class="info-label">Genre</span>
                <span class="info-value">{{ movie.genre }}</span>
              </div>
              <div class="info-row" v-if="movie.duration">
                <span class="info-label">Duration</span>
                <span class="info-value">{{ movie.duration }}</span>
              </div>
              <div class="info-row" v-if="movie.dateStart">
                <span class="info-label">Date</span>
                <span class="info-value">{{ movie.dateStart }}</span>
              </div>
              <div class="info-row" v-if="movie.timeStart">
                <span class="info-label">Showtime</span>
                <span class="info-value">{{ movie.timeStart }}</span>
              </div>
              <div class="info-row" v-if="movie.location">
                <span class="info-label">Cinema</span>
                <span class="info-value">{{ movie.location }}</span>
              </div>
              <div class="info-row" v-if="movie.city">
                <span class="info-label">City</span>
                <span class="info-value">{{ movie.city }}</span>
              </div>
              <div class="info-row">
                <span class="info-label">Price</span>
                <span class="info-value accent" v-if="movie.price">{{ movie.price }}</span>
                <span class="info-value success" v-else>Free</span>
              </div>
            </div>

            <div class="sidebar-actions" v-if="auth.isAdmin || auth.isDeveloper">
              <RouterLink :to="`/movies/edit/${movie.id}`" class="btn btn-ghost" style="width:100%; justify-content:center">Edit Movie</RouterLink>
              <button class="btn btn-danger" style="width:100%; justify-content:center" @click="showDelete = true">Delete Movie</button>
            </div>

            <RouterLink to="/movies" class="btn btn-ghost" style="width:100%; justify-content:center; margin-top:0.5rem">
              ← Back to Movies
            </RouterLink>
          </aside>
        </div>
      </div>
    </template>

    <AppModal v-model="showDelete" title="DELETE MOVIE">
      <p style="color: var(--text-muted)">Are you sure you want to delete <strong style="color:var(--text)">{{ movie?.title }}</strong>?</p>
      <template #footer>
        <button class="btn btn-ghost btn-sm" @click="showDelete = false">Cancel</button>
        <button class="btn btn-danger btn-sm" @click="handleDelete">Delete</button>
      </template>
    </AppModal>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore }  from '@/stores/authStore'
import { useMovieStore } from '@/stores/movieStore'
import AppModal from '@/components/common/AppModal.vue'

const route      = useRoute()
const router     = useRouter()
const auth       = useAuthStore()
const movieStore = useMovieStore()
const showDelete = ref(false)

const movie = computed(() => movieStore.currentMovie)

async function handleDelete() {
  await movieStore.deleteMovie(movie.value.id)
  router.push('/movies')
}

onMounted(() => movieStore.fetchById(route.params.id))
</script>

<style scoped>
.detail-page { padding-bottom: 5rem; }
.detail-hero { position: relative; height: 420px; overflow: hidden; }
.hero-img { width: 100%; height: 100%; object-fit: cover; object-position: top; }
.hero-placeholder { width: 100%; height: 100%; background: var(--bg-elevated); display: flex; align-items: center; justify-content: center; font-size: 5rem; }
.hero-overlay { position: absolute; inset: 0; background: linear-gradient(to top, var(--bg) 0%, transparent 60%); }
.hero-meta { position: absolute; bottom: 2rem; left: 50%; transform: translateX(-50%); width: 100%; }
.genre-badge { display: inline-block; background: var(--accent); color: var(--bg); font-size: 0.75rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.1em; padding: 0.25rem 0.75rem; border-radius: 100px; margin-bottom: 0.75rem; }
.movie-title { color: var(--text); text-shadow: 0 2px 20px rgba(0,0,0,0.8); }
.detail-body { padding-top: 3rem; }
.detail-grid { display: grid; grid-template-columns: 1fr 320px; gap: 3rem; align-items: start; }
.description { color: var(--text-muted); line-height: 1.8; font-size: 1.05rem; }
.info-card { background: var(--bg-card); border: 1px solid var(--border); border-radius: 12px; overflow: hidden; margin-bottom: 1rem; }
.info-row { display: flex; justify-content: space-between; align-items: center; padding: 0.85rem 1.25rem; border-bottom: 1px solid var(--border); gap: 1rem; }
.info-row:last-child { border-bottom: none; }
.info-label { font-size: 0.75rem; text-transform: uppercase; letter-spacing: 0.08em; color: var(--text-muted); font-weight: 500; flex-shrink: 0; }
.info-value { font-size: 0.95rem; font-weight: 500; text-align: right; }
.accent  { color: var(--accent); }
.success { color: var(--success); }
.sidebar-actions { display: flex; flex-direction: column; gap: 0.5rem; margin-bottom: 0.5rem; }
@media (max-width: 768px) { .detail-grid { grid-template-columns: 1fr; } .detail-hero { height: 260px; } }
</style>
