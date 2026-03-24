<template>
  <div class="auth-page">
    <div class="auth-card fade-up">
      <div class="auth-header">
        <RouterLink to="/" class="auth-logo">EVENTS<span class="accent">MK</span></RouterLink>
        <h1 class="auth-title">CREATE ACCOUNT</h1>
        <p class="auth-sub">Join EventsMK today</p>
      </div>

      <form @submit.prevent="handleRegister" class="auth-form">
        <div class="alert alert-error" v-if="auth.error">{{ auth.error }}</div>

        <div class="form-row">
          <div class="form-group">
            <label>First Name</label>
            <input v-model="form.name" type="text" placeholder="John" required/>
          </div>
          <div class="form-group">
            <label>Last Name</label>
            <input v-model="form.surname" type="text" placeholder="Doe" required/>
          </div>
        </div>

        <div class="form-group">
          <label>Username</label>
          <input v-model="form.username" type="text" placeholder="john_doe" required/>
        </div>

        <div class="form-group">
          <label>Email</label>
          <input v-model="form.email" type="email" placeholder="john@example.com" required/>
        </div>

        <div class="form-group">
          <label>Password</label>
          <input v-model="form.password" type="password" placeholder="••••••••" required/>
        </div>

        <div class="form-group">
          <label>Check Password</label>
          <input v-model="form.confirmPassword" type="password" placeholder="••••••••" required/>
        </div>

        <div class="form-group">
          <label>Role</label>
          <select v-model="form.role" required>
            <option value="" disabled>Select role</option>
            <option value="client">Client</option>
            <option value="developer">Developer</option>
            <option value="admin">Admin</option>
          </select>
        </div>

        <div class="alert alert-error" v-if="passwordMismatch">Passwords do not match</div>

        <button type="submit" class="btn btn-primary btn-full" :disabled="passwordMismatch">
          Create Account
        </button>
      </form>

      <p class="auth-switch">
        Already have an account?
        <RouterLink to="/login" class="accent-link">Sign in</RouterLink>
      </p>
    </div>
  </div>
</template>

<script setup>
import {reactive, computed} from 'vue'
import {useRouter} from 'vue-router'
import {useAuthStore} from '@/stores/authStore'

const router = useRouter()
const auth = useAuthStore()

const form = reactive({
  name: '', surname: '', username: '', email: '', password: '', confirmPassword: '', role: ''
})

const passwordMismatch = computed(() =>
    form.confirmPassword.length > 0 && form.password !== form.confirmPassword
)

async function handleRegister() {
  if (passwordMismatch.value) return
  const ok = await auth.register({
    name: form.name,
    surname: form.surname,
    username: form.username,
    email: form.email,
    password: form.password,
    role: form.role
  })
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
  background: radial-gradient(ellipse at 80% 50%, rgba(232, 255, 71, 0.04) 0%, transparent 50%),
  var(--bg);
}

.auth-card {
  width: 100%;
  max-width: 480px;
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

.accent {
  color: var(--accent);
}

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

.auth-form {
  display: flex;
  flex-direction: column;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 0 1rem;
}

.btn-full {
  width: 100%;
  justify-content: center;
  margin-top: 0.5rem;
}

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

.accent-link:hover {
  opacity: 0.8;
}
</style>
