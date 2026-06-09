import api from './axiosInstance'

export const wishlistApi = {
  addToWishlist: (userId, itemId, type) =>
      api.post(`/wishlist/${userId}/${itemId}?type=${type}`),

  removeFromWishlist: (userId, itemId, type) =>
      api.delete(`/wishlist/${userId}/${itemId}?type=${type}`),

  getWishlist: (userId) =>
      api.get(`/wishlist/${userId}`)
}