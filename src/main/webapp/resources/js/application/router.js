import Vue from 'vue'
import VueRouter from 'vue-router'

import home from '../components/Home'
import Strategy from '../components/Strategy'

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    component: home
  },
  {
    path: '/strategy',
    component: Strategy
  },
]

new VueRouter({
  routes,
  mode: 'history'
});

export default new VueRouter({routes});
