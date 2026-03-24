import { defineStore } from 'pinia'
import { ref } from 'vue'
import { eventApi } from '@/api/eventApi'

export const useEventStore = defineStore('events', () => {
  const events  = ref([])
  const loading = ref(false)
  const error   = ref(null)

  async function fetchAll() {
    loading.value = true
    try {
      const res = await eventApi.getAll()
      events.value = res.data
    } catch (e) {
      error.value = e.message
    } finally {
      loading.value = false
    }
  }

  async function filterEvents(city, dateStart, keyword) {
    loading.value = true
    try {
      const res = await eventApi.filter(city, dateStart, keyword)
      events.value = res.data
    } finally {
      loading.value = false
    }
  }

  async function deleteEvent(id) {
    await eventApi.delete(id)
    events.value = events.value.filter(e => e.id !== id)
  }

  return { events, loading, error, fetchAll, filterEvents, deleteEvent }
})
