import api from './axiosInstance'

export const theaterApi = {
    getAll: ()                         => api.get('/theater-shows'),
    getById: (id)                      => api.get(`/theater-shows/${id}`),
    create: (data)                     => api.post('/theater-shows/add-show', data),
    update: (id, data)                 => api.put(`/theater-shows/edit-show/${id}`, data),
    delete: (id)                       => api.delete(`/theater-shows/delete-show/${id}`)
}