<script setup>
const props = defineProps({
  event: Object
})

const emit = defineEmits(["read-more"])

function shortText(text) {
  if (!text) return ""
  return text.length > 120 ? text.slice(0, 120) + "..." : text
}
</script>

<template>
  <div class="card">
    <img :src="event.image" class="img" />

    <h3>{{ event.title }}</h3>

    <p class="desc">{{ shortText(event.description) }}</p>

    <button @click="emit('read-more', event)">
      Прочитај повеќе
    </button>
  </div>
</template>

<style scoped>
.card {
  width: 300px;
  height: 440px;
  border-radius: 16px;
  overflow: hidden;
  background: #18181b;
  color: #f4f4f5;
  display: flex;
  flex-direction: column;
  transition: transform .2s ease, box-shadow .2s ease;
  cursor: pointer;
}

.card:hover {
  transform: translateY(-6px);
  box-shadow: 0 10px 30px rgba(0,0,0,.35);
}

.img {
  width: 100%;
  height: 170px;
  object-fit: cover;
}

.card h3 {
  font-size: 16px;
  font-weight: 600;
  padding: 12px 14px 0;
  line-height: 1.3;
  min-height: 44px;
}

.desc {
  flex: 1;
  font-size: 13px;
  opacity: .85;
  padding: 8px 14px;
  line-height: 1.5;
}

button {
  margin: 12px;
  padding: 10px;
  border-radius: 10px;
  border: none;
  background: #6366f1;
  color: white;
  font-weight: 600;
  cursor: pointer;
  transition: background .2s ease;
}

button:hover {
  background: #4f46e5;
}
</style>