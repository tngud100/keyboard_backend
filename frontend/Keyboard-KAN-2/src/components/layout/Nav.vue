<template>
  <nav class="nav">
    <ul class="navList">
      <li>
        <router-link class="navItem" to="/keyboard">Keyboard</router-link>
      </li>
      <li><router-link class="navItem" to="/switch">Switch</router-link></li>
      <li><router-link class="navItem" to="/pcb">Pcb</router-link></li>
      <li><router-link class="navItem" to="/keycap">Keycap</router-link></li>
      <li><router-link class="navItem" to="/tool">Tool</router-link></li>
    </ul>
  </nav>
</template>

<script setup></script>

<style scoped>
.nav {
  display: flex;
  padding: 28px 29px;
  justify-content: center;
}

.navList {
  display: flex;
  gap: 62px;
  font-weight: 300;
  font-size: 20px;
  letter-spacing: 1.6px;
}

.navItem {
  text-decoration: none;
  color: #000;
}

.navItem:visited {
  color: #000;
}
</style>
