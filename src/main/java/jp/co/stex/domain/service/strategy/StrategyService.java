package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.strategy.TradeStrategyEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>取引戦略を操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
public interface StrategyService {

    /**
     * <p>全ての取引戦略を取得する。</p>
     *
     * @param loginUserId ログインユーザID
     * @return 取引戦略リスト
     */
    List<TradeStrategyEntity> findAll(int loginUserId);

    /**
     * <p>取引戦略を新規作成する。</p>
     *
     * @param uid ユーザID
     * @param tradeStrategy 取引戦略
     */
    void createOne(int uid, TradeStrategyEntity tradeStrategy);
}
