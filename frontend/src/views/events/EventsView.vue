<template>
  <div class="events-page">
    <div class="container">
      <div class="page-header fade-up-1">
        <h1 class="page-title">ALL <span class="accent">EVENTS</span></h1>
        <RouterLink v-if="auth.isAdmin" to="/events/add" class="btn btn-primary">+ Add Event</RouterLink>
      </div>

      <EventFilters class="fade-up-2" @filter="handleFilter" />

      <div v-if="eventStore.loading" class="loading-spinner">
        <div class="spinner"></div>
      </div>

      <div v-else-if="eventStore.error" class="alert alert-error">
        {{ eventStore.error }}
      </div>

      <template v-else>
        <p class="results-count fade-up">
          {{ filteredEvents.length }} event{{ filteredEvents.length !== 1 ? 's' : '' }} found
        </p>

        <div v-if="filteredEvents.length" class="events-grid">
          <EventCard
            v-for="(event, i) in filteredEvents"
            :key="event.id"
            :event="event"
            :style="{ animationDelay: `${i * 0.05}s` }"
            class="fade-up"
          />
        </div>

        <div v-else class="empty-state">
          <div class="icon">🎭</div>
          <p>No events found matching your filters.</p>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useAuthStore }  from '@/stores/authStore'
import { useEventStore } from '@/stores/eventStore'
import EventCard    from '@/components/events/EventCard.vue'
import EventFilters from '@/components/events/EventFilters.vue'

const auth       = useAuthStore()
const eventStore = useEventStore()

const activeFilters = ref({ keyword: '', city: '', dateStart: '' })

const filteredEvents = computed(() => {
  let list = eventStore.events
  const { keyword, city } = activeFilters.value

  if (keyword) {
    const q = keyword.toLowerCase()
    list = list.filter(e =>
      e.title?.toLowerCase().includes(q) ||
      e.description?.toLowerCase().includes(q) ||
      e.location?.toLowerCase().includes(q)
    )
  }
  if (city) {
    list = list.filter(e => e.city?.toLowerCase().includes(city.toLowerCase()))
  }
  return list
})

function handleFilter(filters) {
  activeFilters.value = filters
  // If backend filtering needed:
  // if (filters.city || filters.dateStart || filters.keyword) {
  //   eventStore.filterEvents(filters.city, filters.dateStart, filters.keyword)
  // } else {
  //   eventStore.fetchAll()
  // }
}

onMounted(() => eventStore.fetchAll())
</script>

<style scoped>
.events-page { padding: 3rem 0 5rem; }

.page-header {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  margin-bottom: 2rem;
  gap: 1rem;
  flex-wrap: wrap;
}

.accent { color: var(--accent); }

.results-count {
  font-size: 0.85rem;
  color: var(--text-muted);
  margin-bottom: 1.5rem;
}

.events-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
}
</style>
