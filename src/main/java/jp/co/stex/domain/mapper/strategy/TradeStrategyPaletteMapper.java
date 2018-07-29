package jp.co.stex.domain.mapper.strategy;

import jp.co.stex.domain.model.strategy.TradeStrategyPaletteEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>取引戦略パレットを操作するマッパーです。</p>
 * <p>ユーザIDが合致するもののみ操作可能です。</p>
 *
 * @author Kuneo
 * @since 1.0
 */
@Mapper
@Repository
public interface TradeStrategyPaletteMapper {

    /**
     * <p>取引戦略パレット。</p>
     *
     * @param uid ユーザID
     * @param pid 取引戦略パレットID
     * @return 取引戦略パレット
     */
    TradeStrategyPaletteEntity findOne(@Param("uid") int uid, @Param("pid") int pid);

    /**
     * <p>全ての取引戦略パレットを作成する。</p>
     *
     * @param palettes 取引戦略パレット
     * @return 作成した取引戦略パレットのID
     */
    List<Integer> createAll(@Param("palettes") List<TradeStrategyPaletteEntity> palettes);

    /**
     * <p>指定した全ての取引戦略パレットを更新する。</p>
     *
     * @param palettes 取引戦略パレット
     */
    void updateAll(@Param("palettes") List<TradeStrategyPaletteEntity> palettes);

    /**
     * <p>指定した全ての取引戦略パレットを削除する。</p>
     *
     * @param uid ユーザID
     * @param pids 取引戦略パレットID
     */
    void deleteAll(@Param("uid") int uid, @Param("pids") List<Integer> pids);
}
