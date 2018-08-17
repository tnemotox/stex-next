package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.strategy.BrandEntity;

import java.util.List;

/**
 * <p>銘柄を操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
public interface IBrandService {

    /**
     * <p>銘柄一覧を取得する。</p>
     *
     * @return 銘柄一覧
     */
    List<BrandEntity> findAllBrands();
}
