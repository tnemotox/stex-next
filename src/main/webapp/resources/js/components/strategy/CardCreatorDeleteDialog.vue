<template>
  <el-dialog
    append-to-body
    width="90%"
    :visible.sync="visible"
    :before-close="() => $emit('close')"
  >
    <el-row slot="title" class="dialog-header">
      取引戦略カード削除
    </el-row>
    <el-row>
      <p>取引戦略パレットとの紐付けが解除されます。</p>
      <p>取引戦略カード（{{label}}）を削除してよろしいですか？</p>
    </el-row>
    <el-row slot="footer" class="dialog-footer">
      <el-button
        size="small"
        @click="() => $emit('close')"
      >キャンセル
      </el-button>
      <el-button
        type="danger"
        size="small"
        @click="remove"
      >削除
      </el-button>
    </el-row>
  </el-dialog>
</template>

<script>
  import { mapFields } from 'vuex-map-fields'

  export default {
    props: {
      visible: Boolean,
      label: String,
      cid: Number,
      pid: Number,
    },

    methods: {

      /**
       * 取引戦略カードを削除する
       */
      async remove() {
        // カードとパレットが紐づいている場合は、削除して良いか確認する
        let doDeleteCard = true

        const inPaletteIds = this.inRules
               .map(rule => rule.palettes)
               .reduce((memo, current) => memo.concat(current), [])
               .map(p => p.pid)
        if (inPaletteIds.includes(this.pid)) {
          doDeleteCard = confirm('取引戦略カードが仕掛けルールに利用されていますが、削除してよろしいですか？')
        }

        const exitPaletteIds = this.exitRules
             .map(rule => rule.palettes)
             .reduce((memo, current) => memo.concat(current), [])
             .map(p => p.pid)
        if (exitPaletteIds.includes(this.pid)) {
          doDeleteCard = confirm('取引戦略カードが手仕舞いルールに利用されていますが、削除してよろしいですか？')
        }

        if (doDeleteCard) {
          await this.$http.card.$delete(this.cid).then(() => {
            this.$notify({
              type: 'info',
              message: '取引戦略カードを削除しました。',
              position: 'bottom-right'
            })
            this.$emit('close')
          })
        }
        else {
          this.$emit('close')
        }
      },
    },

    computed: {
      ...mapFields({
        inRules: 'strategyForm.tradeRules.in.rules',
        exitRules: 'strategyForm.tradeRules.exit.rules',
      }),
    }
  }
</script>
