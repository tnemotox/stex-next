package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.mapper.strategy.AnalysisBrandGroupMapper;
import jp.co.stex.domain.mapper.strategy.BrandMapper;
import jp.co.stex.domain.mapper.strategy.TradeStrategyCardMapper;
import jp.co.stex.domain.mapper.strategy.TradeStrategyMapper;
import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
import jp.co.stex.domain.model.strategy.BrandEntity;
import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import jp.co.stex.domain.model.strategy.TradeStrategyEntity;
import jp.co.stex.domain.model.strategy.code.CardType;
import jp.co.stex.domain.model.strategy.code.ComparisonType;
import jp.co.stex.domain.model.strategy.code.IndicatorType;
import jp.co.stex.domain.model.strategy.code.MarketType;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author t.nemoto.x
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StrategyServiceTest {

    @Mock
    private TradeStrategyMapper tradeStrategyMapper;

    @Mock
    private TradeStrategyCardMapper tradeStrategyCardMapper;

    @Mock
    private AnalysisBrandGroupMapper analysisBrandGroupMapper;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private StrategyServiceImpl target;

    private final int uid = 1;

    private final int sid = 1;

    private final int gid = 1;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        target = new StrategyServiceImpl(
            tradeStrategyMapper,
            tradeStrategyCardMapper,
            analysisBrandGroupMapper,
            brandMapper
        );
    }

    /**
     * {@link StrategyService#findAllTradeStrategy}
     */
    @Nested
    class findAllTradeStrategy {

        private final List<TradeStrategyEntity> expected = Arrays.asList(
            TradeStrategyEntity.builder()
                .sid(1)
                .gid(1)
                .label("label1")
                .analysisStartDate(LocalDate.of(2017, 1, 1))
                .analysisEndDate(LocalDate.of(2017, 12, 31))
                .analyzedAt(LocalDate.of(2018, 4, 1))
                .memo("memo1")
                .build(),
            TradeStrategyEntity.builder()
                .sid(2)
                .gid(1)
                .label("label2")
                .analysisStartDate(LocalDate.of(2017, 1, 1))
                .analysisEndDate(LocalDate.of(2017, 12, 31))
                .analyzedAt(LocalDate.of(2018, 4, 1))
                .memo("memo2")
                .build()
        );

        @BeforeEach
        void setUp() {
            reset(tradeStrategyMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("取引戦略を全件取得する")
        @Test
        void _001() {
            when(target.findAllTradeStrategy(anyInt())).thenReturn(expected);
            List<TradeStrategyEntity> actual = target.findAllTradeStrategy(uid);
            verify(tradeStrategyMapper, times(1)).findAll(anyInt());
            assertEquals(expected, actual);
        }

        @DisplayName("取引戦略を0件取得する")
        @Test
        void _002() {
            when(target.findAllTradeStrategy(anyInt())).thenReturn(Collections.emptyList());
            List<TradeStrategyEntity> actual = target.findAllTradeStrategy(uid);
            verify(tradeStrategyMapper, times(1)).findAll(anyInt());
            assertEquals(Collections.emptyList(), actual);
        }
    }

    /**
     * {@link StrategyService#createOneTradeStrategy}
     */
    @Nested
    class createOneTradeStrategy {

        @Captor
        private ArgumentCaptor<TradeStrategyEntity> captor;

        private final TradeStrategyEntity args = TradeStrategyEntity.builder()
            .uid(1)
            .gid(1)
            .label("label1")
            .analysisStartDate(LocalDate.of(2017, 1, 1))
            .analysisEndDate(LocalDate.of(2017, 12, 31))
            .analyzedAt(LocalDate.of(2018, 4, 1))
            .memo("memo1")
            .build();

        @BeforeEach
        void setUp() {
            reset(tradeStrategyMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("取引戦略を追加する")
        @Test
        void _001() {
            when(target.createOneTradeStrategy(captor.capture())).thenReturn(sid);
            int actual = target.createOneTradeStrategy(args);
            verify(tradeStrategyMapper, times(1)).createOne(any());
            assertEquals(args, captor.getValue());
            assertEquals(actual, sid);
        }
    }

    /**
     * {@link StrategyService#updateOneTradeStrategy}
     */
    @Nested
    class updateOneTradeStrategy {

        @Captor
        private ArgumentCaptor<TradeStrategyEntity> captor;

        private final TradeStrategyEntity args = TradeStrategyEntity.builder()
            .uid(1)
            .sid(1)
            .gid(1)
            .label("label1")
            .analysisStartDate(LocalDate.of(2017, 1, 1))
            .analysisEndDate(LocalDate.of(2017, 12, 31))
            .analyzedAt(LocalDate.of(2018, 4, 1))
            .memo("memo1")
            .build();

        @BeforeEach
        void setUp() {
            reset(tradeStrategyMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("取引戦略を更新する")
        @Test
        void _001() {
            doNothing().when(tradeStrategyMapper).updateOne(captor.capture());
            target.updateOneTradeStrategy(args);
            verify(tradeStrategyMapper, times(1)).updateOne(any());
            assertEquals(args, captor.getValue());
        }
    }

    /**
     * {@link StrategyService#deleteOneTradeStrategy}
     */
    @Nested
    class deleteOneTradeStrategy {

        @BeforeEach
        void setUp() {
            reset(tradeStrategyMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("取引戦略を削除する")
        @Test
        void _001() {
            doNothing().when(tradeStrategyMapper).deleteOne(anyInt(), anyInt());
            target.deleteOneTradeStrategy(uid, sid);
            verify(tradeStrategyMapper, times(1)).deleteOne(anyInt(), anyInt());
        }
    }

    /**
     * {@link StrategyService#findAllAnalysisBrandGroup}
     */
    @Nested
    class findAllAnalysisBrandGroup {

        private final List<AnalysisBrandGroupEntity> expected = Arrays.asList(
            AnalysisBrandGroupEntity.builder()
                .uid(uid)
                .gid(gid)
                .label("分析銘柄グループ1")
                .brands(Arrays.asList(1111, 2222))
                .build(),
            AnalysisBrandGroupEntity.builder()
                .uid(uid)
                .gid(2)
                .brands(Arrays.asList(3333, 4444))
                .label("分析銘柄グループ2")
                .build()
        );

        @BeforeEach
        void setUp() {
            reset(analysisBrandGroupMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("分析銘柄グループを全件取得する")
        @Test
        void _001() {
            when(target.findAllAnalysisBrandGroup(anyInt())).thenReturn(expected);
            List<AnalysisBrandGroupEntity> actual = target.findAllAnalysisBrandGroup(uid);
            verify(analysisBrandGroupMapper, times(1)).findAll(anyInt());
            assertEquals(expected, actual);
        }

        @DisplayName("分析銘柄グループを0件取得する")
        @Test
        void _002() {
            when(target.findAllAnalysisBrandGroup(anyInt())).thenReturn(Collections.emptyList());
            List<AnalysisBrandGroupEntity> actual = target.findAllAnalysisBrandGroup(uid);
            verify(analysisBrandGroupMapper, times(1)).findAll(anyInt());
            assertEquals(Collections.emptyList(), actual);
        }
    }

    /**
     * {@link StrategyService#createOneAnalysisBrandGroup}
     */
    @Nested
    class createOneAnalysisBrandGroup {

        @Captor
        private ArgumentCaptor<AnalysisBrandGroupEntity> captor;

        private final AnalysisBrandGroupEntity args = AnalysisBrandGroupEntity.builder()
            .uid(uid)
            .gid(gid)
            .label("分析銘柄グループ1")
            .brands(Arrays.asList(1111, 2222))
            .build();

        @BeforeEach
        void setUp() {
            reset(analysisBrandGroupMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("分析銘柄グループを追加する")
        @Test
        void _001() {
            when(target.createOneAnalysisBrandGroup(args)).thenReturn(gid);
            int actual = target.createOneAnalysisBrandGroup(args);
            verify(analysisBrandGroupMapper, times(1)).createOne(any());
            verify(analysisBrandGroupMapper).createOne(captor.capture());
            assertEquals(args, captor.getValue());
            assertEquals(actual, gid);
        }
    }

    /**
     * {@link StrategyService#updateOneAnalysisBrandGroup}
     */
    @Nested
    class updateOneAnalysisBrandGroup {

        @Captor
        private ArgumentCaptor<AnalysisBrandGroupEntity> captor;

        private final AnalysisBrandGroupEntity args = AnalysisBrandGroupEntity.builder()
            .uid(uid)
            .gid(gid)
            .label("分析銘柄グループ1")
            .brands(Arrays.asList(1111, 2222))
            .build();

        @BeforeEach
        void setUp() {
            reset(analysisBrandGroupMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("分析銘柄グループを更新する")
        @Test
        void _001() {
            doNothing().when(analysisBrandGroupMapper).updateOne(captor.capture());
            target.updateOneAnalysisBrandGroup(args);
            verify(analysisBrandGroupMapper, times(1)).updateOne(any());
            assertEquals(args, captor.getValue());
        }
    }

    /**
     * {@link StrategyService#deleteOneAnalysisBrandGroup}
     */
    @Nested
    class deleteOneAnalysisBrandGroup {

        @BeforeEach
        void setUp() {
            reset(analysisBrandGroupMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("分析銘柄グループを削除する")
        @Test
        void _001() {
            doNothing().when(analysisBrandGroupMapper).deleteOne(anyInt(), anyInt());
            target.deleteOneAnalysisBrandGroup(uid, sid);
            verify(analysisBrandGroupMapper, times(1)).deleteOne(anyInt(), anyInt());
        }
    }

    /**
     * {@link StrategyService#createOneTradeStrategyCard}
     */
    @Nested
    class createOneTradeStrategyCard {

        @Captor
        private ArgumentCaptor<TradeStrategyCardEntity> captor;

        private final TradeStrategyCardEntity args = TradeStrategyCardEntity.builder()
                .uid(1)
                .cardType(CardType.COMPARE)
                .leftSideIndicatorType(IndicatorType._1_移動平均線)
                .leftSideDays(1)
                .rightSideFixOrFlex(true)
                .rightSideFixValue(1)
                .comparisonType(ComparisonType.MORE)
                .build();

        @BeforeEach
        void setUp() {
            reset(tradeStrategyCardMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("取引戦略カードを追加する")
        @Test
        void _001() {
            when(target.createOneTradeStrategyCard(args)).thenReturn(gid);
            int actual = target.createOneTradeStrategyCard(args);
            verify(tradeStrategyCardMapper, times(1)).createOne(any());
            verify(tradeStrategyCardMapper).createOne(captor.capture());
            assertEquals(args, captor.getValue());
            assertEquals(actual, gid);
        }
    }

    /**
     * {@link StrategyService#updateOneTradeStrategyCard}
     */
    @Nested
    class updateOneTradeStrategyCard {

        @Captor
        private ArgumentCaptor<TradeStrategyCardEntity> captor;

        private final TradeStrategyCardEntity args = TradeStrategyCardEntity.builder()
            .uid(1)
            .cid(1)
            .cardType(CardType.COMPARE)
            .leftSideIndicatorType(IndicatorType._1_移動平均線)
            .leftSideDays(1)
            .rightSideFixOrFlex(true)
            .rightSideFixValue(1)
            .comparisonType(ComparisonType.MORE)
            .build();

        @BeforeEach
        void setUp() {
            reset(tradeStrategyCardMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("分析銘柄グループを更新する")
        @Test
        void _001() {
            doNothing().when(tradeStrategyCardMapper).updateOne(captor.capture());
            target.updateOneTradeStrategyCard(args);
            verify(tradeStrategyCardMapper, times(1)).updateOne(any());
            assertEquals(args, captor.getValue());
        }
    }

    /**
     * {@link StrategyService#deleteOneTradeStrategyCard}
     */
    @Nested
    class deleteOneTradeStrategyCard {

        @BeforeEach
        void setUp() {
            reset(tradeStrategyCardMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("分析銘柄グループを削除する")
        @Test
        void _001() {
            doNothing().when(tradeStrategyCardMapper).deleteOne(anyInt(), anyInt());
            target.deleteOneTradeStrategyCard(uid, sid);
            verify(tradeStrategyCardMapper, times(1)).deleteOne(anyInt(), anyInt());
        }
    }

    /**
     * {@link StrategyService#findAllBrands}
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
