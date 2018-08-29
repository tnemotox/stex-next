package jp.co.stex.domain.mapper.strategy;

import jp.co.stex.domain.model.strategy.TradeRuleEntity;

import jp.co.stex.domain.model.strategy.code.TradeType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>取引ルールを操作するマッパーです。</p>
 * <p>取引ルールに紐付く取引戦略パレット、取引戦略カードを合わせて取得します。</p>
 * <p>ユーザIDが合致するもののみ操作可能です。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Mapper
@Repository
public interface TradeRuleMapper {

    /**
     * <p>取引ルールを取得する。</p>
     *
     * @param uid ユーザID
     * @param rid 取引ルールID
     * @return 取引ルール
     */
    TradeRuleEntity findOne(@Param("uid") int uid, @Param("rid") int rid);

    /**
     * <p>取引戦略IDと仕掛けフラグに紐付く取引戦略ルールを取得する。</p>
     *
     * @param uid ユーザID
     * @param sid 取引戦略ID
     * @param tradeType 仕掛けフラグ
     * @return 取引戦略
     */
    List<TradeRuleEntity> findAllByTradeType(@Param("uid") int uid, @Param("sid") int sid, @Param("tradeType") TradeType tradeType);

    /**
     * <p>全ての取引戦略を作成する。</p>
     *
     * @param rule 取引戦略
     * @return 作成した取引戦略のID
     */
    List<Integer> createAll(@Param("rules") List<TradeRuleEntity> rule);

    /**
     * <p>指定した全ての取引戦略を更新する。</p>
     *
     * @param rule 取引戦略
     */
    void updateAll(@Param("rules") List<TradeRuleEntity> rule);

    /**
     * <p>指定した全ての取引戦略を削除する。</p>
     *
     * @param uid ユーザID
     * @param rids 取引戦略ID
     */
    void deleteAll(@Param("uid") int uid, @Param("rids") List<Integer> rids);
}
