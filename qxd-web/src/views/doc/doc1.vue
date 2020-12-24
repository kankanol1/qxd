<template>
  <div class="test-box">
    <span>测试1</span>
    <span>{{message1}}</span>
    <Doc11/>
  </div>
</template>
<script>
  import Doc11 from './doc1-1'

  export default {
    components: { Doc11 },
    data() {
      return {
        message1: 'message1'
      }
    },
  }
</script>
<style scoped lang="scss">
  .test-box {
    padding: 30px;
  }
</style>
