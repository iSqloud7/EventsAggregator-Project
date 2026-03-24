<template>
  <div class="event-card" @click="$router.push(`/events/${event.id}`)">
    <div class="card-image">
      <img v-if="event.image" :src="event.image" :alt="event.title" loading="lazy" />
      <div v-else class="card-image-placeholder">🎭</div>
      <div class="card-city" v-if="event.city">{{ event.city }}</div>
    </div>

    <div class="card-body">
      <h3 class="card-title">{{ event.title }}</h3>

      <div class="card-meta">
        <span v-if="event.date_start" class="meta-item">
          <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4" width="18" height="18" rx="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>
          {{ event.date_start }}
        </span>
        <span v-if="event.time_start" class="meta-item">
          <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>
          {{ event.time_start }}
        </span>
        <span v-if="event.location" class="meta-item">
          <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"/><circle cx="12" cy="10" r="3"/></svg>
          {{ event.location }}
        </span>
      </div>

      <div class="card-footer">
        <span class="card-price" v-if="event.price">{{ event.price }}</span>
        <span class="card-price free" v-else>Free</span>

        <div v-if="auth.isAdmin" class="card-actions" @click.stop>
          <RouterLink :to="`/events/edit/${event.id}`" class="btn btn-ghost btn-sm">Edit</RouterLink>
          <button class="btn btn-danger btn-sm" @click="confirmDelete">Delete</button>
        </div>
      </div>
    </div>

    <!-- Delete confirm modal -->
    <AppModal v-model="showDeleteModal" title="DELETE EVENT">
      <p style="color: var(--text-muted)">Are you sure you want to delete <strong style="color:var(--text)">{{ event.title }}</strong>?</p>
      <template #footer>
        <button class="btn btn-ghost btn-sm" @click="showDeleteModal = false">Cancel</button>
        <button class="btn btn-danger btn-sm" @click="handleDelete" :disabled="deleting">
          {{ deleting ? 'Deleting...' : 'Delete' }}
        </button>
      </template>
    </AppModal>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore }  from '@/stores/authStore'
import { useEventStore } from '@/stores/eventStore'
import AppModal from '@/components/common/AppModal.vue'

const props = defineProps({ event: { type: Object, required: true } })

const auth            = useAuthStore()
const eventStore      = useEventStore()
const showDeleteModal = ref(false)
const deleting        = ref(false)

function confirmDelete() { showDeleteModal.value = true }

async function handleDelete() {
  deleting.value = true
  await eventStore.deleteEvent(props.event.id)
  deleting.value        = false
  showDeleteModal.value = false
}
</script>

<style scoped>
.event-card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: 12px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.25s ease;
  display: flex;
  flex-direction: column;
}

.event-card:hover {
  border-color: var(--accent);
  transform: translateY(-4px);
  box-shadow: 0 12px 40px rgba(232, 255, 71, 0.08);
}

.card-image {
  position: relative;
  aspect-ratio: 16/9;
  overflow: hidden;
  background: var(--bg-elevated);
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.4s ease;
}

.event-card:hover .card-image img { transform: scale(1.04); }

.card-image-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  font-size: 3rem;
  color: var(--border);
}

.card-city {
  position: absolute;
  top: 0.75rem;
  left: 0.75rem;
  background: rgba(10,10,15,0.85);
  border: 1px solid var(--border);
  border-radius: 100px;
  padding: 0.2rem 0.6rem;
  font-size: 0.72rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.06em;
  backdrop-filter: blur(4px);
}

.card-body {
  padding: 1.1rem;
  display: flex;
  flex-direction: column;
  gap: 0.6rem;
  flex: 1;
}

.card-title {
  font-family: var(--font-display);
  font-size: 1.2rem;
  letter-spacing: 0.03em;
  line-height: 1.1;
}

.card-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 0.6rem;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 0.3rem;
  font-size: 0.8rem;
  color: var(--text-muted);
}

.card-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: auto;
  padding-top: 0.75rem;
  border-top: 1px solid var(--border);
}

.card-price {
  font-weight: 600;
  font-size: 0.9rem;
  color: var(--accent);
}

.card-price.free { color: var(--success); }

.card-actions {
  display: flex;
  gap: 0.4rem;
}
</style>
