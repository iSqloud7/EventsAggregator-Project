<template>
  <div class="auth-page">
    <div class="auth-card fade-up">
      <div class="auth-header">
        <RouterLink to="/" class="auth-logo">EVENTS<span class="accent">MK</span></RouterLink>
        <h1 class="auth-title">WELCOME BACK</h1>
        <p class="auth-sub">Sign in to your account</p>
      </div>

      <form @submit.prevent="handleLogin" class="auth-form">
        <div class="alert alert-error" v-if="auth.error">{{ auth.error }}</div>

        <div class="form-group">
          <label>Username</label>
          <input v-model="form.username" type="text" placeholder="your_username" required autofocus />
        </div>

        <div class="form-group">
          <label>Password</label>
          <input v-model="form.password" type="password" placeholder="••••••••" required />
        </div>

        <button type="submit" class="btn btn-primary btn-full" :disabled="loading">
          {{ loading ? 'Signing in...' : 'Sign In' }}
        </button>
      </form>

      <p class="auth-switch">
        Don't have an account?
        <RouterLink to="/register" class="accent-link">Register here</RouterLink>
      </p>
    </div>
  </div>
</template>

<script setup>
import { reactive, computed } from 'vue'
import { useRouter }    from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const router  = useRouter()
const auth    = useAuthStore()
const loading = computed(() => false)

const form = reactive({ username: '', password: '' })

async function handleLogin() {
  const ok = await auth.login({ username: form.username, password: form.password })
  if (ok) router.push('/')
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  background:
    radial-gradient(ellipse at 20% 50%, rgba(232,255,71,0.04) 0%, transparent 50%),
    var(--bg);
}

.auth-card {
  width: 100%;
  max-width: 420px;
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: 16px;
  padding: 2.5rem;
}

.auth-header {
  text-align: center;
  margin-bottom: 2rem;
}

.auth-logo {
  font-family: var(--font-display);
  font-size: 1.4rem;
  letter-spacing: 0.1em;
  display: inline-block;
  margin-bottom: 1.5rem;
}

.accent { color: var(--accent); }

.auth-title {
  font-family: var(--font-display);
  font-size: 2rem;
  letter-spacing: 0.06em;
  margin-bottom: 0.5rem;
}

.auth-sub {
  font-size: 0.9rem;
  color: var(--text-muted);
}

.auth-form { display: flex; flex-direction: column; }

.btn-full { width: 100%; justify-content: center; margin-top: 0.5rem; }

.auth-switch {
  text-align: center;
  margin-top: 1.5rem;
  font-size: 0.875rem;
  color: var(--text-muted);
}

.accent-link {
  color: var(--accent);
  font-weight: 500;
  transition: opacity var(--transition);
}
.accent-link:hover { opacity: 0.8; }
</style>
