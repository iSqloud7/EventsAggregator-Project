<template>
  <div class="filters">
    <div class="filter-row">
      <div class="form-group" style="flex:1; margin:0">
        <input
          v-model="localFilters.keyword"
          type="text"
          placeholder="Search events..."
          @input="emitFilters"
        />
      </div>

      <div class="form-group" style="margin:0; min-width:160px">
        <input
          v-model="localFilters.city"
          type="text"
          placeholder="City"
          @input="emitFilters"
        />
      </div>

      <div class="form-group" style="margin:0; min-width:160px">
      <select v-model="localFilters.month" @change="emitFilters">
        <option value="">All months</option>
        <option value="01">January</option>
        <option value="02">February</option>
        <option value="03">March</option>
        <option value="04">April</option>
        <option value="05">May</option>
        <option value="06">June</option>
        <option value="07">July</option>
        <option value="08">August</option>
        <option value="09">September</option>
        <option value="10">October</option>
        <option value="11">November</option>
        <option value="12">December</option>
      </select>
    </div>

      <button class="btn btn-ghost btn-sm" @click="clearFilters">Clear</button>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'

const emit = defineEmits(['filter'])

const localFilters = reactive({ keyword: '', city: '', month: '' })

function emitFilters() {
  emit('filter', { ...localFilters })
}

function clearFilters() {
  localFilters.keyword   = ''
  localFilters.city      = ''
  localFilters.month = ''
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
