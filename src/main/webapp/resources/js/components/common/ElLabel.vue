<template>
  <span
    class="label-text"
    :style="`width: ${width}px`"
  >
    <slot />
  </span>
</template>

<script>
  export default {
    props: {
      width: Number
    }
  }
</script>

<style scoped>
  .label-text {
    padding: 0 15px;
    border: 1px solid #dcdfe6;
    height: 40px;
    line-height: 40px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    display: inline-block;
  }
  .label-text:hover {
    cursor: not-allowed;
  }
</style>
