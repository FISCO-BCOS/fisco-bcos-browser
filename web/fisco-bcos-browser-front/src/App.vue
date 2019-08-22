<template>
  <div id="app" class="web-font">
      <router-view ></router-view>
  </div>
</template>

<script>
import '@/assets/css/base.css'
export default {
    name: 'app',
}
</script>

<style>
#app {
    background-color: #2a2c3b;
    font-size: 14px;
    height: 100%;
}
</style>
