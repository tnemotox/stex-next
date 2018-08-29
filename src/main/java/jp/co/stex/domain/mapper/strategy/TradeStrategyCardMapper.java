package jp.co.stex.domain.mapper.strategy;

import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>取引戦略カードを操作するマッパーです。</p>
 * <p>ユーザIDが合致するもののみ操作可能です。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Mapper
@Repository
public interface TradeStrategyCardMapper {

    /**
     * <p>指定した取引戦略カードを取得する。</p>
     *
     * @param uid ユーザID
     * @param cid 取引戦略カードID
     * @return 取引戦略カード
     */
    TradeStrategyCardEntity findOne(@Param("uid") int uid, @Param("cid") int cid);

    /**
     * <p>取引戦略IDに紐づく取引戦略カードを取得する。</p>
     *
     * @param uid ユーザID
     * @param sid 取引戦略ID
     * @return 取引戦略カード一覧
     */
    List<TradeStrategyCardEntity> findAllBySid(@Param("uid") int uid, @Param("sid") int sid);

    /**
     * <p>取引戦略カードを作成する。</p>
     *
     * @param card 取引戦略カード
     * @return 取引戦略カードID
     */
    int createOne(@Param("card") TradeStrategyCardEntity card);

    /**
     * <p>指定した取引戦略カードを更新する。</p>
     *
     * @param card 取引戦略カード
     */
    void updateOne(@Param("card") TradeStrategyCardEntity card);

    /**
     * <p>指定した取引戦略カードを削除する。</p>
     *
     * @param uid ユーザID
     * @param cid 取引戦略カードID
     */
    void deleteOne(@Param("uid") int uid, @Param("cid") int cid);
}
