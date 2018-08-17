package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.strategy.TradeStrategyEntity;

import java.util.List;

/**
 * <p>取引戦略を操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
public interface ITradeStrategyService {

    /**
     * <p>全ての取引戦略を取得する。</p>
     *
     * @param loginUserId ログインユーザID
     * @return 取引戦略リスト
     */
    List<TradeStrategyEntity> findAllTradeStrategy(int loginUserId);

    /**
     * <p>取引戦略を新規作成する。</p>
     *
     * @param tradeStrategy 取引戦略
     * @return 取引戦略戦略ID
     */
    int createOneTradeStrategy(TradeStrategyEntity tradeStrategy);

    /**
     * <p>取引戦略を更新する。</p>
     *
     * @param tradeStrategy 取引戦略
     */
    void updateOneTradeStrategy(TradeStrategyEntity tradeStrategy);

    /**
     * <p>取引戦略を削除する。</p>
     *
     * @param uid ユーザID
     * @param sid 取引戦略ID
     */
    void deleteOneTradeStrategy(int uid, int sid);
}
