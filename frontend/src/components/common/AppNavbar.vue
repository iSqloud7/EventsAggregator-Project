<template>
  <nav class="navbar" :class="{ scrolled: isScrolled }">
    <div class="container nav-inner">
      <RouterLink to="/" class="nav-logo">
        <span class="logo-text">EVENTS<span class="accent">MK</span></span>
      </RouterLink>

    <div class="nav-links">
      <RouterLink to="/events" class="nav-link">Events</RouterLink>
      <RouterLink v-if="auth.isLoggedIn" to="/wishlist" class="nav-link">Wishlist ❤️</RouterLink>
      <RouterLink v-if="auth.isAdmin" to="/users" class="nav-link">Users</RouterLink>
      <RouterLink v-if="auth.isAdmin" to="/events/add" class="btn btn-ghost btn-sm">+ Add Event</RouterLink>
      <RouterLink v-if="auth.isAdmin || auth.isDeveloper" to="/stats" class="nav-link">Stats</RouterLink>
    </div>

      <div class="nav-actions">
      <template v-if="auth.isLoggedIn">
      <RouterLink to="/profile" class="user-info">
        <span class="badge" :class="auth.isAdmin ? 'badge-admin' : 'badge-user'">
          {{ auth.user.role }}
        </span>
        {{ auth.user?.name }}
      </RouterLink>
      <button class="btn btn-ghost btn-sm" @click="handleLogout">Logout</button>
      </template>
        <template v-else>
          <RouterLink to="/login" class="btn btn-ghost btn-sm">Login</RouterLink>
          <RouterLink to="/register" class="btn btn-primary btn-sm">Register</RouterLink>
        </template>
      </div>

      <!-- Mobile menu toggle -->
      <button class="mobile-toggle" @click="menuOpen = !menuOpen">
        <span></span><span></span><span></span>
      </button>
    </div>

    <!-- Mobile menu -->
    <div class="mobile-menu" :class="{ open: menuOpen }" @click="menuOpen = false">
      <RouterLink to="/events">Events</RouterLink>
      <RouterLink v-if="auth.isAdmin" to="/users">Users</RouterLink>
      <RouterLink v-if="auth.isAdmin" to="/events/add">+ Add Event</RouterLink>
      <RouterLink v-if="auth.isLoggedIn" to="/wishlist">Wishlist ❤️</RouterLink>
      <template v-if="auth.isLoggedIn">
        <button @click="handleLogout">Logout</button>
      </template>
      <template v-else>
        <RouterLink to="/login">Login</RouterLink>
        <RouterLink to="/register">Register</RouterLink>
      </template>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const auth      = useAuthStore()
const router    = useRouter()
const isScrolled = ref(false)
const menuOpen   = ref(false)

function handleLogout() {
  auth.logout()
  router.push('/login')
}

function onScroll() {
  isScrolled.value = window.scrollY > 20
}

onMounted(()  => window.addEventListener('scroll', onScroll))
onUnmounted(() => window.removeEventListener('scroll', onScroll))
</script>

<style scoped>
.navbar {
  position: sticky;
  top: 0;
  z-index: 100;
  background: transparent;
  border-bottom: 1px solid transparent;
  transition: all 0.3s ease;
  backdrop-filter: blur(0px);
}

.navbar.scrolled {
  background: rgba(10, 10, 15, 0.92);
  border-bottom-color: var(--border);
  backdrop-filter: blur(12px);
}

.nav-inner {
  display: flex;
  align-items: center;
  gap: 2rem;
  height: 64px;
}

.nav-logo { margin-right: auto; }

.logo-text {
  font-family: var(--font-display);
  font-size: 1.6rem;
  letter-spacing: 0.08em;
}

.accent { color: var(--accent); }

.nav-links {
  display: flex;
  align-items: center;
  gap: 1.5rem;
}

.nav-link {
  font-size: 0.9rem;
  font-weight: 500;
  color: var(--text-muted);
  transition: color var(--transition);
}

.nav-link:hover,
.nav-link.router-link-active { color: var(--text); }

.nav-actions {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.85rem;
  color: var(--text-muted);
}

.mobile-toggle {
  display: none;
  flex-direction: column;
  gap: 5px;
  background: none;
  border: none;
  cursor: pointer;
  padding: 4px;
}

.mobile-toggle span {
  display: block;
  width: 22px;
  height: 2px;
  background: var(--text);
  border-radius: 2px;
  transition: all 0.2s;
}

.mobile-menu {
  display: none;
  flex-direction: column;
  gap: 0.5rem;
  padding: 1rem 2rem;
  background: var(--bg-card);
  border-top: 1px solid var(--border);
}

.mobile-menu a,
.mobile-menu button {
  padding: 0.6rem 0;
  color: var(--text);
  font-size: 1rem;
  background: none;
  border: none;
  cursor: pointer;
  text-align: left;
  border-bottom: 1px solid var(--border);
}

@media (max-width: 768px) {
  .nav-links, .nav-actions { display: none; }
  .mobile-toggle { display: flex; margin-left: auto; }
  .mobile-menu.open { display: flex; }
}
</style>
