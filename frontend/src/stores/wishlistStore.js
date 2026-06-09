import { defineStore } from 'pinia'
import { ref } from 'vue'
import { wishlistApi } from '@/api/wishlistApi'
import { useAuthStore } from '@/stores/authStore'

export const useWishlistStore = defineStore('wishlist', () => {
  const wishlist = ref([])

  async function fetchWishlist() {
    const auth = useAuthStore()
    if (!auth.isLoggedIn || !auth.user) return
    try {
      const res = await wishlistApi.getWishlist(auth.user.id)
      wishlist.value = res.data
    } catch (error) {
      console.error("Error fetching wishlist:", error)
    }
  }

  async function addToWishlist(itemId, type = 'EVENT') {
    const auth = useAuthStore()
    if (!auth.isLoggedIn || !auth.user) return
    try {
      await wishlistApi.addToWishlist(auth.user.id, itemId, type)
      await fetchWishlist()
    } catch (error) {
      console.error("Error adding to wishlist:", error)
    }
  }

  async function removeFromWishlist(itemId, type = 'EVENT') {
    const auth = useAuthStore()
    if (!auth.isLoggedIn || !auth.user) return
    try {
      await wishlistApi.removeFromWishlist(auth.user.id, itemId, type)
      await fetchWishlist()
    } catch (error) {
      console.error("Error removing from wishlist:", error)
    }
  }

  function isInWishlist(itemId, type = 'EVENT') {
    if (!wishlist.value) return false
    return wishlist.value.some(w =>
        w.itemId === itemId &&
        w.type.toUpperCase() === type.toUpperCase()
    )
  }

  return { wishlist, fetchWishlist, addToWishlist, removeFromWishlist, isInWishlist }
})