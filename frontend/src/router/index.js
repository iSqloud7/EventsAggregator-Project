import { createRouter, createWebHistory} from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const routes = [
  { path: '/',                component: () => import('@/views/HomeView.vue') },
  { path: '/login',           component: () => import('@/views/auth/LoginView.vue') },
  { path: '/register',        component: () => import('@/views/auth/RegisterView.vue') },
  { path: '/events',          component: () => import('@/views/events/EventsView.vue') },
  { path: '/events/:id',      component: () => import('@/views/events/EventDetailView.vue') },
  {
    path: '/events/add',
    component: () => import('@/views/events/EventFormView.vue'),
    meta: { requiresAdminOrDeveloper: true }
  },
  {
    path: '/events/edit/:id',
    component: () => import('@/views/events/EventFormView.vue'),
    meta: { requiresAdminOrDeveloper: true }
  },
  {
    path: '/users',
    component: () => import('@/views/users/UsersView.vue'),
    meta: { requiresAdmin: true }
  },
  {
  path: '/profile',
  component: () => import('@/views/auth/ProfileView.vue'),
  meta: { requiresAuth: true }
},
{
  path: '/stats',
  component: () => import('@/views/stats/StatsView.vue'),
  meta: { requiresAdminOrDeveloper: true }
},
{
  path: '/wishlist',
  component: () => import('@/views/wishlist/WishlistView.vue'),
  meta: { requiresAuth: true }
}
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const auth = useAuthStore()

  if (to.meta.requiresAdmin && !auth.isAdmin) {
    next('/login')
  } else if (to.meta.requiresAdminOrDeveloper && !auth.isAdmin && !auth.isDeveloper) {
    next('/login')
  } else {
    next()
  }
})

export default router
