<template>
  <div class="app-container Author">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <div style="width: 100%;">
         <!-- <div class="search-item">
&lt;!&ndash;            <label class="el-form-item-label">作者ID</label>&ndash;&gt;
            <el-input v-model="query.authorId" clearable placeholder="作者ID" style="width: 230px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
          </div>-->
          <div class="search-item">
<!--            <label class="el-form-item-label">作者名称</label>-->
            <el-input v-model="query.authorName" clearable placeholder="作者名称" style="width: 230px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
          </div>
          <div class="search-item">
<!--            <label class="el-form-item-label">作者性别</label>-->
            <el-select clearable style="width: 230px;" v-model="query.authorSex" filterable placeholder="请选择" @change="crud.toQuery">
              <el-option
                v-for="item in dict.sex_status"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
          <div class="search-item">
<!--            <label class="el-form-item-label">作者出生日期</label>-->
            <date-range-picker
              v-model="query.authorDate"
              start-placeholder="authorDateStart"
              end-placeholder="authorDateStart"
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
              <el-form-item label="作者名称">
                <el-input v-model="form.authorName" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="作者出生日期">
                <el-date-picker v-model="form.authorDate" type="datetime" style="width: 250px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="作者性别">
                <el-select style="width: 250px;" v-model="form.authorSex" filterable placeholder="请选择">
                  <el-option
                    v-for="item in dict.sex_status"
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
        <el-table-column :show-overflow-tooltip="true" prop="authorId" label="作者ID" />
        <el-table-column :show-overflow-tooltip="true" prop="authorName" label="作者名称" />
        <el-table-column prop="authorDate" label="作者出生日期" width="180px">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.authorDate) }}</span>
          </template>
        </el-table-column>
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
        <el-table-column prop="authorSex" label="作者性别">
          <template slot-scope="scope">
            {{ dict.label.sex_status[scope.row.authorSex] }}
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','Author:edit','Author:del']" label="操作" width="150px" align="center">
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
  import crudAuthor from '@/api/test/Author'
  import CRUD, { presenter, header, form, crud } from '@crud/crud'
  import rrOperation from '@crud/RR.operation'
  import crudOperation from '@crud/CRUD.operation'
  import udOperation from '@crud/UD.operation'
  import pagination from '@crud/Pagination'
  import DateRangePicker from '@/components/DateRangePicker'

  const defaultForm = { authorId: null, authorName: null, authorDate: null, createBy: null, updateBy: null, createTime: null, updateTime: null, authorSex: null }
  export default {
    name: 'Author',
    components: {DateRangePicker, pagination, crudOperation, rrOperation, udOperation },
    mixins: [presenter(), header(), form(defaultForm), crud()],
    dicts: ['sex_status'],
    cruds() {
      return CRUD({ title: '作者管理', url: 'api/Author', idField: 'authorId', sort: 'authorId,desc', crudMethod: { ...crudAuthor }})
    },
    data() {
      return {
        permission: {
          add: ['admin', 'Author:add'],
          edit: ['admin', 'Author:edit'],
          del: ['admin', 'Author:del']
        },
        rules: {
        },
        queryTypeOptions: [
          { key: 'authorId', display_name: '作者ID' },
          { key: 'authorName', display_name: '作者名称' },
          { key: 'authorSex', display_name: '作者性别' }
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
