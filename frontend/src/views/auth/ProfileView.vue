<template>
  <div class="profile-page">
    <div class="container">
      <div class="page-header fade-up-1">
        <h1 class="page-title">MY <span class="accent">PROFILE</span></h1>
      </div>

      <div class="profile-card fade-up-2">
        <div class="profile-avatar">
          {{ auth.user?.name?.charAt(0).toUpperCase() }}{{ auth.user?.surname?.charAt(0).toUpperCase() }}
        </div>

        <div class="profile-info">
          <div class="info-row">
            <span class="info-label">Username</span>
            <span class="info-value">{{ auth.user?.username }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">First Name</span>
            <span class="info-value">{{ auth.user?.name || '—' }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">Last Name</span>
            <span class="info-value">{{ auth.user?.surname || '—' }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">Role</span>
            <span class="info-value">
              <span class="badge" :class="auth.isAdmin ? 'badge-admin' : 'badge-user'">
                {{ auth.user?.role }}
              </span>
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from '@/stores/authStore'

const auth = useAuthStore()
</script>

<style scoped>
.profile-page { padding: 3rem 0 5rem; }

.page-header { margin-bottom: 2rem; }

.accent { color: var(--accent); }

.profile-card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: 16px;
  padding: 2rem;
  max-width: 600px;
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.profile-avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background: var(--accent);
  color: var(--bg);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.8rem;
  font-weight: 700;
  font-family: var(--font-display);
}

.profile-info {
  display: flex;
  flex-direction: column;
  gap: 0;
  border: 1px solid var(--border);
  border-radius: 12px;
  overflow: hidden;
}

.info-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.85rem 1.25rem;
  border-bottom: 1px solid var(--border);
}

.info-row:last-child { border-bottom: none; }

.info-label {
  font-size: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 0.08em;
  color: var(--text-muted);
  font-weight: 500;
}

.info-value {
  font-size: 0.95rem;
  font-weight: 500;
}
</style>