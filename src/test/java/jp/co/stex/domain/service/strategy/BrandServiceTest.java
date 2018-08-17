package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.mapper.strategy.BrandMapper;
import jp.co.stex.domain.model.strategy.BrandEntity;
import jp.co.stex.domain.model.strategy.code.MarketType;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author t.nemoto.x
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BrandServiceTest {

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private BrandService target;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        target = new BrandService(brandMapper);
    }

    /**
     * {@link BrandService#findAllBrands}
     */
    @Nested
    class findAllBrands {

        private final List<BrandEntity> expected = Arrays.asList(
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

        @BeforeEach
        void setUp() {
            reset(brandMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("銘柄を取得する")
        @Test
        void _001() {
            when(brandMapper.findAll()).thenReturn(expected);
            List<BrandEntity> actual = target.findAllBrands();
            verify(brandMapper, times(1)).findAll();
            assertEquals(expected, actual);
        }
    }
}
