// https://www.jianshu.com/p/cb4438e9be3a
import Vue from 'vue'

const vfocus = {
  bind: function(el, binding, vnode, oldVnode) {
    console.log(el, binding, vnode, oldVnode)
    console.log('1-bind')
    // console.log(el.parentNode)
    var s = JSON.stringify
    el.innerHTML =
      'name: ' + s(binding.name) + '<br>' +
      'value: ' + s(binding.value) + '<br>' +
      'expression: ' + s(binding.expression) + '<br>' +
      'argument: ' + s(binding.arg) + '<br>' +
      'modifiers: ' + s(binding.modifiers) + '<br>' +
      'vnode keys: ' + Object.keys(vnode).join(', ')
  },
  inserted:

    function() {
      console.log('2-insert')
    }

  ,
  update: function() {
    console.log('3-update')
  }
  ,
  componentUpdated: function() {
    console.log('4 - componentUpdated')
  }
  ,
  unbind: function() {
    console.log('5 - unbind')
  }
}
// Vue.directive('test', focus)

const install = function(Vue) {
  Vue.directive('test', vfocus)
}

if (window.Vue) {
  window['test'] = vfocus
  Vue.use(install) // eslint-disable-line
}

vfocus.install = install
export default vfocus
