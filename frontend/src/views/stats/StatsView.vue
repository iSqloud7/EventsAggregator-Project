<template>
  <div class="stats-page">
    <div class="container">
      <div class="page-header fade-up-1">
        <h1 class="page-title">APP <span class="accent">STATISTICS</span></h1>
      </div>

      <div v-if="loading" class="loading-spinner">
        <div class="spinner"></div>
      </div>

      <template v-else>
        <div class="stats-grid fade-up-2">
          <div class="stat-card">
            <div class="stat-value">{{ stats.totalEvents }}</div>
            <div class="stat-label">Total Events</div>
          </div>
          <div class="stat-card">
            <div class="stat-value">{{ stats.totalUsers }}</div>
            <div class="stat-label">Total Users</div>
          </div>
          <div class="stat-card">
            <div class="stat-value">{{ stats.totalWishlists }}</div>
            <div class="stat-label">Total Wishlists</div>
          </div>
        </div>

        <div class="charts-grid fade-up-3">
          <!-- Настани по месец -->
          <div class="chart-card">
            <h2 class="chart-title">Events by Month</h2>
            <Bar :data="monthChartData" :options="chartOptions" />
          </div>

          <div class="chart-card">
            <h2 class="chart-title">Users by Role</h2>
            <Pie :data="roleChartData" :options="pieOptions" />
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { Bar, Pie } from 'vue-chartjs'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  ArcElement,
  Title,
  Tooltip,
  Legend
} from 'chart.js'
import { statsApi } from '@/api/statsApi'

ChartJS.register(CategoryScale, LinearScale, BarElement, ArcElement, Title, Tooltip, Legend)

const loading = ref(false)
const stats = ref({
  totalEvents: 0,
  totalUsers: 0,
  totalWishlists: 0,
  eventsByMonth: {},
  usersByRole: {}
})

const monthNames = {
  '01': 'Jan', '02': 'Feb', '03': 'Mar', '04': 'Apr',
  '05': 'May', '06': 'Jun', '07': 'Jul', '08': 'Aug',
  '09': 'Sep', '10': 'Oct', '11': 'Nov', '12': 'Dec'
}

const monthChartData = computed(() => ({
  labels: Object.keys(stats.value.eventsByMonth || {}).map(m => monthNames[m] || m),
  datasets: [{
    label: 'Events',
    data: Object.values(stats.value.eventsByMonth || {}),
    backgroundColor: 'rgba(232, 255, 71, 0.7)',
    borderColor: 'rgba(232, 255, 71, 1)',
    borderWidth: 1,
    borderRadius: 6
  }]
}))

const roleChartData = computed(() => ({
  labels: Object.keys(stats.value.usersByRole || {}),
  datasets: [{
    data: Object.values(stats.value.usersByRole || {}),
    backgroundColor: [
      'rgba(232, 255, 71, 0.8)',
      'rgba(99, 102, 241, 0.8)',
      'rgba(34, 197, 94, 0.8)'
    ],
    borderWidth: 0
  }]
}))

const chartOptions = {
  responsive: true,
  plugins: {
    legend: { display: false },
    tooltip: { callbacks: { label: ctx => ` ${ctx.raw} events` } }
  },
  scales: {
    x: { ticks: { color: '#a1a1aa' }, grid: { color: 'rgba(255,255,255,0.05)' } },
    y: { ticks: { color: '#a1a1aa' }, grid: { color: 'rgba(255,255,255,0.05)' }, beginAtZero: true }
  }
}

const pieOptions = {
  responsive: true,
  plugins: {
    legend: {
      position: 'bottom',
      labels: { color: '#a1a1aa', padding: 20 }
    }
  }
}

onMounted(async () => {
  loading.value = true
  try {
    const res = await statsApi.getStats()
    stats.value = res.data
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.stats-page { padding: 3rem 0 5rem; }

.page-header { margin-bottom: 2rem; }

.accent { color: var(--accent); }

.stats-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.stat-card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: 16px;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.75rem;
  text-align: center;
  transition: all 0.25s ease;
}

.stat-card:hover {
  border-color: var(--accent);
  transform: translateY(-4px);
  box-shadow: 0 12px 40px rgba(232, 255, 71, 0.08);
}

.stat-icon { font-size: 2.5rem; }

.stat-value {
  font-family: var(--font-display);
  font-size: 3rem;
  font-weight: 700;
  color: var(--accent);
}

.stat-label {
  font-size: 0.85rem;
  text-transform: uppercase;
  letter-spacing: 0.08em;
  color: var(--text-muted);
}

.charts-grid {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 1.5rem;
}

.chart-card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: 16px;
  padding: 1.5rem;
}

.chart-title {
  font-family: var(--font-display);
  font-size: 1rem;
  letter-spacing: 0.05em;
  margin-bottom: 1.5rem;
  color: var(--text-muted);
  text-transform: uppercase;
}

@media (max-width: 768px) {
  .stats-grid { grid-template-columns: 1fr; }
  .charts-grid { grid-template-columns: 1fr; }
}
</style>