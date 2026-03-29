import { defineStore } from 'pinia'
import { ref } from 'vue'
import { wishlistApi } from '@/api/wishlistApi'
import { useAuthStore } from '@/stores/authStore'

export const useWishlistStore = defineStore('wishlist', () => {
  const wishlist = ref([])

  async function fetchWishlist() {
    const auth = useAuthStore()
    if (!auth.isLoggedIn) return
    const res = await wishlistApi.getWishlist(auth.user.id)
    wishlist.value = res.data
  }

  async function addToWishlist(eventId) {
    const auth = useAuthStore()
    if (!auth.isLoggedIn) return
    await wishlistApi.addToWishlist(auth.user.id, eventId)
    await fetchWishlist()
  }

  async function removeFromWishlist(eventId) {
    const auth = useAuthStore()
    if (!auth.isLoggedIn) return
    await wishlistApi.removeFromWishlist(auth.user.id, eventId)
    await fetchWishlist()
  }

  function isInWishlist(eventId) {
    return wishlist.value.some(w => w.event.id === eventId)
  }

  return { wishlist, fetchWishlist, addToWishlist, removeFromWishlist, isInWishlist }
})