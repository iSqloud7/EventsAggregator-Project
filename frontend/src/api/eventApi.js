import api from './axiosInstance'

export const eventApi = {
  getAll: ()                         => api.get('/events'),
  getById: (id)                      => api.get(`/events/${id}`),
  filter: (city, dateStart, keyword) => api.get('/events/filter', { params: { city, dateStart, keyword } }),
  create: (data)                     => api.post('/events/add-event', data),
  update: (id, data)                 => api.put(`/events/edit-event/${id}`, data),
  delete: (id)                       => api.delete(`/events/delete-event/${id}`)
}
