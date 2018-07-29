package jp.co.stex.domain.mapper.strategy;

import jp.co.stex.domain.model.strategy.TradeStrategyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>取引戦略を操作するマッパーです。</p>
 * <p>ユーザIDが合致するもののみ操作可能です。</p>
 *
 * @author Kuneo
 * @since 1.0
 */
@Mapper
@Repository
public interface TradeStrategyMapper {

    /**
     * <p>指定した取引戦略を1件取得する。</p>
     *
     * @param uid ユーザID
     * @return 取引戦略リスト
     */
    TradeStrategyEntity findOne(@Param("uid") int uid, @Param("sid") int sid);

    /**
     * <p>取引戦略を全て取得する。</p>
     *
     * @param uid ユーザID
     * @return 取引戦略リスト
     */
    List<TradeStrategyEntity> findAll(@Param("uid") int uid);

    /**
     * <p>取引戦略を新規作成する。</p>
     *
     * @param strategy 取引戦略
     * @return 取引戦略ID
     */
    int createOne(@Param("strategy") TradeStrategyEntity strategy);

    /**
     * <p>取引戦略IDが合致するレコードを更新する。</p>
     *
     * @param strategy 取引戦略
     */
    void updateOne(@Param("strategy") TradeStrategyEntity strategy);

    /**
     * <p>取引戦略IDが合致するレコードを削除する。</p>
     *
     * @param uid ユーザID
     * @param sid 取引戦略
     */
    void deleteOne(@Param("uid") int uid, @Param("sid") int sid);
}
