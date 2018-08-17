<template>
  <el-menu
    id="menu"
    :default-active="activeIndex"
    mode="horizontal"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
  >
    <el-menu-item index="1">
      <router-link to="/">ホーム</router-link>
    </el-menu-item>
    <el-menu-item index="2">
      <router-link to="/strategy">取引戦略</router-link>
    </el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    data() {
      return {
        activeIndex: '1'
      }
    }
  }
</script>

<style scoped>
  #menu {
    background-color: #545c64;
    color: #ffffff;
  }

  #menu li {
    padding: 0;
  }

  #menu li a {
    padding: 20px;
  }
</style>
