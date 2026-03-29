import api from './axiosInstance'

export const statsApi = {
  getStats: () => api.get('/stats')
}