import Vuex from 'vuex'
import strategy from './strategy'
import Vue from 'vue'

Vue.use(Vuex)

const store = new Vuex.Store(strategy)

export default store;
