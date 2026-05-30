<template>
  <div class="home">
    <!-- Hero -->
    <section class="hero">
      <div class="hero-bg">
        <div class="hero-grid"></div>
        <div class="hero-glow"></div>
      </div>
      <div class="container hero-content">
        <p class="hero-eyebrow fade-up-1">Discover Macedonia's Best Events & Movies</p>
        <h1 class="page-title fade-up-2">
          YOUR NEXT<br /><span class="accent">EXPERIENCE</span><br />AWAITS
        </h1>
        <p class="hero-sub fade-up-3">Concerts, festivals, theatre, cinema & more — all in one place.</p>
        <div class="hero-actions fade-up-4">
          <RouterLink to="/events" class="btn btn-primary">Browse Events</RouterLink>
          <RouterLink to="/movies" class="btn btn-ghost">Now Showing 🎬</RouterLink>
        </div>
      </div>
    </section>

    <!-- Stats -->
    <section class="stats-section">
      <div class="container stats-grid">
        <div class="stat-item">
          <span class="stat-number">{{ eventStore.events.length || '20' }}+</span>
          <span class="stat-label">Live Events</span>
        </div>
        <div class="stat-item">
          <span class="stat-number">{{ movieStore.movies.length || '10' }}+</span>
          <span class="stat-label">Movies</span>
        </div>
        <div class="stat-item">
          <span class="stat-number">5+</span>
          <span class="stat-label">Cities</span>
        </div>
        <div class="stat-item">
          <span class="stat-number">100%</span>
          <span class="stat-label">Up to date</span>
        </div>
      </div>
    </section>

    <!-- Latest Events -->
    <section class="latest-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">LATEST <span class="accent">EVENTS</span></h2>
          <RouterLink to="/events" class="btn btn-ghost btn-sm">View all →</RouterLink>
        </div>
        <div v-if="eventStore.loading" class="loading-spinner"><div class="spinner"></div></div>
        <div v-else class="events-preview-grid">
          <EventCard v-for="event in previewEvents" :key="event.id" :event="event" />
        </div>
      </div>
    </section>

    <!-- Now Showing Movies -->
    <section class="movies-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">NOW <span class="accent">SHOWING</span></h2>
          <RouterLink to="/movies" class="btn btn-ghost btn-sm">View all →</RouterLink>
        </div>
        <div v-if="movieStore.loading" class="loading-spinner"><div class="spinner"></div></div>
        <div v-else-if="previewMovies.length" class="movies-preview-grid">
          <MovieCard v-for="movie in previewMovies" :key="movie.id" :movie="movie" />
        </div>
        <div v-else class="empty-state">
          <div class="icon">🎬</div>
          <p>No movies available yet.</p>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useEventStore } from '@/stores/eventStore'
import { useMovieStore } from '@/stores/movieStore'
import EventCard from '@/components/events/EventCard.vue'
import MovieCard from '@/components/movies/MovieCard.vue'

const eventStore = useEventStore()
const movieStore = useMovieStore()

const previewEvents = computed(() => eventStore.events.slice(0, 6))
const previewMovies = computed(() => movieStore.movies.slice(0, 4))

onMounted(() => {
  if (!eventStore.events.length) eventStore.fetchAll()
  if (!movieStore.movies.length) movieStore.fetchAll()
})
</script>

<style scoped>
.hero { position: relative; min-height: 88vh; display: flex; align-items: center; overflow: hidden; }
.hero-bg { position: absolute; inset: 0; pointer-events: none; }
.hero-grid {
  position: absolute; inset: 0;
  background-image: linear-gradient(var(--border) 1px, transparent 1px), linear-gradient(90deg, var(--border) 1px, transparent 1px);
  background-size: 60px 60px; opacity: 0.3;
  mask-image: radial-gradient(ellipse at center, black 30%, transparent 75%);
}
.hero-glow {
  position: absolute; top: 20%; left: 50%; transform: translateX(-50%);
  width: 600px; height: 600px;
  background: radial-gradient(circle, rgba(232,255,71,0.08) 0%, transparent 70%); border-radius: 50%;
}
.hero-content { position: relative; z-index: 1; padding-top: 4rem; padding-bottom: 4rem; }
.hero-eyebrow { font-size: 0.85rem; font-weight: 500; text-transform: uppercase; letter-spacing: 0.14em; color: var(--accent); margin-bottom: 1.5rem; }
.hero-content .page-title { margin-bottom: 1.5rem; max-width: 700px; }
.accent { color: var(--accent); }
.hero-sub { font-size: 1.1rem; color: var(--text-muted); margin-bottom: 2.5rem; max-width: 460px; }
.hero-actions { display: flex; gap: 1rem; flex-wrap: wrap; }

.stats-section { border-top: 1px solid var(--border); border-bottom: 1px solid var(--border); padding: 2rem 0; background: var(--bg-card); }
.stats-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 2rem; text-align: center; }
.stat-item { display: flex; flex-direction: column; gap: 0.25rem; }
.stat-number { font-family: var(--font-display); font-size: 2.8rem; color: var(--accent); line-height: 1; }
.stat-label { font-size: 0.8rem; text-transform: uppercase; letter-spacing: 0.1em; color: var(--text-muted); }

.latest-section { padding: 5rem 0 3rem; }
.movies-section { padding: 3rem 0 5rem; background: var(--bg-card); border-top: 1px solid var(--border); }

.section-header { display: flex; align-items: center; justify-content: space-between; margin-bottom: 2.5rem; }
.events-preview-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 1.5rem; }
.movies-preview-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 1.5rem; }

@media (max-width: 768px) { .stats-grid { grid-template-columns: repeat(2, 1fr); } }
@media (max-width: 640px) { .stat-number { font-size: 2rem; } }
</style>
