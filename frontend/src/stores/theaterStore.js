import { defineStore } from 'pinia'
import { ref } from 'vue'
import { theaterApi } from '@/api/theaterApi'
import { useWishlistStore } from '@/stores/wishlistStore'

export const useTheaterStore = defineStore('theater', () => {
    const shows = ref([])
    const loading = ref(false)
    const error = ref(null)
    const currentShow = ref(null)

    async function fetchById(id) {
        loading.value = true
        try {
            const res = await theaterApi.getById(id)
            currentShow.value = res.data
        } catch (e) {
            currentShow.value = null
        } finally {
            loading.value = false
        }
    }

    async function fetchAll() {
        loading.value = true
        try {
            const res = await theaterApi.getAll()
            shows.value = res.data
        } catch (e) {
            error.value = e.message
        } finally {
            loading.value = false
        }
    }

    async function deleteShow(id) {
        await theaterApi.delete(id)
        shows.value = shows.value.filter(s => s.id !== id)

        const wishlistStore = useWishlistStore()
        await wishlistStore.fetchWishlist()
    }

    async function createShow(data) {
        loading.value = true
        try {
            const res = await theaterApi.create(data)
            shows.value.push(res.data)
            return true
        } catch (e) {
            error.value = e.message
            return false
        } finally {
            loading.value = false
        }
    }

    async function updateShow(id, data) {
        loading.value = true
        try {
            const res = await theaterApi.update(id, data)
            const index = shows.value.findIndex(s => s.id === Number(id))
            if (index !== -1) {
                shows.value[index] = res.data
            }
            currentShow.value = res.data
            return true
        } catch (e) {
            error.value = e.message
            return false
        } finally {
            loading.value = false
        }
    }

    function clearCurrentShow() {
        currentShow.value = null
    }

    return {
        shows,
        currentShow,
        loading,
        error,
        fetchAll,
        deleteShow,
        fetchById,
        createShow,
        updateShow,
        clearCurrentShow
    }
})