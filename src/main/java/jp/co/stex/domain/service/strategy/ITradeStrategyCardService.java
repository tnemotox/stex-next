package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;

import java.util.List;

/**
 * <p>取引戦略カードを操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
public interface ITradeStrategyCardService {

    /**
     * <p>取引戦略IDに紐づく取引戦略カードを全て取得する。</p>
     *
     * @param uid ユーザID
     * @param sid 取引戦略ID
     * @return 取引戦略カード一覧
     */
    List<TradeStrategyCardEntity> findAllTradeStrategyCard(int uid, int sid);

    /**
     * <p>取引戦略カードを追加する。</p>
     *
     * @param tradeStrategyCard 取引戦略カード
     * @return 取引戦略カードID
     */
    int createOneTradeStrategyCard(TradeStrategyCardEntity tradeStrategyCard);

    /**
     * <p>取引戦略カードを更新する。</p>
     *
     * @param tradeStrategyCard 取引戦略カード
     */
    void updateOneTradeStrategyCard(TradeStrategyCardEntity tradeStrategyCard);

    /**
     * <p>取引戦略カードを削除する。</p>
     *
     * @param uid ユーザID
     * @param cid 取引戦略カードID
     */
    void deleteOneTradeStrategyCard(int uid, int cid);
}
