import { defineStore } from 'pinia'
import { ref } from 'vue'
import { userApi } from '@/api/userApi'

export const useUserStore = defineStore('users', () => {
  const users   = ref([])
  const loading = ref(false)

  async function fetchByRole(role) {
    loading.value = true
    const res = await userApi.getByRole(role)
    users.value = res.data
    loading.value = false
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

  return { users, loading, fetchByRole, fetchByName, fetchBySurname, fetchByNameAndSurname }
})
