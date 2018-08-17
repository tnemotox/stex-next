package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.mapper.strategy.TradeStrategyCardMapper;
import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import jp.co.stex.domain.model.strategy.code.CardType;
import jp.co.stex.domain.model.strategy.code.ComparisonType;
import jp.co.stex.domain.model.strategy.code.IndicatorType;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author t.nemoto.x
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TradeStrategyCardServiceTest {

    @Mock
    private TradeStrategyCardMapper tradeStrategyCardMapper;

    @InjectMocks
    private TradeStrategyCardService target;

    private final int uid = 1;

    private final int gid = 1;

    private final int sid = 1;

    private final int cid = 1;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        target = new TradeStrategyCardService(tradeStrategyCardMapper);
    }

    /**
     * {@link TradeStrategyCardService#createOneTradeStrategyCard}
     */
    @Nested
    class createOneTradeStrategyCard {

        @Captor
        private ArgumentCaptor<TradeStrategyCardEntity> captor;

        private final TradeStrategyCardEntity args = TradeStrategyCardEntity.builder()
                .uid(uid)
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
            when(tradeStrategyCardMapper.createOne(args)).thenReturn(gid);
            int actual = target.createOneTradeStrategyCard(args);
            verify(tradeStrategyCardMapper, times(1)).createOne(any());
            verify(tradeStrategyCardMapper).createOne(captor.capture());
            assertEquals(args, captor.getValue());
            assertEquals(actual, gid);
        }
    }

    /**
     * {@link TradeStrategyCardService#updateOneTradeStrategyCard}
     */
    @Nested
    class updateOneTradeStrategyCard {

        @Captor
        private ArgumentCaptor<TradeStrategyCardEntity> captor;

        private final TradeStrategyCardEntity args = TradeStrategyCardEntity.builder()
            .uid(uid)
            .cid(cid)
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
     * {@link TradeStrategyCardService#deleteOneTradeStrategyCard}
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
}
