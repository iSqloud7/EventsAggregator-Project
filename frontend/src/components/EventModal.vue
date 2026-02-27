<script setup>
const props = defineProps({
  event: Object
})

const emit = defineEmits(["close"])
</script>

<template>
  <div class="overlay" @click.self="emit('close')">
    <div class="modal">
      <img :src="event.image" />

      <h2>{{ event.title }}</h2>

      <p>{{ event.description }}</p>

      <button @click="emit('close')">Затвори</button>
    </div>
  </div>
</template>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,.7);
  backdrop-filter: blur(4px);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal {
  width: 680px;
  max-height: 85vh;
  overflow: auto;
  background: #18181b;
  padding: 22px;
  border-radius: 18px;
  color: #f4f4f5;
  box-shadow: 0 20px 60px rgba(0,0,0,.5);
}

.modal h2 {
  margin: 14px 0;
}

.modal p {
  line-height: 1.6;
  opacity: .9;
}

.modal button {
  margin-top: 16px;
}
</style>