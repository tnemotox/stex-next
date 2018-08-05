import APIClient from './APIClient'

const strategy = new APIClient('/strategy')
const card = new APIClient('/card')

export default {
  card: {
    $create: cardForm => card.post(cardForm),

    $update: cardForm => card.put(cardForm, `/${cardForm.cid}`),

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
