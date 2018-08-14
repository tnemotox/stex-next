<template>
  <el-dialog
    width="90%"
    :visible.sync="visible"
    :before-close="() => $emit('close')"
  >
    <el-row slot="title" class="dialog-header">
      {{gid ? '分析銘柄グループ更新' : '分析銘柄グループ追加'}}
    </el-row>
    <el-form
      id="analysis-brand-group-form"
      :model="analysisBrandGroupForm"
      :rules="rules"
      ref="analysisBrandGroupForm"
      label-position="top"
    >
      <el-form-item v-if="gid" label="分析銘柄グループID">
        <el-label :width="150">
          {{gid}}
        </el-label>
      </el-form-item>
      <el-form-item
        label="分析銘柄グループ名"
        prop="label"
        style="width: 400px;"
      >
        <el-input
          placeholder="分析銘柄グループ名"
          v-model="label"
          clearable
        />
      </el-form-item>
      <el-form-item label="対象銘柄">
        <el-transfer
          filterable
          v-model="selectedBrands"
          :data="allBrands"
          :titles="['未選択の銘柄', '選択済の銘柄']"
        />
      </el-form-item>
    </el-form>
    <el-row slot="footer" class="dialog-footer">
      <el-button
        size="small"
        @click="() => $emit('close')"
      >キャンセル
      </el-button>
      <el-button
        v-if="gid"
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

  export default {

    components: {
      ElLabel
    },

    props: {
      visible: Boolean
    },

    data() {
      return {
        rules: {
          label: [
            {
              required: true,
              message: '取引戦略名を入力してください。',
              trigger: 'blur'
            }
          ],
        }
      }
    },

    methods: {
      create() {
        this.$refs.analysisBrandGroupForm.validate(async valid => {
          if (valid) {
            await this.$http.analysisBrandGroup.$create(this.analysisBrandGroupForm).then(() => {
              this.$notify({
                type: 'info',
                message: '分析銘柄グループを作成しました。',
                position: 'bottom-right'
              })
              this.$emit('close')
            })
          }
        })
      },

      async update() {
        this.$refs.analysisBrandGroupForm.validate(async valid => {
          if (valid) {
            await this.$http.analysisBrandGroup.$update(this.analysisBrandGroupForm).then(() => {
              this.$notify({
                type: 'info',
                message: '分析銘柄グループを更新しました。',
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
        analysisBrandGroupForm: 'analysisBrandGroupForm',
        gid: 'analysisBrandGroupForm.gid',
        label: 'analysisBrandGroupForm.label',
        selectedBrands: 'analysisBrandGroupForm.brands',
        brands: 'brands'
      }),

      allBrands: function() {
        return this.brands.map(b => ({key: b.code, label: `${b.code}　-　${b.name}　-　${b.market}`}))
      }
    }
  }
</script>

<style scoped>
  .el-transfer >>> .el-transfer-panel {
    width: 40% !important;
  }

  #analysis-brand-group-form {
    padding-left: 10px;
    padding-bottom: 10px;
  }
</style>
