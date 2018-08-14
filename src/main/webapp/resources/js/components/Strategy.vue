<template>
  <div>
    <trade-strategy />
    <analysis-brand-group />
  </div>
</template>

<script>
  import { mapFields } from 'vuex-map-fields'
  import TradeStrategy from './strategy/TradeStrategy'
  import AnalysisBrandGroup from './strategy/AnalysisBrandGroup'

  export default {
    components: {
      TradeStrategy,
      AnalysisBrandGroup
    },
    async created() {
      const response = await this.$http.brand.$fetch()
      this.brands = response.data
    },
    computed: {
      ...mapFields({
        brands: 'brands',
      }),
    }
  }


</script>

<style scoped>
</style>
