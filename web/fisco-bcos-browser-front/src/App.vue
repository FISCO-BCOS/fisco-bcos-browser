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
    background-image: url(./assets/images/bg.jpg);
    background-repeat: no-repeat;
    background-attachment:fixed;
    background-size:100% 100%;
    /* background-position:center; */
    font-size: 14px;
    height: 100%;
    box-sizing: border-box;
    overflow: auto;
}
</style>
