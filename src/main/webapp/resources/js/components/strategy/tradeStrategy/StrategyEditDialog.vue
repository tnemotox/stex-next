<template>
  <el-dialog
    width="90%"
    :visible.sync="visible"
    :before-close="() => $emit('close')"
  >
    <el-row slot="title" class="dialog-header">
      {{sid ? '取引戦略更新' : '取引戦略追加'}}
    </el-row>
    <h3>基本情報</h3>
    <el-form
      id="strategy-form"
      :model="strategyForm"
      :rules="rules"
      ref="strategyForm"
      label-position="top"
    >
      <el-form-item v-if="sid" label="取引戦略ID">
        <el-label :width="150">
          {{sid}}
        </el-label>
      </el-form-item>
      <el-form-item
        label="取引戦略名"
        prop="label"
        style="width: 400px;"
      >
        <el-input
          placeholder="取引戦略名"
          v-model="label"
          clearable
        />
      </el-form-item>
      <el-form-item
        label="分析銘柄グループ"
        prop="gid"
      >
        <el-select v-model="gid" :value="null">
          <el-option
            v-for="analysisBrandGroup in analysisBrandGroups"
            :label="analysisBrandGroup.label"
            :value="analysisBrandGroup.gid"
            :key="`analysis-brand-group-${analysisBrandGroup.gid}`"
          />
        </el-select>
      </el-form-item>
      <el-form-item
        label="分析期間"
        prop="analysisDate"
      >
        <el-date-picker
          v-model="analysisDate"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="To"
          start-placeholder="分析開始日"
          end-placeholder="分析終了日"
        >
        </el-date-picker>
      </el-form-item>
    </el-form>
    <div v-if="strategyForm.sid">
      <h3>取引ルール</h3>
      <div id="rule-tabs">
        <el-tabs type="border-card" stretch :value="tabName" @tab-click="tab => this.tabName = tab.name">
          <el-tab-pane label="カード" name="card">
            <card-holder id="card-holder"/>
          </el-tab-pane>
          <el-tab-pane label="仕掛けルール" name="in-rule">
            <strategy-board :trade-type="true" id="in-trade-rule"/>
          </el-tab-pane>
          <el-tab-pane label="手仕舞いルール" name="exit-rule">
            <strategy-board :trade-type="false" id="exit-trade-rule"/>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <el-row slot="footer" class="dialog-footer">
      <el-button
        size="small"
        @click="() => $emit('close')"
      >キャンセル
      </el-button>
      <el-button
        v-if="sid"
        type="primary"
        size="small"
        @click="update"
      >更新
      </el-button>
      <el-button
        v-else
        type="primary"
        size="small"
        @click="create"
      >追加
      </el-button>
    </el-row>
  </el-dialog>
</template>

<script>
  import { mapFields } from 'vuex-map-fields'
  import ElLabel from '../../common/ElLabel'
  import StrategyBoard from './StrategyBoard'
  import CardHolder from './CardHolder'
  import moment from 'moment'
  moment.locale('ja')

  export default {

    components: {
      StrategyBoard,
      CardHolder,
      ElLabel
    },

    props: {
      strategy: Object,
      visible: Boolean
    },

    data() {
      return {
        tabName: 'card',
        rules: {
          label: [
            {
              required: true,
              message: '取引戦略名を入力してください。',
              trigger: 'blur'
            }
          ],
          gid: [
            {
              required: true,
              message: '分析銘柄グループを入力してください。',
              trigger: 'change'
            }
          ],
          analysisDate: [
            {
              required: true,
              message: '分析期間を入力してください。',
              trigger: ['blur', 'change']
            }
          ]
        }
      }
    },

    methods: {

      create() {
        this.$refs.strategyForm.validate(async valid => {
          if (valid) {
            await this.$http.strategy.$create(Object.assign(this.strategyForm, {
              analysisStartDate: moment(this.strategyForm.analysisDate[0]).format('YYYY-MM-DD'),
              analysisEndDate: moment(this.strategyForm.analysisDate[1]).format('YYYY-MM-DD'),
            })).then(() => {
              this.$notify({
                type: 'info',
                message: '取引戦略を作成しました。',
                position: 'bottom-right'
              })
              this.$emit('close')
            })
          }
        })
      },

      async update() {
        this.$refs.strategyForm.validate(async valid => {
          if (valid) {
            await this.$http.strategy.$update(Object.assign(this.strategyForm, {
              analysisStartDate: moment(this.strategyForm.analysisDate[0]).format('YYYY-MM-DD'),
              analysisEndDate: moment(this.strategyForm.analysisDate[1]).format('YYYY-MM-DD'),
            })).then(() => {
              this.$notify({
                type: 'info',
                message: '取引戦略を更新しました。',
                position: 'bottom-right'
              })
              this.$emit('close')
            })
          }
        })
      },
    },

    computed: {
      ...mapFields({
        strategyForm: 'strategyForm',
        sid: 'strategyForm.sid',
        label: 'strategyForm.label',
        gid: 'strategyForm.gid',
        analysisDate: 'strategyForm.analysisDate',
        analysisBrandGroups: 'analysisBrandGroups',
      }),
    }
  }
</script>

<style scoped>
  #strategy-form {
    padding-left: 10px;
    padding-bottom: 10px;
  }

  #rule-tabs {
    padding: 10px;
  }
</style>
