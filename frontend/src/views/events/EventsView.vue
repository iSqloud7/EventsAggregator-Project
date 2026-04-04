<template>
  <div class="events-page">
    <div class="container">
      <div class="page-header fade-up-1">
        <h1 class="page-title">ALL <span class="accent">EVENTS</span></h1>
        <RouterLink v-if="auth.isAdmin || auth.isDeveloper" to="/events/add" class="btn btn-primary">+ Add Event</RouterLink>
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
          <p>No events found matching your filters.</p>
        </div>
         <div class="pagination" v-if="totalPages > 1">
            <button class="btn btn-ghost btn-sm" :disabled="currentPage === 1" @click="currentPage--">← Prev</button>
            <span class="page-info">{{ currentPage }} / {{ totalPages }}</span>
            <button class="btn btn-ghost btn-sm" :disabled="currentPage === totalPages" @click="currentPage++">Next →</button>
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

const filteredEvents = computed(() => {
  let list = eventStore.events
  const { keyword, city, month } = activeFilters.value

  if (keyword) {
    const q = transliterate(keyword.toLowerCase())
    list = list.filter(e =>
      transliterate(e.title?.toLowerCase() || '').includes(q) ||
      transliterate(e.description?.toLowerCase() || '').includes(q) ||
      transliterate(e.location?.toLowerCase() || '').includes(q)
    )
  }

  if (city) {
    list = list.filter(e =>
      transliterate(e.city?.toLowerCase() || '').includes(transliterate(city.toLowerCase())) ||
      e.city?.toLowerCase().includes(city.toLowerCase())
    )
  }

  if (month) {
    list = list.filter(e => {
      if (!e.dateStart) return false
      const parts = e.dateStart.split('-')
      return parts[1] === month
    })
  }

  return list
})
const currentPage = ref(1)
const pageSize = 9

const paginatedEvents = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return filteredEvents.value.slice(start, start + pageSize)
})

const totalPages = computed(() => Math.ceil(filteredEvents.value.length / pageSize))

function handleFilter(filters) {
  activeFilters.value = filters
  currentPage.value = 1
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
.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  margin-top: 2rem;
}
.page-info {
  font-size: 0.85rem;
  color: var(--text-muted);
}
</style>
