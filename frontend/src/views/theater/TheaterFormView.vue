<template>
  <div class="form-page">
    <div class="container">
      <div class="form-header fade-up-1">
        <RouterLink to="/theaters" class="back-link">← Back to Theater</RouterLink>
        <h1 class="page-title">
          {{ isEdit ? 'EDIT' : 'ADD' }} <span class="accent">SHOW</span>
        </h1>
      </div>

      <div class="form-card fade-up-2">
        <div v-if="loading" class="loading-spinner">
          <div class="spinner"></div>
        </div>
        <TheaterForm
            v-else
            :initial-data="theaterStore.currentShow"
            :show-id="isEdit ? route.params.id : null"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRoute }      from 'vue-router'
import { useTheaterStore } from '@/stores/theaterStore'
import TheaterForm from '@/components/theater/TheaterForm.vue'

const route = useRoute()
const theaterStore = useTheaterStore()

const isEdit = computed(() => !!route.params.id)
const loading = computed(() => theaterStore.loading)

onMounted(() => {
  if (isEdit.value) {
    theaterStore.fetchById(route.params.id)
  } else {
    theaterStore.clearCurrentShow()
  }
})
</script>

<style scoped>
.form-page { padding: 3rem 0 5rem; }
.form-header { margin-bottom: 2rem; }
.back-link { font-size: 0.85rem; color: var(--text-muted); transition: color var(--transition); display: inline-block; margin-bottom: 1rem; }
.back-link:hover { color: var(--accent); }
.accent { color: var(--accent); }
.form-card { background: var(--bg-card); border: 1px solid var(--border); border-radius: 16px; padding: 2rem; max-width: 700px; }
</style>