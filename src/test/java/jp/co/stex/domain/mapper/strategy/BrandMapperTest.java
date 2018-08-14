package jp.co.stex.domain.mapper.strategy;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import jp.co.stex.domain.mapper.MapperTestBase;
import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
import jp.co.stex.domain.model.strategy.BrandEntity;
import jp.co.stex.domain.model.strategy.code.MarketType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 銘柄を操作するマッパーのテストクラスです。
 *
 * @author t.nemoto.x
 */
class BrandMapperTest extends MapperTestBase {

    @Autowired
    private BrandMapper target;

    private final List<BrandEntity> initialBrands = Arrays.asList(
        BrandEntity.builder()
            .code(1111)
            .name("銘柄1")
            .market(MarketType.TSE_FIRST)
            .detail("説明1")
            .build(),
        BrandEntity.builder()
            .code(3333)
            .name("銘柄3")
            .market(MarketType.TSE_SECOND)
            .detail("説明3")
            .build()
    );

    /**
     * {@link BrandMapper#findAll}
     */
    @Nested
    class findAll extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/BrandMapper/")
        @DisplayName("有効な銘柄を全件取得する")
        void _001() {
            List<BrandEntity> actual = target.findAll();
            assertEquals(initialBrands, actual);
        }
    }
}
