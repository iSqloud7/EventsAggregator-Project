<template>
  <div class="user-card">
    <div class="user-avatar">
      {{ initials }}
    </div>
    <div class="user-info">
      <div class="user-name">{{ user.name }} {{ user.surname }}</div>
      <div class="user-email">{{ user.email }}</div>
    </div>
    <span class="badge" :class="user.role === 'ADMIN' ? 'badge-admin' : 'badge-user'">
      {{ user.role }}
    </span>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({ user: { type: Object, required: true } })

const initials = computed(() =>
  `${props.user.name?.[0] || ''}${props.user.surname?.[0] || ''}`.toUpperCase()
)
</script>

<style scoped>
.user-card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: 12px;
  padding: 1rem 1.25rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  transition: border-color var(--transition);
}

.user-card:hover { border-color: var(--accent); }

.user-avatar {
  width: 42px;
  height: 42px;
  border-radius: 50%;
  background: var(--bg-elevated);
  border: 1px solid var(--border);
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: var(--font-display);
  font-size: 1rem;
  color: var(--accent);
  flex-shrink: 0;
}

.user-info { flex: 1; min-width: 0; }

.user-name {
  font-weight: 600;
  font-size: 0.95rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.user-email {
  font-size: 0.8rem;
  color: var(--text-muted);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
