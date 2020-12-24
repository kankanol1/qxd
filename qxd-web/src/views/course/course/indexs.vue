<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="800px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="120px">
          <el-form-item label="课程名称" prop="courseName">
            <el-input v-model="form.courseName" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="课程封面">
            <el-input v-model="form.cover" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="课程介绍">
            <el-input v-model="form.introduction" :rows="1" type="textarea" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="截止日期">
            <el-date-picker v-model="form.deadlineTime" type="datetime" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="课程金额" prop="money">
            <el-input v-model="form.money" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="预售金额" prop="preMoney">
            <el-input v-model="form.preMoney" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="单人次佣金" prop="commission">
            <el-input v-model="form.commission" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="开课形式">
            <el-select v-model="form.courseForm" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.course_form"
                :key="item.id+'courseForm'"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="资源链接" prop="sourceUrl">
            <el-input v-model="form.sourceUrl" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="开课老师账号" prop="accoutNumber">
            <el-input v-model="form.accoutNumber" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="紧急联系方式" prop="contactWay">
            <el-input v-model="form.contactWay" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="付费方式" prop="payWay">
            <el-select v-model="form.payWay" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.pay_way"
                :key="item.id+'payWay'"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态" prop="enabled">
            <el-select v-model="form.enabled" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.course_status"
                :key="item.id+'enabled'"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="排序">
            <el-input v-model="form.courseSort" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="审核状态" prop="checkStatus">
            <el-input v-model="form.checkStatus" style="width: 250px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="courseName" label="课程名称" />
        <el-table-column prop="cover" label="课程封面" />
        <el-table-column prop="introduction" label="课程介绍">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" placement="top">
              <div slot="content" style="width: 200px">{{ scope.row.introduction }}</div>
              <div class="el-title-pop">{{ scope.row.introduction }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="deadlineTime" label="截止日期">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.deadlineTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="money" label="课程金额" />
        <el-table-column prop="preMoney" label="预售金额" />
        <el-table-column prop="commission" label="单人次佣金" />
        <el-table-column prop="courseForm" label="开课形式">
          <template slot-scope="scope">
            {{ dict.label.course_form[scope.row.courseForm] }}
          </template>
        </el-table-column>
        <el-table-column prop="sourceUrl" label="资源链接" />
        <el-table-column prop="accoutNumber" label="开课老师账号">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" placement="top">
              <div slot="content">{{ scope.row.payWaaccoutNumber }}</div>
              <div class="el-title-pop">{{ scope.row.accoutNumber }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="contactWay" label="紧急联系方式" />
        <el-table-column prop="payWay" label="付费方式">
          <template slot-scope="scope">
            {{ dict.label.pay_way[scope.row.payWay] }}
          </template>
        </el-table-column>
        <el-table-column prop="enabled" label="状态">
          <template slot-scope="scope">
            {{ dict.label.course_status[scope.row.enabled] }}
          </template>
        </el-table-column>
        <el-table-column prop="courseSort" label="排序" />
        <el-table-column prop="checkStatus" label="审核状态" />
        <el-table-column v-permission="['admin','course:edit','course:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudCourse from '@/api/qxd/course'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { courseId: null, courseName: null, createTime: null, checkerTime: null, owner: null, checker: null, cover: null, introduction: null, deadlineTime: null, money: null, preMoney: null, commission: null, courseForm: null, sourceUrl: null, accoutNumber: null, contactWay: null, payWay: null, enabled: null, courseSort: null, checkStatus: null }
export default {
  name: 'Course',
  // eslint-disable-next-line vue/no-unused-components
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['course_form', 'pay_way', 'course_status'],
  cruds() {
    return CRUD({ title: '课程', url: 'api/course', idField: 'courseId', sort: 'courseId,desc', crudMethod: { ...crudCourse }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'course:add'],
        edit: ['admin', 'course:edit'],
        del: ['admin', 'course:del']
      },
      rules: {
        courseName: [
          { required: true, message: '课程名称不能为空', trigger: 'blur' }
        ],
        money: [
          { required: true, message: '课程金额不能为空', trigger: 'blur' }
        ],
        preMoney: [
          { required: true, message: '预售金额不能为空', trigger: 'blur' }
        ],
        commission: [
          { required: true, message: '单人次佣金不能为空', trigger: 'blur' }
        ],
        sourceUrl: [
          { required: true, message: '资源链接不能为空', trigger: 'blur' }
        ],
        accoutNumber: [
          { required: true, message: '开课老师账号不能为空', trigger: 'blur' }
        ],
        contactWay: [
          { required: true, message: '紧急联系方式不能为空', trigger: 'blur' }
        ],
        payWay: [
          { required: true, message: '付费方式不能为空', trigger: 'blur' }
        ],
        enabled: [
          { required: true, message: '状态：1启用、0禁用不能为空', trigger: 'blur' }
        ],
        checkStatus: [
          { required: true, message: '审核状态不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style lang="scss" scoped>
    .el-form-item.el-form-item--small{
      width: 48%;
      display: inline-block;
    }
    .el-select.el-select--small{
      width: 250px;
    }
  .el-title-pop{
    overflow:hidden;
    white-space:nowrap;
    text-overflow:ellipsis;
  }
</style>
