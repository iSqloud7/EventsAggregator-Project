import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { userApi } from '@/api/userApi'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))

  const isLoggedIn = computed(() => !!user.value)
  const isAdmin    = computed(() => user.value?.role === 'ADMIN')

  async function login(credentials) {
    const res = await userApi.login(credentials)
    user.value = res.data
    localStorage.setItem('user', JSON.stringify(res.data))
  }

  async function register(data) {
    const res = await userApi.register(data)
    user.value = res.data
    localStorage.setItem('user', JSON.stringify(res.data))
  }

  function logout() {
    user.value = null
    localStorage.removeItem('user')
  }

  return { user, isLoggedIn, isAdmin, login, register, logout }
})
