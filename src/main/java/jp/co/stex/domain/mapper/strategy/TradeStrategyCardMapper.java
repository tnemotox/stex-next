package jp.co.stex.domain.mapper.strategy;

import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>取引戦略カードを操作するマッパーです。</p>
 * <p>ユーザIDが合致するもののみ操作可能です。</p>
 *
 * @author Kuneo
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
     * <p>指定した取引戦略カードと取引戦略パレットの紐付けを更新する。</p>
     *
     * @param card 取引戦略カード
     */
    void associateOneWithPalette(@Param("card") TradeStrategyCardEntity card);

    /**
     * <p>指定した取引戦略カードを削除する。</p>
     *
     * @param uid ユーザID
     * @param cid 取引戦略カードID
     */
    void deleteOne(@Param("uid") int uid, @Param("cid") int cid);
}
