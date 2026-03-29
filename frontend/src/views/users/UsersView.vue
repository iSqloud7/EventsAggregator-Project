<template>
  <div class="users-page">
    <div class="container">
      <div class="page-header fade-up-1">
        <h1 class="page-title">MANAGE <span class="accent">USERS</span></h1>
        <span class="users-count">{{ userStore.users.length }} users</span>
      </div>

      <UserFilters class="fade-up-2" @filter="handleFilter" />

      <div v-if="userStore.loading" class="loading-spinner">
        <div class="spinner"></div>
      </div>

      <div v-else-if="userStore.error" class="alert alert-error">
        {{ userStore.error }}
      </div>

      <template v-else>
        <div v-if="userStore.users.length" class="users-grid fade-up-3">
          <UserCard
            v-for="user in userStore.users"
            :key="user.id"
            :user="user"
          />
        </div>

        <div v-else class="empty-state">
          <div class="icon">👤</div>
          <p>No users found.</p>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useUserStore } from '@/stores/userStore'
import UserCard    from '@/components/users/UserCard.vue'
import UserFilters from '@/components/users/UserFilters.vue'

const userStore = useUserStore()

function handleFilter({ name, surname, role }) {
  if (role) {
    userStore.fetchByRole(role)
  } else if (name && surname) {
    userStore.fetchByNameAndSurname(name, surname)
  } else if (name) {
    userStore.fetchByName(name)
  } else if (surname) {
    userStore.fetchBySurname(surname)
  } else {
    userStore.fetchAll()
  }
}

onMounted(() => userStore.fetchAll())
</script>

<style scoped>
.users-page { padding: 3rem 0 5rem; }

.page-header {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  margin-bottom: 2rem;
  flex-wrap: wrap;
  gap: 1rem;
}

.accent { color: var(--accent); }

.users-count {
  font-size: 0.85rem;
  color: var(--text-muted);
}

.users-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 1rem;
}
</style>
