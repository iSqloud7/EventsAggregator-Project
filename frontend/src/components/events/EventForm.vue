<template>
  <form class="event-form" @submit.prevent="handleSubmit">
    <div class="alert alert-error" v-if="error">{{ error }}</div>

    <div class="form-row">
      <div class="form-group">
        <label>Title *</label>
        <input v-model="form.title" type="text" placeholder="Event title" required />
      </div>
      <div class="form-group">
        <label>City</label>
        <input v-model="form.city" type="text" placeholder="Skopje" />
      </div>
    </div>

    <div class="form-row">
      <div class="form-group">
        <label>Date</label>
        <input v-model="form.dateStart" type="text" placeholder="e.g. 25.04.2025" />
      </div>
      <div class="form-group">
        <label>Time</label>
        <input v-model="form.timeStart" type="text" placeholder="e.g. 20:00" />
      </div>
      <div class="form-group">
        <label>Price</label>
        <input v-model="form.price" type="text" placeholder="e.g. 500 MKD" />
      </div>
    </div>

    <div class="form-group">
      <label>Location</label>
      <input v-model="form.location" type="text" placeholder="Venue name" />
    </div>

    <div class="form-group">
      <label>Image URL</label>
      <input v-model="form.image" type="url" placeholder="https://..." />
    </div>

    <div class="form-group">
      <label>Description</label>
      <textarea v-model="form.description" placeholder="Event description..." rows="4" />
    </div>

    <div class="form-actions">
      <RouterLink to="/events" class="btn btn-ghost">Cancel</RouterLink>
      <button type="submit" class="btn btn-primary" :disabled="loading">
        {{ loading ? 'Saving...' : isEdit ? 'Update Event' : 'Create Event' }}
      </button>
    </div>
  </form>
</template>

<script setup>
import { reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useEventStore } from '@/stores/eventStore'

const props = defineProps({
  initialData: { type: Object, default: null },
  eventId:     { type: [Number, String], default: null }
})

const router     = useRouter()
const eventStore = useEventStore()
const loading    = computed(() => eventStore.loading)
const error      = computed(() => eventStore.error)
const isEdit     = computed(() => !!props.eventId)

const form = reactive({
  title:       props.initialData?.title       || '',
  city:        props.initialData?.city        || '',
  location:    props.initialData?.location    || '',
  dateStart:   props.initialData?.date_start  || '',
  timeStart:   props.initialData?.time_start  || '',
  price:       props.initialData?.price       || '',
  image:       props.initialData?.image       || '',
  description: props.initialData?.description || ''
})

async function handleSubmit() {
  const payload = {
    title:       form.title,
    city:        form.city,
    location:    form.location,
    date_start:  form.dateStart,
    time_start:  form.timeStart,
    price:       form.price,
    image:       form.image,
    description: form.description
  }

  let result
  if (isEdit.value) {
    result = await eventStore.updateEvent(props.eventId, payload)
  } else {
    result = await eventStore.createEvent(payload)
  }

  if (result) router.push('/events')
}
</script>

<style scoped>
.event-form { display: flex; flex-direction: column; gap: 0.25rem; }

.form-row {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 0 1rem;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
  margin-top: 1rem;
  padding-top: 1.25rem;
  border-top: 1px solid var(--border);
}
</style>
