import Vue from 'vue'
import Element from 'element-ui'
import sanitizeHTML from 'sanitize-html'

import App from './App'
import router from './router'
import store from '../store/index'

import locale from 'element-ui/lib/locale/lang/ja'
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/display.css';

import '../../css/index.css'

import $http from '../api/Api'

Vue.use(Element, { locale })
Vue.prototype.$sanitize = sanitizeHTML
Vue.prototype.$http = $http

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
