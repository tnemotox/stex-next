<template>
  <div id="analysis-brand-group-area">
    <h2>分析銘柄グループ</h2>
    <el-row>
      <el-col :sm="12" :xs="16">
        <el-button
          type="primary"
          size="small"
          plain
          @click="analysisBrandGroupEditDialog = {visible: true}"
        >追加
        </el-button>
      </el-col>
      <el-col :sm="12" :xs="8">
        <el-input
          v-model="analysisBrandGroupTableFilter"
          size="small"
          placeholder="フィルター"
          prefix-icon="el-icon-search"
        />
      </el-col>
    </el-row>
    <el-table
      id="analysis-brand-group-table"
      :data="filteredAnalysisBrandGroup"
      border
    >
      <el-table-column
        prop="gid"
        label="ID"
        width="50"
      >
      </el-table-column>
      <el-table-column
        prop="label"
        label="分析銘柄グループ名"
      >
      </el-table-column>
      <el-table-column
        label="操作"
        width="150"
      >
        <template slot-scope="slot">
          <el-button
            type="primary"
            size="small"
            plain
            @click="showUpdateDialog(slot.row)"
          >更新
          </el-button>
          <el-button
            type="danger"
            size="small"
            plain
            @click="showDeleteDialog(slot.row)"
          >削除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-row>
      <el-col :sm="6">
        <el-button
          type="primary"
          size="small"
          plain
          @click="analysisBrandGroupEditDialog = {visible: true}"
        >追加
        </el-button>
      </el-col>
      <el-col :sm="12">
        <el-pagination
          id="analysis-brand-group-pagination"
          layout="prev, pager, next"
          :page-size="10"
          :total="analysisBrandGroups.length">
        </el-pagination>
      </el-col>
    </el-row>
    <analysis-brand-group-edit-dialog
      :visible="analysisBrandGroupEditDialog.visible"
      @close="closeDialog"
    />
    <analysis-brand-group-delete-dialog
      :visible="analysisBrandGroupDeleteDialog.visible"
      @close="closeDialog"
    />
  </div>
</template>

<script>
  import { mapFields } from 'vuex-map-fields'
  import AnalysisBrandGroupEditDialog from './analysisBrandGroup/AnalysisBrandGroupEditDialog'
  import AnalysisBrandGroupDeleteDialog from './analysisBrandGroup/AnalysisBrandGroupDeleteDialog'

  export default {

    components: {
      AnalysisBrandGroupDeleteDialog,
      AnalysisBrandGroupEditDialog
    },

    data() {
      return {
        analysisBrandGroups: [],
        analysisBrandGroupTableFilter: '',
        analysisBrandGroupEditDialog: {
          visible: false
        },
        analysisBrandGroupDeleteDialog: {
          visible: false
        }
      }
    },

    created() {
      this.reloadTable()
    },

    methods: {

      async reloadTable() {
        const analysisBrandGroups = await this.$http.analysisBrandGroup.$fetch().then(res => res.data)
        this.analysisBrandGroups = [...analysisBrandGroups]
      },

      closeDialog() {
        this.reloadTable()
        this.analysisBrandGroupEditDialog.visible = false
        this.analysisBrandGroupDeleteDialog.visible = false
        this.$store.commit('initAnalysisBrandForm')
      },

      showUpdateDialog(analysisBrandGroup) {
        this.analysisBrandGroupEditDialog.visible = true
        this.analysisBrandGroupForm = Object.assign({}, analysisBrandGroup)
      },

      showDeleteDialog(analysisBrandGroup) {
        this.analysisBrandGroupDeleteDialog.visible = true
        this.analysisBrandGroupForm = analysisBrandGroup
      }
    },

    computed: {
      ...mapFields({
        analysisBrandGroupForm: 'analysisBrandGroupForm',
      }),

      filteredAnalysisBrandGroup: function() {
        return this.analysisBrandGroupTableFilter === ''
          ? this.analysisBrandGroups
          : this.analysisBrandGroups.filter(s => {
            return s.label.includes(this.analysisBrandGroupTableFilter) ||
              s.gid.toString().includes(this.analysisBrandGroupTableFilter)
          })
      }
    }
  }

</script>

<style scoped>
  #analysis-brand-group-area {
    margin-top: 50px;
  }

  #analysis-brand-group-pagination {
    text-align: center;
  }

  #analysis-brand-group-table {
    margin-top: 5px;
    margin-bottom: 10px;
  }

  .el-pagination >>> .el-select .el-input {
    width: 150px !important;
  }
</style>
