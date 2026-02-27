<script setup>
import { ref, onMounted } from "vue"
import EventCard from "../components/EventCard.vue"
import EventModal from "../components/EventModal.vue"

const events = ref([])
const selectedEvent = ref(null)

onMounted(async () => {
  const res = await fetch("http://127.0.0.1:5000/events")
  events.value = await res.json()
})

function openModal(event) {
  selectedEvent.value = event
}

function closeModal() {
  selectedEvent.value = null
}
</script>

<template>
  <div class="grid">
    <EventCard
      v-for="e in events"
      :key="e.id"
      :event="e"
      @read-more="openModal"
    />
  </div>

  <EventModal
    v-if="selectedEvent"
    :event="selectedEvent"
    @close="closeModal"
  />
</template>

<style scoped>
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 24px;
  padding: 32px;
  max-width: 1200px;
  margin: 0 auto;
}
</style>