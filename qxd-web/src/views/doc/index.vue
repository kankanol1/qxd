<template>
  <div>
    <el-button @click="changeStatus">切换</el-button>
    <div>{{message}}</div>
   <!-- <keep-alive>
      <template v-if="currentTabComponent">
        <doc @click="testFun" :data="message" :kankan="message">
          <template v-slot:header>
            <h1>About Me</h1>
          </template>
          <p>Here's some page content, which will be included in vm.$slots.default, because it's not inside a named slot.</p>
          <template v-slot:footer>
            <p>Copyright 2016 Evan You</p>
          </template>
        </doc>
      </template>
      <template v-else>
        <doc1/>
      </template>
    </keep-alive>-->
    <doc/>
  </div>
</template>

<script>
import Doc from './doc'
import Doc1 from './doc1'

const mixin = {
  created: function() {  }
}
export default {
  name: 'Index',
  components: { Doc, Doc1 },
  mixins: [mixin],
  provide: {
    foo: 'bar',
    test() {
      this.message = 'messaghahafhe'
    }
  },
  customOption: 'foo',
  data() {
    return {
      currentTabComponent: true,
      message: 'currentTabComponent'
    }
  },
  methods: {
    testFun() {
      console.log(121)
    },
    changeStatus() {
      this.currentTabComponent = !this.currentTabComponent
    }
  }
}
</script>

<style scoped>

</style>
