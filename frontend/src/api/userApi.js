import api from './axiosInstance'

export const userApi = {
  register:           (data)           => api.post('/users/user-register', data),
  login:              (data)           => api.post('/users/user-login', data),
  getByRole:          (role)           => api.get('/users/by-role', { params: { role } }),
  getByName:          (name)           => api.get('/users/by-name', { params: { name } }),
  getBySurname:       (surname)        => api.get('/users/by-surname', { params: { surname } }),
  getByNameAndSurname:(name, surname)  => api.get('/users/by-name-and-surname', { params: { name, surname } })
}
