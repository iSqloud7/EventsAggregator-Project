<template>
  <form class="theater-form" @submit.prevent="handleSubmit">
    <div class="alert alert-error" v-if="error">{{ error }}</div>

    <div class="form-row">
      <div class="form-group">
        <label>Title *</label>
        <input v-model="form.title" type="text" placeholder="Show title" required />
      </div>
      <div class="form-group">
        <label>City</label>
        <input v-model="form.city" type="text" placeholder="Skopje" />
      </div>
    </div>

    <div class="form-row">
      <div class="form-group">
        <label>Date</label>
        <input v-model="form.dateStart" type="text" placeholder="e.g. 25-04-2025" />
      </div>
      <div class="form-group">
        <label>Time</label>
        <input v-model="form.timeStart" type="text" placeholder="e.g. 20:00" />
      </div>
      <div class="form-group">
        <label>Price</label>
        <input v-model="form.price" type="text" placeholder="e.g. 300 ден." />
      </div>
    </div>

    <div class="form-group">
      <label>Location</label>
      <input v-model="form.location" type="text" placeholder="Theater name" />
    </div>

    <div class="form-group">
      <label>Image URL</label>
      <input v-model="form.image" type="url" placeholder="https://..." />
    </div>

    <div class="form-group">
      <label>Description</label>
      <textarea v-model="form.description" placeholder="Show description or cast..." rows="4" />
    </div>

    <div class="form-actions">
      <RouterLink to="/theaters" class="btn btn-ghost">Cancel</RouterLink>
      <button type="submit" class="btn btn-primary" :disabled="loading">
        {{ loading ? 'Saving...' : isEdit ? 'Update Show' : 'Create Show' }}
      </button>
    </div>
  </form>
</template>

<script setup>
import { reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useTheaterStore } from '@/stores/theaterStore'

const props = defineProps({
  initialData: { type: Object, default: null },
  showId:      { type: [Number, String], default: null }
})

const router = useRouter()
const theaterStore = useTheaterStore()
const loading = computed(() => theaterStore.loading)
const error = computed(() => theaterStore.error)
const isEdit = computed(() => !!props.showId)

const form = reactive({
  title:       props.initialData?.title       || '',
  city:        props.initialData?.city        || '',
  location:    props.initialData?.location    || '',
  dateStart:   props.initialData?.dateStart   || '',
  timeStart:   props.initialData?.timeStart   || '',
  price:       props.initialData?.price       || '',
  image:       props.initialData?.image       || '',
  description: props.initialData?.description || ''
})

async function handleSubmit() {
  const payload = {
    title:       form.title,
    city:        form.city,
    location:    form.location,
    dateStart:   form.dateStart,
    timeStart:   form.timeStart,
    price:       form.price,
    image:       form.image,
    description: form.description
  }

  let result
  if (isEdit.value) {
    result = await theaterStore.updateShow(props.showId, payload)
  } else {
    result = await theaterStore.createShow(payload)
  }

  if (result) router.back()
}
</script>

<style scoped>
.theater-form { display: flex; flex-direction: column; gap: 0.25rem; }
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