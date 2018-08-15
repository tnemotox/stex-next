import APIClient from './APIClient'

const strategy = new APIClient('/trade-strategy')
const card = new APIClient('/trade-strategy-card')
const analysisBrandGroup = new APIClient('/analysis-brand-group')
const brand = new APIClient('/brand')

export default {

  brand: {
    $fetch: () => brand.get()
  },

  analysisBrandGroup: {

    /**
     * 分析対象グループを取得する。
     */
    $fetch: () => analysisBrandGroup.get(),

    /**
     * 分析対象グループを作成する。
     */
    $create: analysisBrandGroupForm => analysisBrandGroup.post(analysisBrandGroupForm),

    /**
     * 分析対象グループを更新する。
     */
    $update: analysisBrandGroupForm => analysisBrandGroup.put(analysisBrandGroupForm, `/${analysisBrandGroupForm.gid}`),

    /**
     * 分析対象グループを削除する。
     */
    $delete: gid => analysisBrandGroup.delete(`/${gid}`)
  },
  card: {

    /**
     * 取引戦略カードを作成する。
     */
    $create: cardForm => card.post(cardForm),

    /**
     * 取引戦略カードを更新する。
     */
    $update: cardForm => card.put(cardForm, `/${cardForm.cid}`),

    /**
     * 取引戦略カードを削除する。
     */
    $delete: cid   => card.delete(`/${cid}`),
  },
  strategy: {

    /**
     * 取引戦略を取得する。
     */
    $fetch: () => strategy.get(),

    /**
     * 取引戦略を作成する。
     */
    $create: strategyForm => strategy.post(strategyForm),

    /**
     * 取引戦略を更新する。
     */
    $update: strategyForm => strategy.put(strategyForm, `/${strategyForm.sid}`),

    /**
     * 取引戦略を削除する。
     */
    $delete: sid => strategy.delete(`/${sid}`)
  }
}
