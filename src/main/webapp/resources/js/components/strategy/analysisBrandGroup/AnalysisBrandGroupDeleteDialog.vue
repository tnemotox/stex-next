<template>
  <el-dialog
    width="90%"
    :visible.sync="visible"
    :before-close="() => $emit('close')"
  >
    <el-row slot="title" class="dialog-header">
      分析銘柄グループ削除
    </el-row>
    <el-row>
      <p>分析銘柄グループ（{{label}}）を削除してよろしいですか？</p>
      <p>分析銘柄グループを利用している取引戦略は未選択になります。</p>
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
        await this.$http.analysisBrandGroup.$delete(this.gid).then(() => {
          this.$notify({
            type: 'info',
            message: '分析銘柄グループを削除しました。',
            position: 'bottom-right'
          })
          this.$emit('close')
        })
      },
    },

    computed: {
      ...mapFields({
        gid: 'analysisBrandGroupForm.gid',
        label: 'analysisBrandGroupForm.label',
        brands: 'analysisBrandGroupForm.brands'
      })
    }
  }
</script>
