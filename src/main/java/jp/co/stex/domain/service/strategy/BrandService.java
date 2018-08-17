package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.mapper.strategy.BrandMapper;
import jp.co.stex.domain.model.strategy.BrandEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>取引戦略を操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {

    private final BrandMapper brandMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BrandEntity> findAllBrands() {
        return brandMapper.findAll();
    }
}
