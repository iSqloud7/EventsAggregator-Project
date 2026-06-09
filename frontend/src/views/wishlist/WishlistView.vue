<template>
  <div class="container" style="padding: 2rem">
    <h1 class="page-title">MY WISHLIST</h1>

    <div v-if="!wishlistStore.wishlist || wishlistStore.wishlist.length === 0" class="empty-state">
      <div class="icon">🤍</div>
      <p>Your wishlist is empty.</p>
      <RouterLink to="/events" class="btn btn-ghost" style="margin-top:1rem">
        Browse Events
      </RouterLink>
    </div>

    <div v-else class="events-grid">
      <template v-for="item in wishlistStore.wishlist" :key="item.id">

        <EventCard
            v-if="item.type === 'EVENT'"
            :event="{
            id: item.itemId,
            title: item.title,
            image: item.image,
            dateStart: item.dateStart,
            timeStart: item.timeStart,
            location: item.location,
            price: item.price
          }"
        />

        <TheaterCard
            v-else-if="item.type === 'THEATER'"
            :show="{
            id: item.itemId,
            title: item.title,
            image: item.image,
            dateStart: item.dateStart,
            timeStart: item.timeStart,
            location: item.location,
            price: item.price,
            city: item.city
          }"
        />

      </template>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useWishlistStore } from '@/stores/wishlistStore'
import EventCard from '@/components/events/EventCard.vue'
import TheaterCard from '@/components/theater/TheaterCard.vue'

const wishlistStore = useWishlistStore()

onMounted(async () => {
  try {
    await wishlistStore.fetchWishlist()
  } catch (err) {
    console.error("Error loading wishlist:", err)
  }
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