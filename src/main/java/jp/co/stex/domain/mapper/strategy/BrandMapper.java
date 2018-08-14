package jp.co.stex.domain.mapper.strategy;

import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
import jp.co.stex.domain.model.strategy.BrandEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>銘柄を操作するマッパーです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Mapper
@Repository
public interface BrandMapper {

    /**
     * <p>銘柄を全て取得する。</p>
     *
     * @return 銘柄リスト
     */
    List<BrandEntity> findAll();
}
