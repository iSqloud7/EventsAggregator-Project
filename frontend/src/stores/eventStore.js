import { defineStore } from 'pinia'
import { ref } from 'vue'
import { eventApi } from '@/api/eventApi'
import { useWishlistStore } from '@/stores/wishlistStore'

export const useEventStore = defineStore('events', () => {
  const events  = ref([])
  const loading = ref(false)
  const error   = ref(null)
  const currentEvent = ref(null)

    async function fetchById(id) {
      loading.value = true
      try {
        const res = await eventApi.getById(id)
        currentEvent.value = res.data
      } catch (e) {
        currentEvent.value = null
      } finally {
        loading.value = false
      }
    }


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

    const wishlistStore = useWishlistStore()
    await wishlistStore.fetchWishlist()
  }

  async function createEvent(data) {
  loading.value = true
  try {
    const res = await eventApi.create(data)
    events.value.push(res.data)
    return true
  } catch (e) {
    error.value = e.message
    return false
  } finally {
    loading.value = false
  }
}

async function updateEvent(id, data) {
  loading.value = true
  try {
    const res = await eventApi.update(id, data)
    const index = events.value.findIndex(e => e.id === Number(id))
    if (index !== -1) {
      events.value[index] = res.data
    }
    currentEvent.value = res.data
    return true
  } catch (e) {
    error.value = e.message
    return false
  } finally {
    loading.value = false
  }
}

function clearCurrentEvent() {
  currentEvent.value = null
}

return { events, currentEvent, loading, error, fetchAll, filterEvents, deleteEvent, fetchById, createEvent, updateEvent, clearCurrentEvent  }})
