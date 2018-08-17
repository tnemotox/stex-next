<template>
  <div>
    <h2>取引戦略カード</h2>
    <el-row>
      <el-col :sm="12" :xs="16">
        <el-button
          type="primary"
          size="small"
          plain
          @click="showCardCreatorEditDialog(null)"
        >追加
        </el-button>
      </el-col>
      <el-col :sm="12" :xs="8">
        <el-input
          v-model="cardTableFilter"
          size="small"
          placeholder="フィルター"
          prefix-icon="el-icon-search"
        />
      </el-col>
    </el-row>
    <el-table
      id="card-table"
      :data="filteredCards"
      border
    >
      <el-table-column label="ID" width="50">
        <template slot-scope="slot">
          {{slot.row.cid}}
        </template>
      </el-table-column>
      <el-table-column label="概要">
        <template slot-scope="slot">
          {{slot.row.label}}
        </template>
      </el-table-column>
      <el-table-column label="利用フラグ">
        <template slot-scope="slot">
          {{slot.row.used ? '使用中' : '未使用'}}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="slot">
          <el-button
            type="primary"
            size="small"
            plain
            @click="showCardCreatorEditDialog(slot.row)"
          >更新
          </el-button>
          <el-button
            type="danger"
            size="small"
            plain
            @click="showCardCreatorDeleteDialog(slot.row)"
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
          @click="showCardCreatorEditDialog(null)"
        >追加
        </el-button>
      </el-col>
      <el-col :sm="12">
        <el-pagination
          id="card-pagination"
          layout="prev, pager, next"
          :page-size="10"
          :total="cards.length"
        >
        </el-pagination>
      </el-col>
    </el-row>
    <card-creator-edit-dialog
      v-if="cardCreatorEditDialog.visible"
      id="card-creator-edit-dialog"
      :card="cardCreatorEditDialog.card"
      :visible="cardCreatorEditDialog.visible"
      @close="closeDialog"
    />
    <card-creator-delete-dialog
      v-if="cardCreatorDeleteDialog.visible"
      id="card-creator-delete-dialog"
      :label="cardCreatorDeleteDialog.label"
      :cid="cardCreatorDeleteDialog.cid"
      :visible="cardCreatorDeleteDialog.visible"
      :pid="cardCreatorDeleteDialog.pid"
      @close="closeDialog"
    />
  </div>
</template>

<script>
  import { mapFields, mapMultiRowFields } from 'vuex-map-fields'
  import CardCreatorEditDialog from './CardCreatorEditDialog'
  import CardCreatorDeleteDialog from './CardCreatorDeleteDialog'

  export default {

    components: {
      CardCreatorEditDialog,
      CardCreatorDeleteDialog
    },

    data() {
      return {
        cardTableFilter: '',
        cardCreatorEditDialog: {
          visible: false,
          card: null
        },
        cardCreatorDeleteDialog: {
          visible: false,
          cid: null,
          label: null,
          pid: null,
        },
      }
    },

    async created() {
      this.reloadTable()
    },

    methods: {

      async reloadTable() {
        const cards = await this.$http.card.$fetch(this.sid).then(res => res.data)
        this.cards = [...cards]
      },

      closeDialog() {
        this.cardCreatorDeleteDialog.visible = false
        this.cardCreatorDeleteDialog.cid = null
        this.cardCreatorDeleteDialog.label = null
        this.cardCreatorDeleteDialog.pid = null
        this.cardCreatorEditDialog.visible = false
        this.cardCreatorEditDialog.card = null
        this.reloadTable()
      },

      showCardCreatorEditDialog(row) {
        this.cardCreatorEditDialog = {
          visible: true,
          card: row
        }
      },

      showCardCreatorDeleteDialog(row) {
        this.cardCreatorDeleteDialog = {
          visible: true,
          cid: row.cid,
          label: row.label,
          pid: row.pid,
        }
      },
    },

    computed: {
      ...mapFields({
        sid: 'strategyForm.sid',
        cards: 'strategyForm.cards',
        inRules: 'strategyForm.inRules',
        exitRules: 'strategyForm.exitRules',
      }),


      /**
       * 取引戦略テーブルをフィルタリングする
       */
      filteredCards: function () {
        return this.cardTableFilter === ''
          ? this.cards
          : this.cards.filter(c => c.label.includes(this.cardTableFilter))
      }
    }
  }
</script>

<style scoped>
  #card-pagination {
    text-align: center;
  }

  #card-table {
    margin-top: 5px;
    margin-bottom: 10px;
  }

  .el-pagination > > > .el-select .el-input {
    width: 150px !important;
  }
</style>
