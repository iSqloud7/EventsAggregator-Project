import { defineStore } from 'pinia'
import { ref } from 'vue'
import { wishlistApi } from '@/api/wishlistApi'
import { useAuthStore } from '@/stores/authStore'

export const useWishlistStore = defineStore('wishlist', () => {
  const wishlist = ref([])
  const loading = ref(false)

  async function fetchWishlist() {
    const auth = useAuthStore()
    if (!auth.isLoggedIn || !auth.user) return
    loading.value = true
    try {
      const res = await wishlistApi.getWishlist(auth.user.id)
      wishlist.value = res.data
    } catch (error) {
      console.error("Error fetching wishlist:", error)
    } finally {
      loading.value = false
    }
  }

  async function addToWishlist(itemId, type = 'EVENT') {
    if (loading.value) return
    const auth = useAuthStore()
    if (!auth.isLoggedIn || !auth.user) return

    loading.value = true
    try {
      await wishlistApi.addToWishlist(auth.user.id, itemId, type)
      await fetchWishlist()
    } catch (error) {
      console.error("Error adding to wishlist:", error)
    } finally {
      loading.value = false
    }
  }

  async function removeFromWishlist(itemId, type = 'EVENT') {
    if (loading.value) return
    const auth = useAuthStore()
    if (!auth.isLoggedIn || !auth.user) return

    loading.value = true
    try {
      await wishlistApi.removeFromWishlist(auth.user.id, itemId, type)
      await fetchWishlist()
    } catch (error) {
      console.error("Error removing from wishlist:", error)
    } finally {
      loading.value = false
    }
  }

  function isInWishlist(itemId, type = 'EVENT') {
    if (!wishlist.value) return false
    return wishlist.value.some(w =>
        w.itemId === itemId &&
        w.type.toUpperCase() === type.toUpperCase()
    )
  }

  return { wishlist, loading, fetchWishlist, addToWishlist, removeFromWishlist, isInWishlist }
})