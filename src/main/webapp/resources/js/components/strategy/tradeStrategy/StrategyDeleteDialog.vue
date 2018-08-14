<template>
  <el-dialog
    width="90%"
    :visible.sync="visible"
    :before-close="() => $emit('close')"
  >
    <el-row slot="title" class="dialog-header">
      取引戦略削除
    </el-row>
    <el-row>
      <p>取引戦略（{{label}}）を削除してよろしいですか？</p>
      <p>取引戦略に含まれる取引ルールも削除されます。</p>
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
      visible: Boolean
    },

    methods: {
      async remove() {
        await this.$http.strategy.$delete(this.sid).then(() => {
          this.$notify({
            type: 'info',
            message: '取引戦略を削除しました。',
            position: 'bottom-right'
          })
          this.$emit('close')
        })
      },
    },

    computed: {
      ...mapFields({
        sid: 'strategyForm.sid',
        label: 'strategyForm.label',
      })
    }
  }
</script>
