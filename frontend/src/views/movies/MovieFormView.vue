<template>
  <div class="form-page">
    <div class="container">
      <div class="form-header fade-up-1">
        <RouterLink to="/movies" class="back-link">← Back to Movies</RouterLink>
        <h1 class="page-title">{{ isEdit ? 'EDIT' : 'ADD' }} <span class="accent">MOVIE</span></h1>
      </div>

      <div class="form-card fade-up-2">
        <div v-if="loading" class="loading-spinner"><div class="spinner"></div></div>

        <form v-else class="movie-form" @submit.prevent="handleSubmit">
          <div class="alert alert-error" v-if="error">{{ error }}</div>

          <div class="form-row">
            <div class="form-group">
              <label>Title *</label>
              <input v-model="form.title" type="text" placeholder="Movie title" required />
            </div>
            <div class="form-group">
              <label>Genre</label>
              <input v-model="form.genre" type="text" placeholder="Action, Drama..." />
            </div>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label>Date</label>
              <input v-model="form.dateStart" type="text" placeholder="DD-MM-YYYY" />
            </div>
            <div class="form-group">
              <label>Showtime</label>
              <input v-model="form.timeStart" type="text" placeholder="HH:MM" />
            </div>
            <div class="form-group">
              <label>Duration</label>
              <input v-model="form.duration" type="text" placeholder="120 min" />
            </div>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label>Cinema / Location</label>
              <input v-model="form.location" type="text" placeholder="Cineplexx Skopje" />
            </div>
            <div class="form-group">
              <label>City</label>
              <input v-model="form.city" type="text" placeholder="Скопје" />
            </div>
            <div class="form-group">
              <label>Price</label>
              <input v-model="form.price" type="text" placeholder="350 ден." />
            </div>
          </div>

          <div class="form-group">
            <label>Poster / Image URL</label>
            <input v-model="form.image" type="url" placeholder="https://..." />
          </div>

          <div class="form-group">
            <label>Description</label>
            <textarea v-model="form.description" placeholder="Movie synopsis..." rows="4" />
          </div>

          <div class="form-actions">
            <RouterLink to="/movies" class="btn btn-ghost">Cancel</RouterLink>
            <button type="submit" class="btn btn-primary" :disabled="movieStore.loading">
              {{ movieStore.loading ? 'Saving...' : isEdit ? 'Update Movie' : 'Add Movie' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useMovieStore } from '@/stores/movieStore'

const route      = useRoute()
const router     = useRouter()
const movieStore = useMovieStore()

const isEdit  = computed(() => !!route.params.id)
const loading = computed(() => movieStore.loading && isEdit.value)
const error   = computed(() => movieStore.error)

const form = reactive({ title: '', genre: '', duration: '', timeStart: '', dateStart: '', location: '', city: '', price: '', image: '', description: '' })

onMounted(async () => {
  if (isEdit.value) {
    await movieStore.fetchById(route.params.id)
    const m = movieStore.currentMovie
    if (m) Object.assign(form, { title: m.title||'', genre: m.genre||'', duration: m.duration||'', timeStart: m.timeStart||'', dateStart: m.dateStart||'', location: m.location||'', city: m.city||'', price: m.price||'', image: m.image||'', description: m.description||'' })
  } else {
    movieStore.clearCurrentMovie()
  }
})

async function handleSubmit() {
  const ok = isEdit.value
    ? await movieStore.updateMovie(route.params.id, { ...form })
    : await movieStore.createMovie({ ...form })
  if (ok) router.push('/movies')
}
</script>

<style scoped>
.form-page { padding: 3rem 0 5rem; }
.form-header { margin-bottom: 2rem; }
.back-link { font-size: 0.85rem; color: var(--text-muted); transition: color var(--transition); display: inline-block; margin-bottom: 1rem; }
.back-link:hover { color: var(--accent); }
.accent { color: var(--accent); }
.form-card { background: var(--bg-card); border: 1px solid var(--border); border-radius: 16px; padding: 2rem; max-width: 700px; }
.movie-form { display: flex; flex-direction: column; gap: 0.25rem; }
.form-row { display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 0 1rem; }
.form-actions { display: flex; justify-content: flex-end; gap: 0.75rem; margin-top: 1rem; padding-top: 1.25rem; border-top: 1px solid var(--border); }
</style>
