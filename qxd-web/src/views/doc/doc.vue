<template>
  <div class="test-box">
<!--    <slot></slot>
    <slot name="header">
      <p>jisnin</p>
    </slot>
    <slot name="footer">
      <p>jisnin</p>
    </slot>
    <span>哈哈{{data}}</span>
    <input v-model="message" type="text">
    <Doc11/>
      <el-button @click="example">example</el-button>
    <span v-text="message"></span>

    <div @click="rightFun">
      <el-button
        @click.stop.prevent="middleFun"
      >
        按钮
      </el-button>
      测试
    </div>
    <Doc11 :[key]="message"></Doc11>
    <span v-pre>{{ this will not be compiled }}</span>
    <div v-cloak>
      {{ message }}
    </div>
    <span v-once>This will never change: {{message}}</span>
    <el-button @click="changeTab">切换</el-button>
    <component :is="components"></component>-->
    <transition name="fade" mode="out-in" appear>
      <component :is="components"></component>
    </transition>
  </div>
</template>
<script>
import Doc11 from './doc1-1'
import Doc1 from './doc1'
export default {
  delimiters: ['${', '}'],
  components: { Doc11, Doc1},
  watch: {
    message: {
      handler: (newVal, oldVal) => {
        console.log(newVal, oldVal)
      },
      deep: true,
      // immediate: true//立即出发回调
    }
  },
  props: {
    data: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      components: 'Doc11',
      message: 'message',
      key: 'sss'
    }
  },
  mounted() {
    // console.log(this.$el)
    // console.log(this.$options)
    // console.log(this.$parent)
    // console.log(this.$root)
    // console.log(this.$children)
    // console.log(this.$slots)
    // console.log(this.$scopedSlots.footer())
    // console.log(this.$isServer)
    // console.log(this.$attrs)
    // console.log(this.$listeners)
    // this.$listeners.click()
    // console.log(this.$listeners)
    // this.$set(this, 'message', '休息西')
    // this.$delete(this, 'message')
    // console.log(this.$options)
    this.$on('test', (msg) => {
      // console.log(msg)
    })
    this.$off('test')
    this.$emit('test', 'hi')
    this.$once('test', 'hi')
  },
  methods: {
    changeTab(){
      this.components = this.components === 'Doc1' ? 'Doc11' : 'Doc1'
    },
    leftFun(){console.log('leftFun')},
    rightFun(){console.log('rightFun')},
    middleFun(){console.log('middleFun')},
    example: function() {
      // 修改数据
      this.message = 'changed'
      // DOM 还没有更新
      this.$nextTick(function() {
        // DOM 现在更新了
        // `this` 绑定到当前实例
        // console.log(this.message)
      })//
    }
  }
}
</script>
<style scoped lang="scss">
  .test-box {
    padding: 30px;
  }
</style>
