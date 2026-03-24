<template>
  <footer class="footer">
    <div class="container footer-inner">
      <span class="logo-text">EVENTS<span class="accent">MK</span></span>
      <span class="copy">© {{ new Date().getFullYear() }} EventsMK. All rights reserved.</span>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  border-top: 1px solid var(--border);
  padding: 1.5rem 0;
  margin-top: auto;
}

.footer-inner {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
  flex-wrap: wrap;
}

.logo-text {
  font-family: var(--font-display);
  font-size: 1.2rem;
  letter-spacing: 0.08em;
}

.accent { color: var(--accent); }

.copy {
  font-size: 0.8rem;
  color: var(--text-muted);
}
</style>
