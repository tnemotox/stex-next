import { getField, updateField } from 'vuex-map-fields';
import moment from 'moment'
moment.locale('ja')

export default {
  state: {
    strategies: [],
    brands: [],
    analysisBrandGroups: [],
    analysisBrandGroupForm: {
      gid: null,
      label: '',
      brands: []
    },
    strategyForm: {
      sid: null,
      label: '',
      gid: null,
      analysisDate: [
        moment().subtract(1, 'years').format('YYYY-MM-DD'),
        moment().format('YYYY-MM-DD'),
      ],
      cards: [],
      entryRules: [],
      exitRules: [],
    },
    ruleForm: {}
  },
  getters: {
    getField
  },
  mutations: {
    updateField,
    initAnalysisBrandForm(state) {
      state.analysisBrandGroupForm = Object.assign(state.analysisBrandGroupForm, {
        gid: null,
        label: '',
        brands: []
      })
    },
    initStrategyForm(state) {
      state.strategyForm = Object.assign(state.strategyForm, {
        sid: null,
        label: '',
        gid: null,
        analysisDate: [
          moment().subtract(1, 'years').format('YYYY-MM-DD'),
          moment().format('YYYY-MM-DD'),
        ],
        cards: [],
        entryRules: [],
        exitRules: [],
      })
    },
    initRules(state, inOrExit) {
      if (inOrExit) {
        state.strategyForm.entryRules.splice(0, state.strategyForm.entryRules.length - 1)
      }
      else {
        state.strategyForm.exitRules.splice(0, state.strategyForm.entryRules.length - 1)
      }
    },
  }
}
