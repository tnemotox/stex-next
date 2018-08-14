<template>
    <div>
        <page-header />
        <page-layout />
    </div>
</template>

<script>
  import PageHeader from '../components/common/PageHeader'
  import PageLayout from '../components/common/PageLayout'
  export default {
    name: 'app',
    components: {
      PageHeader,
      PageLayout
    }
  }
</script>

<style>

</style>
