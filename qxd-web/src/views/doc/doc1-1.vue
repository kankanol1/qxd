<template>
  <div class="test-box">
    <span>测试1</span>
    <span>{{message}}</span>
  </div>
</template>
<script>

  export default {
    inject: ['foo', 'test'],
    created() {
    },
    mounted() {
      console.log(this.$attrs);
    },
    data() {
      return {
        message: 'message'
      }
    }
  }
</script>
<style scoped lang="scss">
  .test-box {
    padding: 30px;
  }
</style>
