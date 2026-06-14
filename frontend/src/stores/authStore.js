import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { userApi } from '@/api/userApi'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(JSON.parse(sessionStorage.getItem('user') || 'null'))

  const isLoggedIn = computed(() => !!user.value)
  const isAdmin    = computed(() => user.value?.role === 'ADMIN')
  const isDeveloper = computed(() => user.value?.role === 'DEVELOPER')
  const error = ref(null)


  async function login(credentials) {
  try {
    const res = await userApi.login(credentials)
    user.value = res.data
    sessionStorage.setItem('user', JSON.stringify(res.data))
    return true
  } catch (e) {
    error.value = 'Invalid username or password. Please check your credentials or register first.'
    return false
  }
}

async function register(data) {
  try {
    await userApi.register(data)
    error.value = null
    return true
  } catch (e) {
    error.value = 'Registration failed.'
    return false
  }
}
  function logout() {
    user.value = null
    sessionStorage.removeItem('user')
  }

  return { user, isLoggedIn, isAdmin, isDeveloper, error, login, register, logout }
})
