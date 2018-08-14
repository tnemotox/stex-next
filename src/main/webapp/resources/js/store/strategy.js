import { getField, updateField } from 'vuex-map-fields';
import moment from 'moment'

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
      rules: {
        in: [],
        exit: []
      }
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
        gid: 1,
        analysisDate: [
          moment().subtract(1, 'years').format('YYYY-MM-DD'),
          moment().format('YYYY-MM-DD'),
        ],
        cards: [],
        rules: {
          in: {},
          exit: {}
        }
      })
    }
  }
}
