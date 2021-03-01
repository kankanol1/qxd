<template>
  <div class="app-container course">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <div style="width: 100%;">
          <div class="search-item">
<!--            <label class="el-form-item-label">课程名称</label>-->
            <el-input v-model="query.courseName" clearable placeholder="课程名称" style="width: 230px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
          </div>
          <div class="search-item">
<!--            <label class="el-form-item-label">开课形式</label>-->
            <el-select clearable style="width: 230px;" v-model="query.courseForm" filterable placeholder="请选择" @change="crud.toQuery">
              <el-option
                v-for="item in dict.course_form"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
          <div class="search-item">
<!--            <label class="el-form-item-label">付费方式</label>-->
            <el-select clearable style="width: 230px;" v-model="query.payWay" filterable placeholder="请选择" @change="crud.toQuery">
              <el-option
                v-for="item in dict.pay_way"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
          <div class="search-item">
<!--            <label class="el-form-item-label">课程状态</label>-->
            <el-select clearable style="width: 230px;" v-model="query.enabled" filterable placeholder="请选择" @change="crud.toQuery">
              <el-option
                v-for="item in dict.course_status"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
          <div class="search-item">
<!--            <label class="el-form-item-label">审核状态</label>-->
            <el-select clearable style="width: 230px;" v-model="query.checkStatus" filterable placeholder="请选择" @change="crud.toQuery">
              <el-option
                v-for="item in dict.check_status"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
          <div class="search-item">
<!--            <label class="el-form-item-label">创建日期</label>-->
            <date-range-picker
              v-model="query.createTime"
              start-placeholder="createTimeStart"
              end-placeholder="createTimeStart"
              class="date-item"
            />
          </div>
          <div class="search-item">
<!--            <label class="el-form-item-label">审核时间</label>-->
            <date-range-picker
              v-model="query.checkerTime"
              start-placeholder="checkerTimeStart"
              end-placeholder="checkerTimeStart"
              class="date-item"
            />
          </div>
          <div class="search-item">
            <rrOperation :crud="crud" />
          </div>
        </div>
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="800px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="120px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="课程名称" prop="courseName">
                <el-input v-model="form.courseName" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="课程封面">
                <el-input v-model="form.cover" type="file" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="课程介绍">
                <el-input v-model="form.introduction" :rows="1" type="textarea" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="停售时间">
                <el-date-picker v-model="form.deadlineTime" type="datetime" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="课程金额" prop="money">
                <el-input v-model="form.money" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="预售金额" prop="preMoney">
                <el-input v-model="form.preMoney" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="单次佣金" prop="commission">
                <el-input v-model="form.commission" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="开课形式" prop="courseForm">
                <el-select style="width: 250px;" v-model="form.courseForm" filterable placeholder="请选择">
                  <el-option
                    v-for="item in dict.course_form"
                    :key="item.id"
                    :label="item.label"
                    :value="item.value" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="资源链接" prop="sourceUrl">
                <el-input v-model="form.sourceUrl" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="老师账号" prop="accoutNumber">
                <el-input v-model="form.accoutNumber" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系方式" prop="contactWay">
                <el-input v-model="form.contactWay" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="付费方式" prop="payWay">
                <el-select style="width: 250px;" v-model="form.payWay" filterable placeholder="请选择">
                  <el-option
                    v-for="item in dict.pay_way"
                    :key="item.id"
                    :label="item.label"
                    :value="item.value" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="课程状态" prop="enabled">
                <el-select v-model="form.enabled" filterable placeholder="请选择">
                  <el-option
                    v-for="item in dict.course_status"
                    :key="item.id"
                    :label="item.label"
                    :value="item.value" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column :show-overflow-tooltip="true" prop="courseName" label="课程名称" />
        <el-table-column prop="createTime" label="创建日期" width="180px">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="checkerTime" label="审核时间" width="180px">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.checkerTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="owner" label="拥有者" />
        <el-table-column :show-overflow-tooltip="true" prop="checker" label="审核者" />
        <el-table-column :show-overflow-tooltip="true" prop="cover" label="课程封面" />
        <el-table-column :show-overflow-tooltip="true" prop="introduction" label="课程介绍" />
        <el-table-column prop="deadlineTime" label="停售时间" width="180px">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.deadlineTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="money" label="课程金额" />
        <el-table-column :show-overflow-tooltip="true" prop="preMoney" label="预售金额" />
        <el-table-column :show-overflow-tooltip="true" prop="commission" label="单次佣金" />
        <el-table-column prop="courseForm" label="开课形式">
          <template slot-scope="scope">
            {{ dict.label.course_form[scope.row.courseForm] }}
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="sourceUrl" label="资源链接" />
        <el-table-column :show-overflow-tooltip="true" prop="accoutNumber" label="老师账号" />
        <el-table-column :show-overflow-tooltip="true" prop="contactWay" label="联系方式" />
        <el-table-column prop="payWay" label="付费方式">
          <template slot-scope="scope">
            {{ dict.label.pay_way[scope.row.payWay] }}
          </template>
        </el-table-column>
        <el-table-column prop="enabled" label="课程状态">
          <template slot-scope="scope">
            {{ dict.label.course_status[scope.row.enabled] }}
          </template>
        </el-table-column>
        <el-table-column prop="checkStatus" label="审核状态">
          <template slot-scope="scope">
            {{ dict.label.check_status[scope.row.checkStatus] }}
          </template>
        </el-table-column>
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
import DateRangePicker from '@/components/DateRangePicker'
const defaultForm = { courseId: null, courseName: null, createTime: null, checkerTime: null, owner: null, checker: null, cover: null, introduction: null, deadlineTime: null, money: null, preMoney: null, commission: null, courseForm: null, sourceUrl: null, accoutNumber: null, contactWay: null, payWay: null, enabled: null, courseSort: null, checkStatus: null }
export default {
  name: 'Course',
  components: { DateRangePicker, pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['course_form', 'pay_way', 'course_status', 'check_status'],
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
          { required: true, message: '单次佣金不能为空', trigger: 'blur' }
        ],
        courseForm: [
          { required: true, message: '开课形式不能为空', trigger: 'blur' }
        ],
        sourceUrl: [
          { required: true, message: '资源链接不能为空', trigger: 'blur' }
        ],
        accoutNumber: [
          { required: true, message: '老师账号不能为空', trigger: 'blur' }
        ],
        contactWay: [
          { required: true, message: '联系方式不能为空', trigger: 'blur' }
        ],
        payWay: [
          { required: true, message: '付费方式不能为空', trigger: 'blur' }
        ],
        enabled: [
          { required: true, message: '课程状态不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'courseName', display_name: '课程名称' },
        { key: 'courseForm', display_name: '开课形式' },
        { key: 'payWay', display_name: '付费方式' },
        { key: 'enabled', display_name: '课程状态' },
        { key: 'checkStatus', display_name: '审核状态' }
      ]
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

<style scoped lang="scss">
  .search-item{
    box-sizing: border-box;
    display: inline-block;
    width: 24.5%;
    .el-form-item-label{
      margin-top: 8px !important;
    }
  }
</style>
