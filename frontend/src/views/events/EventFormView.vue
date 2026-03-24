<template>
  <div class="form-page">
    <div class="container">
      <div class="form-header fade-up-1">
        <RouterLink to="/events" class="back-link">← Back to Events</RouterLink>
        <h1 class="page-title">
          {{ isEdit ? 'EDIT' : 'ADD' }} <span class="accent">EVENT</span>
        </h1>
      </div>

      <div class="form-card fade-up-2">
        <div v-if="loading" class="loading-spinner">
          <div class="spinner"></div>
        </div>
        <EventForm
          v-else
          :initial-data="eventStore.currentEvent"
          :event-id="isEdit ? route.params.id : null"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRoute }      from 'vue-router'
import { useEventStore } from '@/stores/eventStore'
import EventForm from '@/components/events/EventForm.vue'

const route      = useRoute()
const eventStore = useEventStore()

const isEdit  = computed(() => !!route.params.id)
const loading = computed(() => eventStore.loading)

onMounted(() => {
  if (isEdit.value) {
    eventStore.fetchById(route.params.id)
  } else {
    eventStore.currentEvent = null
  }
})
</script>

<style scoped>
.form-page { padding: 3rem 0 5rem; }

.form-header { margin-bottom: 2rem; }

.back-link {
  font-size: 0.85rem;
  color: var(--text-muted);
  transition: color var(--transition);
  display: inline-block;
  margin-bottom: 1rem;
}
.back-link:hover { color: var(--accent); }

.accent { color: var(--accent); }

.form-card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: 16px;
  padding: 2rem;
  max-width: 700px;
}
</style>
