package jp.co.stex.domain.mapper.strategy;

import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.TradeStrategy;
import jp.co.stex.domain.model.strategy.value.VSid;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>取引戦略を操作するマッパーです。</p>
 * <p>ユーザIDが合致するもののみ操作可能です。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Mapper
@Component
public interface TradeStrategyMapper {

    /**
     * <p>指定した取引戦略を1件取得する。</p>
     *
     * @param uid ユーザID
     * @param sid 取引戦略ID
     * @return 取引戦略リスト
     */
    TradeStrategy findOne(@Param("uid") VUid uid, @Param("sid") VSid sid);

    /**
     * <p>取引戦略を全て取得する。</p>
     *
     * @param uid ユーザID
     * @return 取引戦略リスト
     */
    List<TradeStrategy> findAll(@Param("uid") VUid uid);

    /**
     * <p>取引戦略を新規作成する。</p>
     *
     * @param uid ユーザID
     * @param tradeStrategy 取引戦略
     * @return 取引戦略ID
     */
    VSid createOne(@Param("uid") VUid uid, @Param("tradeStrategy") TradeStrategy tradeStrategy);

    /**
     * <p>取引戦略IDが合致するレコードを更新する。</p>
     *
     * @param tradeStrategy 取引戦略
     */
    void updateOne(@Param("uid") VUid uid, @Param("tradeStrategy") TradeStrategy tradeStrategy);

    /**
     * <p>取引戦略IDが合致するレコードを削除する。</p>
     *
     * @param uid ユーザID
     * @param sid 取引戦略
     */
    void deleteOne(@Param("uid") VUid uid, @Param("sid") VSid sid);
}
