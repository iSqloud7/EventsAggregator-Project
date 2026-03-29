import { defineStore } from 'pinia'
import { ref } from 'vue'
import { userApi } from '@/api/userApi'

export const useUserStore = defineStore('users', () => {
  const users   = ref([])
  const loading = ref(false)
  const error   = ref(null)

  async function fetchAll() {
    loading.value = true
    try {
      const res = await userApi.getAll()
      users.value = res.data
    } catch (e) {
      error.value = e.message
    } finally {
      loading.value = false
    }
  }

  async function fetchByRole(role) {
    loading.value = true
    try {
      const res = await userApi.getByRole(role)
      users.value = res.data
    } catch (e) {
      error.value = e.message
    } finally {
      loading.value = false
    }
  }

  async function fetchByName(name) {
    loading.value = true
    const res = await userApi.getByName(name)
    users.value = res.data
    loading.value = false
  }

  async function fetchBySurname(surname) {
    loading.value = true
    const res = await userApi.getBySurname(surname)
    users.value = res.data
    loading.value = false
  }

  async function fetchByNameAndSurname(name, surname) {
    loading.value = true
    const res = await userApi.getByNameAndSurname(name, surname)
    users.value = res.data
    loading.value = false
  }

  return { users, loading, error, fetchAll, fetchByRole, fetchByName, fetchBySurname, fetchByNameAndSurname }
})