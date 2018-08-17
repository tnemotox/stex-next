<template>
  <div id="trade-strategy-area">
    <h2>取引戦略</h2>
    <el-row>
      <el-col :sm="12" :xs="16">
        <el-button
          type="primary"
          size="small"
          plain
          @click="strategyEditDialog = {visible: true}"
        >追加
        </el-button>
      </el-col>
      <el-col :sm="12" :xs="8">
        <el-input
          v-model="strategyTableFilter"
          size="small"
          placeholder="フィルター"
          prefix-icon="el-icon-search"
        />
      </el-col>
    </el-row>
    <el-table
      id="strategy-table"
      :data="filteredStrategies"
      border
    >
      <el-table-column type="expand">
        <template slot-scope="scope">
          {{scope.row.memo}}
        </template>
      </el-table-column>
      <el-table-column
        prop="sid"
        label="ID"
        width="50"
      >
      </el-table-column>
      <el-table-column
        prop="label"
        label="戦略名"
      >
      </el-table-column>
      <el-table-column
        label="分析銘柄グループ"
      >
        <template slot-scope="slot">
          {{analysisBrandGroups.find(g => g.gid === slot.row.gid) ? analysisBrandGroups.find(g => g.gid === slot.row.gid).label : ''}}
        </template>
      </el-table-column>
      <el-table-column
        prop="analysisStartDate"
        label="分析開始日"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="analysisEndDate"
        label="分析終了日"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="analyzedAt"
        label="最終分析日"
        width="120"
      >
      </el-table-column>
      <el-table-column
        label="操作"
        width="220"
      >
        <template slot-scope="slot">
          <el-button
            type="success"
            size="small"
            plain
          >分析
          </el-button>
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
          @click="strategyEditDialog = {visible: true}"
        >追加
        </el-button>
      </el-col>
      <el-col :sm="12">
        <el-pagination
          id="strategy-pagination"
          layout="prev, pager, next"
          :page-size="10"
          :total="strategies.length">
        </el-pagination>
      </el-col>
    </el-row>
    <strategy-edit-dialog
      v-if="strategyEditDialog.visible"
      :strategy="strategyEditDialog.strategy"
      :visible="strategyEditDialog.visible"
      @close="closeDialog"
    />
    <strategy-delete-dialog
      v-if="strategyDeleteDialog.visible"
      :visible="strategyDeleteDialog.visible"
      @close="closeDialog"
    />
  </div>
</template>

<script>
  import { mapFields } from 'vuex-map-fields'
  import moment from 'moment'
  import StrategyEditDialog from './tradeStrategy/StrategyEditDialog'
  import StrategyDeleteDialog from './tradeStrategy/StrategyDeleteDialog'

  export default {

    components: {
      StrategyEditDialog,
      StrategyDeleteDialog
    },

    data() {
      return {
        strategyTableFilter: '',
        strategyEditDialog: {
          visible: false
        },
        strategyDeleteDialog: {
          visible: false
        }
      }
    },

    created() {
      this.reloadTable()
    },

    methods: {

      async reloadTable() {
        const strategies = await this.$http.strategy.$fetch().then(res => res.data)
        this.strategies = [...strategies]
      },

      closeDialog() {
        this.reloadTable()
        this.strategyEditDialog.visible = false
        this.strategyDeleteDialog.visible = false
        this.$store.commit('initStrategyForm')
      },

      /**
       * 取引戦略更新モーダルを表示
       *
       * @param strategy 選択した取引戦略
       */
      async showUpdateDialog(strategy) {
        this.strategyEditDialog = {
          visible: true
        }
        const result = await Promise.all([
          this.$http.card.$fetch(strategy.sid),
          // this.$http.rule.$find(strategy.sid),
          // this.$http.rule.$find(strategy.sid),
        ])
        this.strategyForm = Object.assign({
          // element-uiのため、分析日時を配列に格納
          analysisDate: [
            moment(strategy.analysisStartDate).format(),
            moment(strategy.analysisEndDate).format()
          ],
          cards: result[0].data,
          sid: strategy.sid
        }, strategy)
      },

      showDeleteDialog(strategy) {
        this.strategyDeleteDialog = {
          visible: true
        }
        this.strategyForm = strategy
      }
    },

    computed: {
      ...mapFields([
        'analysisBrandGroups',
        'strategies',
        'strategyForm',
        'paletteForm'
      ]),

      /**
       * 取引戦略テーブルをフィルタリングする
       */
      filteredStrategies: function () {
        return this.strategyTableFilter === ''
          ? this.strategies
          : this.strategies.filter(s => {
            return s.label.includes(this.strategyTableFilter) ||
              s.sid.toString().includes(this.strategyTableFilter) ||
              s.analysisStartDate.includes(this.strategyTableFilter) ||
              s.analysisEndDate.includes(this.strategyTableFilter) ||
              s.analyzedAt.includes(this.strategyTableFilter)
          })
      }
    }
  }

</script>

<style scoped>
  #strategy-pagination {
    text-align: center;
  }

  #strategy-table {
    margin-top: 5px;
    margin-bottom: 10px;
  }

  .el-pagination >>> .el-select .el-input {
    width: 150px !important;
  }
</style>
