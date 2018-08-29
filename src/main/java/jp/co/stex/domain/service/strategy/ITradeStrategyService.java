package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.TradeStrategies;
import jp.co.stex.domain.model.strategy.TradeStrategy;
import jp.co.stex.domain.model.strategy.value.VSid;

/**
 * <p>取引戦略を操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
public interface ITradeStrategyService {

    /**
     * <p>取引戦略一覧を取得する。</p>
     *
     * @param uid ログインユーザID
     * @return 取引戦略一覧
     */
    TradeStrategies findAll(VUid uid);

    /**
     * <p>取引戦略を追加する。</p>
     *
     * @param uid ユーザID
     * @param tradeStrategy 取引戦略
     * @return 取引戦略戦略ID
     */
    VSid createOne(VUid uid, TradeStrategy tradeStrategy);

    /**
     * <p>取引戦略を更新する。</p>
     *
     * @param uid ユーザID
     * @param tradeStrategy 取引戦略
     */
    void updateOne(VUid uid, TradeStrategy tradeStrategy);

    /**
     * <p>取引戦略を削除する。</p>
     *
     * @param uid ユーザID
     * @param sid 取引戦略ID
     */
    void deleteOne(VUid uid, VSid sid);
}
