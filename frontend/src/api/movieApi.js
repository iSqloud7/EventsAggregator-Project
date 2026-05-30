import api from './axiosInstance'

export const movieApi = {
  getAll:  ()                                => api.get('/movies'),
  getById: (id)                              => api.get(`/movies/${id}`),
  filter:  (city, dateStart, keyword, genre) => api.get('/movies/filter', { params: { city, dateStart, keyword, genre } }),
  create:  (data)                            => api.post('/movies/add-movie', data),
  update:  (id, data)                        => api.put(`/movies/edit-movie/${id}`, data),
  delete:  (id)                              => api.delete(`/movies/delete-movie/${id}`)
}
