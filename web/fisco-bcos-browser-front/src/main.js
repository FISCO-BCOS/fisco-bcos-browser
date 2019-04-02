// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from '@/router'
import promise from 'es6-promise'
import ElementUI from 'element-ui';
import axios from 'axios'
import JsonViewer from "vue-json-viewer"
import 'element-ui/lib/theme-chalk/index.css'
/*iconfont*/
import '@/assets/icon/iconfont.css'
import '@/assets/icon/iconfont.js'
import '@/assets/icon/iconfont_webaas.css'

promise.polyfill();

axios.defaults.headers.post['X-Requested-With'] = 'XMLHttpRequest';
axios.defaults.headers.get['X-Requested-With'] = 'XMLHttpRequest';
axios.defaults.withCredentials = true;
axios.defaults.timeout = 60*1000;
// axios.defaults.baseURL = 'http://10.39.81.201:8081';//可以在此处修改baseURL地址
Vue.prototype.$axios = axios;
Vue.use(router);
Vue.use(JsonViewer)
  
Vue.config.productionTip = false;
Vue.use(ElementUI);
// require('./mock')
/* eslint-disable no-new */
window.eventBus = new Vue();
new Vue({
  el: '#app',
    router,
  template: '<App/>',
  components: { App }
})
