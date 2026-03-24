import { createRouter, createWebHistory } from 'vue-router'
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
    meta: { requiresAdmin: true }
  },
  {
    path: '/events/edit/:id',
    component: () => import('@/views/events/EventFormView.vue'),
    meta: { requiresAdmin: true }
  },
  {
    path: '/users',
    component: () => import('@/views/users/UsersView.vue'),
    meta: { requiresAdmin: true }
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
  } else {
    next()
  }
})

export default router
