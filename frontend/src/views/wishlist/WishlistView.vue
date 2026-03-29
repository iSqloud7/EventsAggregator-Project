<template>
  <div class="container" style="padding: 2rem">
    <h1 class="page-title">MY WISHLIST</h1>

    <div v-if="wishlistStore.wishlist.length === 0" class="empty-state">
      <div class="icon">🤍</div>
      <p>Your wishlist is empty.</p>
      <RouterLink to="/events" class="btn btn-ghost" style="margin-top:1rem">
        Browse Events
      </RouterLink>
    </div>

    <div v-else class="events-grid">
      <EventCard
        v-for="item in wishlistStore.wishlist"
        :key="item.id"
        :event="item.event"
      />
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useWishlistStore } from '@/stores/wishlistStore'
import EventCard from '@/components/events/EventCard.vue'

const wishlistStore = useWishlistStore()

onMounted(async () => {
  await wishlistStore.fetchWishlist()
})
</script>

<style scoped>
.events-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
  margin-top: 2rem;
}
</style>