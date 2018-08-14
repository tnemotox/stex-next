<template>
  <div>
    <el-row id="wrapper">
      <el-col :sm="24" :xs="24">
        <div class="board">
          <div v-for="(rule, ridx) in tradeRules" :key="`${inOrExit}-${rule.orderBy}`">
            <el-form :model="rule" :rules="rules">
              <div class="rule-area">
                <el-row class="rule-header" type="flex" align="bottom">
                  <el-col :sm="17" :xs="14">
                    <el-form-item prop="label">
                      <el-input
                        v-model="rule.label"
                        size="small"
                        suffix-icon="el-icon-edit"
                        class="rule-label"
                        placeholder="取引ルール名を入力してください"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :sm="7" :xs="10">
                    <div style="text-align: right;">
                      <el-button
                        v-if="ridx !== 0"
                        size="mini"
                        type="success"
                        icon="el-icon-arrow-up"
                        plain
                        @click="orderUp(rule.orderBy, inOrExit)"
                      />
                      <el-button
                        v-if="tradeRules.length - 1 !== ridx"
                        size="mini"
                        type="success"
                        icon="el-icon-arrow-down"
                        plain
                        @click="orderDown(rule.orderBy, inOrExit)"
                      />
                      <el-button
                        size="mini"
                        type="primary"
                        icon="el-icon-plus"
                        plain
                        @click="addRule(rule, inOrExit)"
                      />
                      <el-button
                        v-if="ridx !== 0"
                        size="mini"
                        type="danger"
                        icon="el-icon-minus"
                        plain
                        @click="removeRule(rule, inOrExit)"
                      />
                    </div>
                  </el-col>
                </el-row>
                <el-row class="palette-row">
                  <el-col :sm="3" :xs="5">
                    <div class="palette-item-header">
                      操作
                    </div>
                  </el-col>
                  <el-col :sm="2" :xs="3">
                    <div class="palette-item-header">
                      結合
                    </div>
                  </el-col>
                  <el-col :sm="2" :xs="3">
                    <div class="palette-item-header">
                      階層
                    </div>
                  </el-col>
                  <el-col :sm="13" :xs="7">
                    <div class="palette-item-header">
                      戦略カード
                    </div>
                  </el-col>
                  <el-col :sm="2" :xs="3">
                    <div class="palette-item-header">
                      結合
                    </div>
                  </el-col>
                  <el-col :sm="2" :xs="3">
                    <div class="palette-item-header">
                      階層
                    </div>
                  </el-col>
                </el-row>
                <el-row class="palette-row" v-for="(palette, pidx) in rule.palettes"
                        :key="`${inOrExit}-${rule.orderBy}-${palette.orderBy}`">
                  <el-col :sm="3" :xs="5">
                    <div class="palette-item-manipulate">
                      <el-button
                        size="mini"
                        type="primary"
                        icon="el-icon-plus"
                        plain
                        @click="addPalette(rule, palette, inOrExit)"
                      />
                      <el-button
                        v-if="pidx !== 0"
                        size="mini"
                        type="danger"
                        icon="el-icon-minus"
                        plain
                        @click="removePalette(rule, palette, inOrExit)"
                      />
                    </div>
                  </el-col>
                  <el-col :sm="2" :xs="3">
                    <div class="palette-item">
                      <el-button
                        size="small"
                        class="palette-item-button"
                        @click="toggleJoint(rule.orderBy, palette.orderBy, false)"
                        :type="palette.leftJointType === 0 ? '' : 'success'"
                        plain
                      >
                        {{ palette.leftJointType === 0 ? 'なし' : palette.leftJointType === 1 ? 'AND' : 'OR' }}
                      </el-button>
                    </div>
                  </el-col>
                  <el-col :sm="2" :xs="3">
                    <div class="palette-item">
                      <el-button
                        size="small"
                        class="palette-item-button"
                        @click="toggleNest(rule.orderBy, palette.orderBy, true)"
                        :type="palette.nestOpen ? 'success' : ''"
                        plain
                      >
                        {{ palette.nestOpen ? '開始' : 'なし' }}
                      </el-button>
                    </div>
                  </el-col>
                  <el-col :sm="13" :xs="7">
                    <div class="palette-item">
                      <div
                        class="palette-item-card"
                        data-inorexit="true"
                      >
                        <el-select
                          :value="resolveCard(palette)"
                          @change="selectedCard"
                          @clear="clearCard(Object.assign({}, palette))"
                          clearable
                          filterable
                        >
                          <el-option
                            v-for="card in cards.filter(c => c.pid === palette.pid).concat(unusedCards).sort((a, b) => a.cid > b.cid ? 1 : -1)"
                            :key="`${inOrExit}-${rule.orderBy}-${palette.orderBy}-${card.cid}`"
                            :value="`${card.cid}_${palette.pid}`"
                            :label="`${('000' + card.cid).slice(-4)}　${card.label}`"
                          >
                          </el-option>
                        </el-select>
                      </div>
                    </div>
                  </el-col>
                  <el-col :sm="2" :xs="3">
                    <div class="palette-item">
                      <el-button
                        size="small"
                        class="palette-item-button"
                        @click="toggleNest(rule.orderBy, palette.orderBy, false)"
                        :type="palette.nestClose ? 'success' : ''"
                        plain
                      >
                        {{ palette.nestClose ? '終了' : 'なし' }}
                      </el-button>
                    </div>
                  </el-col>
                  <el-col :sm="2" :xs="3">
                    <div class="palette-item">
                      <el-button
                        size="small"
                        class="palette-item-button"
                        @click="toggleJoint(rule.orderBy, palette.orderBy, true)"
                        :type="palette.rightJointType === 0 ? '' : 'success'"
                        plain
                      >
                        {{ palette.rightJointType === 0 ? 'なし' : palette.rightJointType === 1 ? 'AND' : 'OR' }}
                      </el-button>
                    </div>
                  </el-col>
                </el-row>
                <el-row class="rule-form">
                  <el-col>
                    <div class="rule">
                      <span class="rule-text">上記を満たすとき、</span>
                      <el-radio-group size="small" v-model="rule.todayOrTomorrow">
                        <el-radio-button :label="true">当日</el-radio-button>
                        <el-radio-button :label="false">翌日</el-radio-button>
                      </el-radio-group>
                      <span class="rule-text"> の </span>
                      <el-radio-group size="small" v-model="rule.tradeTimingType">
                        <el-radio-button :label="1">寄成</el-radio-button>
                        <el-radio-button :label="2">引成</el-radio-button>
                        <el-radio-button :label="3">成行</el-radio-button>
                        <el-radio-button :label="4">指値</el-radio-button>
                      </el-radio-group>
                      <el-input
                        v-if="rule.tradeTimingType === 4"
                        size="small"
                        placeholder="指値"
                        class="limit-order-value"
                        :value="rule.limitOrderPrice"
                        v-model="rule.limitOrderPrice"
                      />
                      <span class="rule-text"> で </span>
                      <el-radio-group v-if="rule.orderBy === 1" size="small" v-model="rule.buyOrSell">
                        <el-radio-button :label="true">購入</el-radio-button>
                        <el-radio-button :label="false">売却</el-radio-button>
                      </el-radio-group>
                      <span class="rule-text" v-else> {{tradeRules[0].buyOrSell ? '購入' : '売却'}} </span>
                      <span class="rule-text"> で </span>
                      <span class="rule-text"> 仕掛ける </span>
                    </div>
                  </el-col>
                </el-row>
              </div>
            </el-form>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {
    mapFields,
    mapMultiRowFields
  } from 'vuex-map-fields'

  export default {
    props: {
      inOrExit: Boolean,
    },

    data() {
      return {
        // フロントエンドでは、負数でパレットIDを仮に割り振る
        newPid: -1,
        rules: {
          label: [
            {
              required: true,
              message: '取引ルール名を入力してください。',
              trigger: ['blur', 'change']
            }
          ]
        }
      }
    },

    methods: {

      /**
       * ルールの順序を下と入れ替える
       *
       * @param rOrder 順序を入れ替えるルールの順序
       * @param inOrExit 仕掛けフラグ
       */
      orderDown(rOrder, inOrExit) {
        const cb = rules => {
          return rules.map(rule => {
            if (rule.orderBy === rOrder) {
              rule.orderBy++
            }
            else if (rule.orderBy === rOrder + 1) {
              rule.orderBy--
            }
            return rule
          }).sort((r1, r2) => r1.orderBy > r2.orderBy ? 1 : -1)
        }

        if (inOrExit) {
          this.inRules = cb(this.inRules)
        }
        else {
          this.exitRules = cb(this.exitRules)
        }
      },

      /**
       * ルールの順序を上と入れ替える
       *
       * @param rOrder 順序を入れ替えるルールの順序
       * @param inOrExit 仕掛けフラグ
       */
      orderUp(rOrder, inOrExit) {
        const cb = rules => {
          return rules.map(rule => {
            if (rule.orderBy === rOrder - 1) {
              rule.orderBy++
            }
            else if (rule.orderBy === rOrder) {
              rule.orderBy--
            }
            return rule
          }).sort((r1, r2) => r1.orderBy > r2.orderBy ? 1 : -1)
        }

        if (inOrExit) {
          this.inRules = cb(this.inRules)
        }
        else {
          this.exitRules = cb(this.exitRules)
        }
      },

      /**
       * ルールを追加する
       *
       * @param rule 取引戦略ルール
       * @param inOrExit 仕掛けフラグ
       */
      addRule(rule, inOrExit) {
        const cb = rules => {
          // パレットをディープコピーして要素を追加する
          let newRules = [].concat(rules)
          newRules.map(r => {
            if (r.orderBy > rule.orderBy) {
              r.orderBy = r.orderBy + 1
            }
            return r
          })
          // パレットの順序を採番し直す
          newRules.splice(rule.orderBy, 0, {
            rid: null,
            label: '新しい取引ルール',
            todayOrTomorrow: false,
            buyOrSell: true,
            tradeTimingType: 1,
            limitOrderPrice: null,
            orderBy: rule.orderBy + 1,
            palettes: [{
              pid: this.newPid--,
              leftJointType: 0,
              rightJointType: 0,
              nestOpen: false,
              nestClose: false,
              orderBy: 1
            }],
          })

          return newRules
        }

        // 仕掛けの場合
        if (inOrExit) {
          this.inRules = cb(this.inRules)
        }
        // 手仕舞いの場合
        else {
          this.exitRules = cb(this.exitRules)
        }
      },

      /**
       * ルールを削除する
       * ルールが持つパレットとカードの関連付けを削除する
       *
       * @param rule 取引戦略ルール
       * @param inOrExit 仕掛けフラグ
       */
      removeRule(rule, inOrExit) {
        let pids = []

        const cb = rules => {
          // ルールをディープコピーして要素を削除する
          let newRules = [].concat(rules)
          // カードとパレットの紐付け解除のため、ルールが持つパレットIDを保持
          rules.forEach(rule => {
            rule.palettes.forEach(palette => {
              pids.push(palette.pid)
            })
          })

          newRules.splice(rule.orderBy - 1, 1)
          // ルールの順序を採番し直す
          newRules.map((r, idx) => {
            r.orderBy = idx + 1
            return r
          })
          return newRules
        }

        // 仕掛けの場合
        if (inOrExit) {
          this.inRules = cb(this.inRules)
        }
        // 手仕舞いの場合
        else {
          this.exitRules = cb(this.exitRules)
        }

        // カードとパレットの紐付け解除
        this.cards = this.cards.map(card => {
          if (card.pid && pids.includes(card.pid)) {
            card.pid = null
            card.used = false
          }
          return card
        })
      },

      /**
       * パレットを追加する
       *
       * @param rule 取引戦略ルール
       * @param palette 取引戦略パレット
       * @param inOrExit 仕掛けフラグ
       */
      addPalette(rule, palette, inOrExit) {
        const cb = rules => {
          // パレットをディープコピーして要素を追加する
          let newPalettes = [].concat(rules[rule.orderBy - 1].palettes)
          newPalettes.map(p => {
            if (p.orderBy > palette.orderBy) {
              p.orderBy = p.orderBy + 1
            }
            return p
          })
          // パレットの順序を採番し直す
          newPalettes.splice(palette.orderBy, 0, {
            pid: this.newPid--,
            leftJointType: 0,
            rightJointType: 0,
            nestOpen: false,
            nestClose: false,
            orderBy: palette.orderBy + 1
          })
          // ルールをディープコピーしてパレットを設定し、storeに設定する
          let newRules = [].concat(rules)
          newRules[rule.orderBy - 1].palettes = newPalettes
          return newRules
        }

        // 仕掛けの場合
        if (inOrExit) {
          this.inRules = cb(this.inRules)
        }
        // 手仕舞いの場合
        else {
          this.exitRules = cb(this.exitRules)
        }
      },

      /**
       * パレットを削除する
       * パレットとカードの関連付けを削除する
       *
       * @param rule 取引戦略ルール
       * @param palette 取引戦略パレット
       * @param inOrExit 仕掛けフラグ
       */
      removePalette(rule, palette, inOrExit) {
        let pid
        const cb = rules => {
          // パレットをディープコピーして要素を削除する
          let newPalettes = [].concat(rules[rule.orderBy - 1].palettes)
          // カードとパレットの紐付け解除のため、パレットIDを保持
          pid = newPalettes[palette.orderBy - 1].pid

          newPalettes.splice(palette.orderBy - 1, 1)
          // パレットの順序を採番し直す
          newPalettes.map((p, idx) => {
            p.orderBy = idx + 1
            return p
          })
          // ルールをディープコピーしてパレットを設定し、storeに設定する
          let newRules = [].concat(rules)
          newRules[rule.orderBy - 1].palettes = newPalettes
          return newRules
        }

        // 仕掛けの場合
        if (inOrExit) {
          this.inRules = cb(this.inRules)
        }
        // 手仕舞いの場合
        else {
          this.exitRules = cb(this.exitRules)
        }

        // カードとパレットの紐付け解除
        this.cards = this.cards.map(card => {
          if (card.pid && card.pid === pid) {
            card.pid = null
            card.used = false
          }
          return card
        })
      },

      /**
       * 階層をトグルする
       *
       * @param rOrder 取引ルールID
       * @param pOrder 取引戦略パレットID
       * @param openOrClose 開始フラグ（true: 開始、false: 終了）
       */
      toggleNest(rOrder, pOrder, openOrClose) {
        // 開始か終了かを判別してブラケット演算子の引数を生成
        const nestType = openOrClose ? 'nestOpen' : 'nestClose'

        // 開始/終了いずれかをトグルするコールバック
        const cb = rules => {
          return rules.map(rule => {
            if (rule.orderBy === rOrder) {
              rule.palettes.map(palette => {
                if (palette.orderBy === pOrder) {
                  palette[nestType] = !palette[nestType]
                }
                return palette
              })
            }
            return rule
          })
        }

        // 仕掛けの場合
        if (this.inOrExit) {
          this.inRules = cb(this.inRules)
        }
        // 手仕舞いの場合
        else {
          this.exitRules = cb(this.exitRules)
        }
      },

      /**
       * 結合種別をトグルする
       *
       * @param rOrder 取引ルールID
       * @param pOrder 取引戦略パレットID
       * @param rightOrLeft 左右フラグ（true: 右、false: 左）
       */
      toggleJoint(rOrder, pOrder, rightOrLeft) {
        // 左右を判別してブラケット演算子の引数を生成
        const jointType = rightOrLeft ? 'rightJointType' : 'leftJointType'

        // 左右いずれかの結合種別をトグルするコールバック
        const cb = rules => {
          return rules.map(rule => {
            if (rule.orderBy === rOrder) {
              rule.palettes.map(palette => {
                if (palette.orderBy === pOrder) {
                  palette[jointType] = palette[jointType] !== 2 ? palette[jointType] + 1 : 0
                }
                return palette
              })
            }
            return rule
          })
        }

        // 仕掛けの場合
        if (this.inOrExit) {
          this.inRules = cb(this.inRules)
        }
        // 手仕舞いの場合
        else {
          this.exitRules = cb(this.exitRules)
        }
      },

      /**
       * 設定された取引戦略カードを解決する
       *
       * @param palette 取引戦略カード
       */
      resolveCard(palette) {
        let card = this.cards.find(card => palette.pid && card.pid === palette.pid)
        return card ? `${card.cid}_${card.pid}` : null
      },

      /**
       * 設定された取引戦略カードを外す
       */
      clearCard(palette) {
        this.cards = this.cards.map(card => {
          if(card && card.pid === palette.pid) {
            card.pid = null
            card.used = false
          }
          return card
        })
      },

      /**
       * 取引戦略カードをパレットにドロップする
       */
      selectedCard(ids) {
        // アンダーバー区切りのカードID、パレットIDを取得
        const cid = parseInt(ids.split('_')[0])
        const pid = parseInt(ids.split('_')[1])

        this.cards = this.cards.map(card => {
          // 他で利用中のカードであればそれを取り除く
          if (card && card.pid === pid) {
            card.pid = null
            card.used = false
          }
          // カードにパレットを設定する
          if (card && card.cid === cid) {
            card.pid = pid
            card.used = true
          }
          return card
        })
      }
    },

    computed: {
      ...mapFields({
        cards: 'strategyForm.cards',
        inRules: 'strategyForm.rules.in',
        exitRules: 'strategyForm.rules.exit',
      }),

      ...mapMultiRowFields({
        inRulesMulti: 'strategyForm.rules.in',
        exitRulesMulti: 'strategyForm.rules.exit',
      }),

      /**
       * 未使用のカードを算出する
       */
      unusedCards: function () {
        return this.cards.filter(card => !card.used)
      },

      /**
       * 仕掛けフラグから利用する取引ルールを算出する
       */
      tradeRules: function () {
        return this.inOrExit ? this.inRulesMulti : this.exitRulesMulti
      },
    }
  }
</script>

<style scoped>
  #wrapper {
    padding: 0 10px 10px;
  }

  .rule-area {
    border: solid 1px #dddddd;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    padding: 10px;
    margin-bottom: 10px;
  }

  .palette-item {
    margin-bottom: 2px;
    height: 41px;
  }

  .palette-item-button {
    width: 100%;
    height: 100%;
  }

  .palette-item-card >>> .el-select {
    width: 100%;
  }

  .palette-item-card >>> .el-select .el-input input {
    height: 41px;
  }

  .palette-item-header {
    border: solid 1px #dedede;
    background-color: #efefef;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    padding: 9px;
    margin-bottom: 2px;
    text-align: center;
  }

  .el-button--small {
    padding: 9px 10px !important;
  }

  .rule-form {
    margin-top: 8px;
  }

  .rule {
    display: inline;
  }

  .limit-order-value {
    display: table-cell;
    width: 50px;
    top: 1px;
  }

  .limit-order-value >>> input {
    padding: 0 10px !important;
    font-size: 12px !important;
  }

  .el-radio-button--small >>> .el-radio-button__inner {
    padding: 9px 10px;
  }

  .rule-text {
    vertical-align: middle;
  }

  .palette-item-manipulate {
    border: solid 1px #dedede;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    text-align: center;
    padding: 5px 0;
  }

  .el-button--mini {
    padding: 7px;
  }

  .rule-area >>> div div div div .el-form-item__error {
    top: -16px;
  }

  .rule-area >>> div div .el-form-item {
    margin: 0;
  }

  .rule-area >>> div div div .el-form-item__content {
    top: 5px;
  }

  .rule-header {
    border-bottom: solid 1px #dedede;
    margin-bottom: 15px;
    padding: 10px;
  }

</style>
