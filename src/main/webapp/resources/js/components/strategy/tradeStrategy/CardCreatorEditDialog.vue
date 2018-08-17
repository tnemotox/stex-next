<template>
  <el-dialog
    append-to-body
    width="90%"
    :visible.sync="visible"
    :before-close="() => $emit('close')"
  >
    <el-row slot="title" class="dialog-header">
      {{card ? '取引戦略更新' : '取引戦略追加'}}
    </el-row>
    <el-form :model="cardForm" id="creator-tabs" ref="cardForm">
      <el-tabs
        type="border-card"
        stretch
        :value="card ? resolveTabName(card.cardType) : 'compare'"
        @tab-click="selectCardType"
      >
        <el-tab-pane
          label="比較"
          name="compare"
          :disabled="card ? card.cardType !== 1 : false"
        >
          <el-row class="creator-row">
            <h4>左辺</h4>
            <div class="creator">
              <el-input
                v-if="daysNeedIndicator(cardForm.leftSideIndicatorType)"
                v-model.number="cardForm.leftSideDays"
                size="small"
                placeholder="日数"
                class="creator-input"
                type="number"
                min="1"
              />
              <span v-if="daysNeedIndicator(cardForm.leftSideIndicatorType)" class="creator-text"> 日の </span>
              <el-select
                :value="cardForm.leftSideIndicatorType"
                v-model="cardForm.leftSideIndicatorType"
                clearable
                filterable
                size="small"
              >
                <el-option :value="1" label="移動平均線" />
                <el-option :value="2" label="移動平均線乖離率" />
                <el-option :value="3" label="終値" />
              </el-select>
              <span class="creator-text"> が </span>
            </div>
          </el-row>
          <el-row class="creator-row">
            <h4>右辺</h4>
            <div class="creator">
              <el-radio-group size="small" v-model="cardForm.rightSideFixOrFlex">
                <el-radio-button :label="true">固定値</el-radio-button>
                <el-radio-button :label="false">指標値</el-radio-button>
              </el-radio-group>
            </div>
            <div v-if="cardForm.rightSideFixOrFlex" class="creator">
              <el-input
                v-model.number="cardForm.rightSideFixValue"
                size="small"
                placeholder="値"
                class="creator-input"
                type="number"
                min="1"
              />
            </div>
            <div v-else class="creator">
              <el-input
                v-if="daysNeedIndicator(cardForm.rightSideIndicatorType)"
                v-model.number="cardForm.rightSideDays"
                size="small"
                placeholder="日数"
                class="creator-input"
                type="number"
                min="1"
              />
              <span v-if="daysNeedIndicator(cardForm.rightSideIndicatorType)" class="creator-text"> 日の </span>
              <el-select
                :value="cardForm.rightSideIndicatorType"
                v-model="cardForm.rightSideIndicatorType"
                clearable
                filterable
                size="small"
              >
                <el-option :value="1" label="移動平均線" />
                <el-option :value="2" label="移動平均線乖離率" />
                <el-option :value="3" label="終値" />
              </el-select>
              <span class="creator-text"> x </span>
              <el-input
                v-model.number="cardForm.coefficient"
                size="small"
                placeholder="係数"
                class="creator-input"
                type="number"
                min="1"
                step="0.001"
              />
            </div>
          </el-row>
          <el-row class="creator-row">
            <h4>比較条件</h4>
            <div class="creator">
              <el-radio-group size="small" v-model="cardForm.comparisonType">
                <el-radio-button :label="1">より大きい</el-radio-button>
                <el-radio-button :label="2">以上</el-radio-button>
                <el-radio-button :label="3">より小さい</el-radio-button>
                <el-radio-button :label="4">以下</el-radio-button>
                <el-radio-button :label="5">と等しい</el-radio-button>
              </el-radio-group>
            </div>
          </el-row>
        </el-tab-pane>
        <el-tab-pane
          label="交差"
          name="cross"
          :disabled="card ? card.cardType !== 2 : false"
        >
          <el-row class="creator-row">
            <h4>左辺</h4>
            <div class="creator">
              <el-input
                v-if="daysNeedIndicator(cardForm.leftSideIndicatorType)"
                v-model.number="cardForm.leftSideDays"
                size="small"
                placeholder="日数"
                class="creator-input"
                type="number"
                min="1"
              />
              <span v-if="daysNeedIndicator(cardForm.leftSideIndicatorType)" class="creator-text"> 日の </span>
              <el-select
                :value="cardForm.leftSideIndicatorType"
                v-model="cardForm.leftSideIndicatorType"
                clearable
                filterable
                size="small"
              >
                <el-option :value="1" label="移動平均線" />
                <el-option :value="2" label="移動平均線乖離率" />
                <el-option :value="3" label="終値" />
              </el-select>
              <span class="creator-text"> が </span>
            </div>
          </el-row>
          <el-row class="creator-row">
            <h4>右辺</h4>
            <div class="creator">
              <el-radio-group size="small" v-model="cardForm.rightSideFixOrFlex">
                <el-radio-button :label="true">固定値</el-radio-button>
                <el-radio-button :label="false">指標値</el-radio-button>
              </el-radio-group>
            </div>
            <div v-if="cardForm.rightSideFixOrFlex" class="creator">
              <el-input
                v-model.number="cardForm.rightSideFixValue"
                size="small"
                placeholder="値"
                class="creator-input"
                type="number"
                min="1"
              />
            </div>
            <div v-else class="creator">
              <el-input
                v-if="daysNeedIndicator(cardForm.rightSideIndicatorType)"
                v-model.number="cardForm.rightSideDays"
                size="small"
                placeholder="日数"
                class="creator-input"
                type="number"
                min="1"
              />
              <span v-if="daysNeedIndicator(cardForm.rightSideIndicatorType)" class="creator-text"> 日の </span>
              <el-select
                :value="cardForm.rightSideIndicatorType"
                v-model="cardForm.rightSideIndicatorType"
                clearable
                filterable
                size="small"
              >
                <el-option :value="1" label="移動平均線" />
                <el-option :value="2" label="移動平均線乖離率" />
                <el-option :value="3" label="終値" />
              </el-select>
              <span class="creator-text"> を </span>
            </div>
          </el-row>
          <el-row class="creator-row">
            <h4>交差条件</h4>
            <div class="creator">
              <el-radio-group size="small" v-model="cardForm.crossType">
                <el-radio-button :label="1">上抜け</el-radio-button>
                <el-radio-button :label="2">下抜け</el-radio-button>
              </el-radio-group>
            </div>
          </el-row>
        </el-tab-pane>
        <el-tab-pane
          label="時間"
          name="time"
          :disabled="card ? card.cardType !== 3 : false"
        >
          <div class="creator">
            <el-input
              v-model.number="cardForm.elapsedDay"
              size="small"
              placeholder="日数"
              class="creator-input"
              type="number"
              min="1"
            />
            <span class="creator-text"> 日 経過 </span>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-form>
    <el-row slot="footer" class="dialog-footer">
      <el-button size="small" @click="() => $emit('close')">キャンセル</el-button>
      <el-button v-if="card" type="primary" size="small" @click="update">更新</el-button>
      <el-button v-else type="primary" size="small" @click="create">追加</el-button>
    </el-row>
  </el-dialog>
</template>

<script>
  import { mapFields } from 'vuex-map-fields'

  export default {

    props: {
      visible: Boolean,
      card: Object
    },

    created() {
      if (this.card) {
        this.cardForm = Object.assign({}, this.card)
      }
      this.cardForm.sid = this.sid
    },

    data() {
      return {
        cardForm: {
          cid: null,
          pid: null,
          sid: null,
          cardType: 1,
          leftSideDays: null,
          leftSideIndicatorType: null,
          rightSideFixOrFlex: false,
          rightSideFixValue: null,
          rightSideDays: null,
          rightSideIndicatorType: null,
          coefficient: null,
          comparisonType: 1,
          crossType: 1,
        },
        rules: {

        }
      }
    },

    methods: {

      /**
       * カード種別からカード種別名を解決する
       *
       * @param cardType カード種別
       * @returns カード種別名
       */
      resolveTabName: function(cardType) {
        return cardType === 1
          ? 'compare'
          : cardType === 2
            ? 'cross'
            : 'time'
      },

      /**
       * クリックされたタブからカード種別を設定する
       *
       * @param tab クリックされたタブ
       */
      selectCardType(tab) {
        this.cardForm.cardType =
          tab.name === 'compare'
            ? 1
            : tab.name === 'cross'
              ? 2
              : 3
      },

      /**
       * 日付を必要とする指標かどうか判定する
       * @param indicatorType 指標種別
       * @returns {boolean}
       */
      daysNeedIndicator(indicatorType) {
        switch(indicatorType) {
          case 1:
          case 2:
            return true
          case 3:
          default:
            return false
        }
      },

      /**
       * 取引戦略カードを作成する
       */
      create() {
        this.$refs.cardForm.validate(async valid => {
          if (valid) {
            await this.$http.card.$create(this.cardForm).then(() => {
              this.$notify({
                type: 'info',
                message: '取引戦略カードを作成しました。',
                position: 'bottom-right'
              })
              this.$emit('close')
            })
          }
        })
      },

      /**
       * 取引戦略カードを更新する
       */
      async update() {
        // カードとパレットが紐づいている場合は、更新して良いか確認する
        let doUpdateCard = true

        const inPaletteIds = this.inRules
              .map(rule => rule.palettes)
              .reduce((memo, current) => memo.concat(current), [])
              .map(p => p.pid)
        if (inPaletteIds.includes(this.cardForm.pid)) {
          doUpdateCard = confirm('取引戦略カードが仕掛けルールに利用されていますが、更新してよろしいですか？')
        }

        const exitPaletteIds = this.exitRules
              .map(rule => rule.palettes)
              .reduce((memo, current) => memo.concat(current), [])
              .map(p => p.pid)
        if (exitPaletteIds.includes(this.cardForm.pid)) {
          doUpdateCard = confirm('取引戦略カードが手仕舞いルールに利用されていますが、更新してよろしいですか？')
        }

        if (doUpdateCard) {
          this.$refs.cardForm.validate(async valid => {
            if (valid) {
              await this.$http.card.$update(this.cardForm).then(() => {
                this.$notify({
                  type: 'info',
                  message: '取引戦略カードを更新しました。',
                  position: 'bottom-right'
                })
                this.$emit('close')
              })
            }
            else {

            }
          })
        }
        else {
          this.$emit('close')
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
    }
  }
</script>

<style scoped>

  .creator {
    display: inline;
    margin-left: 10px;
  }

  .creator-input {
    display: inline-block;
    width: 70px;
    top: 1px;
  }

  .creator-input >>> input {
    padding: 0 10px !important;
    font-size: 12px !important;
  }

  .el-radio-button--small >>> .el-radio-button__inner {
    padding: 9px 10px;
  }

  .creator-text {
    vertical-align: middle;
  }

  .creator-row {
    margin-bottom: 10px;
  }

</style>
