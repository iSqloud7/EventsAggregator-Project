import api from './axiosInstance'

export const wishlistApi = {
  addToWishlist: (userId, eventId) => api.post(`/wishlist/${userId}/${eventId}`),
  removeFromWishlist: (userId, eventId) => api.delete(`/wishlist/${userId}/${eventId}`),
  getWishlist: (userId) => api.get(`/wishlist/${userId}`),
  isInWishlist: (userId, eventId) => api.get(`/wishlist/${userId}/${eventId}/check`)
}