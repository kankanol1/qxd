<template>
  <div class="app-container Tables">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <div style="width: 100%;">
          <div class="search-item">
<!--            <label class="el-form-item-label">名称</label>-->
            <el-input v-model="query.testName" clearable placeholder="名称" style="width: 230px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
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
              <el-form-item label="书籍ID">
                <el-input v-model="form.bookId" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="作者ID">
                <el-input v-model="form.authorId" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="名称">
                <el-input v-model="form.testName" style="width: 250px;" />
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
        <el-table-column :show-overflow-tooltip="true" prop="testId" label="测试ID" />
        <el-table-column :show-overflow-tooltip="true" prop="bookId" label="书籍ID" />
        <el-table-column :show-overflow-tooltip="true" prop="authorId" label="作者ID" />
        <el-table-column :show-overflow-tooltip="true" prop="testName" label="名称" />
        <el-table-column :show-overflow-tooltip="true" prop="createBy" label="创建者" />
        <el-table-column :show-overflow-tooltip="true" prop="updateBy" label="更新者" />
        <el-table-column prop="createTime" label="创建日期" width="180px">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="180px">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.updateTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','Tables:edit','Tables:del']" label="操作" width="150px" align="center">
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
  import crudTables from '@/api/test/Tables'
  import CRUD, { presenter, header, form, crud } from '@crud/crud'
  import rrOperation from '@crud/RR.operation'
  import crudOperation from '@crud/CRUD.operation'
  import udOperation from '@crud/UD.operation'
  import pagination from '@crud/Pagination'
  import DateRangePicker from '@/components/DateRangePicker'

  const defaultForm = { testId: null, bookId: null, authorId: null, testName: null, createBy: null, updateBy: null, createTime: null, updateTime: null }
  export default {
    name: 'Tables',
    components: {DateRangePicker, pagination, crudOperation, rrOperation, udOperation },
    mixins: [presenter(), header(), form(defaultForm), crud()],
    cruds() {
      return CRUD({ title: '测试管理', url: 'api/Tables', idField: 'testId', sort: 'testId,desc', crudMethod: { ...crudTables }})
    },
    data() {
      return {
        permission: {
          add: ['admin', 'Tables:add'],
          edit: ['admin', 'Tables:edit'],
          del: ['admin', 'Tables:del']
        },
        rules: {
        },
        queryTypeOptions: [
          { key: 'testName', display_name: '名称' }
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
