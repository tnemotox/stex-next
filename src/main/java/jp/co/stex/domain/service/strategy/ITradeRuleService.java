package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.strategy.TradeRuleEntity;
import jp.co.stex.domain.model.strategy.TradeRules;
import jp.co.stex.domain.model.strategy.code.TradeType;

import java.util.List;

/**
 * <p>取引戦略を操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
public interface ITradeRuleService {

    /**
     * <p>全ての取引戦略を取得する。</p>
     *
     * @param uid ログインユーザID
     * @param sid 取引戦略ID
     * @param tradeType 取引種別
     * @return 取引戦略リスト
     */
    List<TradeRuleEntity> findAllTradeRule(int uid, int sid, TradeType tradeType);

    /**
     * <p>全ての取引ルールを更新する。</p>
     *
     * @param tradeRules 取引ルールリスト
     */
    void updateAllTradeRule(TradeRules tradeRules);
}
