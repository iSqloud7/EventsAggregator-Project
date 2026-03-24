<template>
  <div class="filters">
    <div class="filter-row">
      <div class="form-group" style="margin:0; flex:1">
        <input v-model="localFilters.name" type="text" placeholder="First name..." @input="emitFilters" />
      </div>
      <div class="form-group" style="margin:0; flex:1">
        <input v-model="localFilters.surname" type="text" placeholder="Last name..." @input="emitFilters" />
      </div>
      <div class="form-group" style="margin:0; min-width:140px">
        <select v-model="localFilters.role" @change="emitFilters">
          <option value="">All roles</option>
          <option value="ADMIN">Admin</option>
          <option value="USER">User</option>
        </select>
      </div>
      <button class="btn btn-ghost btn-sm" @click="clearFilters">Clear</button>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'

const emit = defineEmits(['filter'])

const localFilters = reactive({ name: '', surname: '', role: '' })

function emitFilters() {
  emit('filter', { ...localFilters })
}

function clearFilters() {
  localFilters.name    = ''
  localFilters.surname = ''
  localFilters.role    = ''
  emitFilters()
}
</script>

<style scoped>
.filters {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: 12px;
  padding: 1rem 1.25rem;
  margin-bottom: 2rem;
}

.filter-row {
  display: flex;
  gap: 0.75rem;
  align-items: flex-end;
  flex-wrap: wrap;
}
</style>
