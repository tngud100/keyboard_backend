<script setup>
import Header from "#/layout/Header.vue";
import Nav from "#/layout/Nav.vue";
import Footer from "#/layout/Footer.vue";
</script>

<template>
  <div>
    <Header />
    <section class="contents">
      <Nav />
      <router-view />
    </section>
    <Footer />
  </div>
</template>

<style scoped>
.contents {
  padding: 0 20px;
}
</style>
