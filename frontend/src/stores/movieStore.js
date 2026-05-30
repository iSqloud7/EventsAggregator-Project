import { defineStore } from 'pinia'
import { ref } from 'vue'
import { movieApi } from '@/api/movieApi'

export const useMovieStore = defineStore('movies', () => {
  const movies       = ref([])
  const currentMovie = ref(null)
  const loading      = ref(false)
  const error        = ref(null)

  async function fetchAll() {
    loading.value = true
    try {
      const res = await movieApi.getAll()
      movies.value = res.data
    } catch (e) {
      error.value = e.message
    } finally {
      loading.value = false
    }
  }

  async function fetchById(id) {
    loading.value = true
    try {
      const res = await movieApi.getById(id)
      currentMovie.value = res.data
    } catch (e) {
      currentMovie.value = null
    } finally {
      loading.value = false
    }
  }

  async function filterMovies(city, dateStart, keyword, genre) {
    loading.value = true
    try {
      const res = await movieApi.filter(city, dateStart, keyword, genre)
      movies.value = res.data
    } finally {
      loading.value = false
    }
  }

  async function deleteMovie(id) {
    await movieApi.delete(id)
    movies.value = movies.value.filter(m => m.id !== id)
  }

  async function createMovie(data) {
    loading.value = true
    try {
      const res = await movieApi.create(data)
      movies.value.push(res.data)
      return true
    } catch (e) {
      error.value = e.message
      return false
    } finally {
      loading.value = false
    }
  }

  async function updateMovie(id, data) {
    loading.value = true
    try {
      const res = await movieApi.update(id, data)
      const index = movies.value.findIndex(m => m.id === Number(id))
      if (index !== -1) movies.value[index] = res.data
      currentMovie.value = res.data
      return true
    } catch (e) {
      error.value = e.message
      return false
    } finally {
      loading.value = false
    }
  }

  function clearCurrentMovie() {
    currentMovie.value = null
  }

  return {
    movies, currentMovie, loading, error,
    fetchAll, fetchById, filterMovies,
    deleteMovie, createMovie, updateMovie, clearCurrentMovie
  }
})
