package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.mapper.strategy.TradeStrategyMapper;
import jp.co.stex.domain.model.strategy.TradeStrategyEntity;
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
class TradeStrategyServiceTest {

    @Mock
    private TradeStrategyMapper tradeStrategyMapper;

    @InjectMocks
    private TradeStrategyService target;

    private final int uid = 1;

    private final int sid = 1;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        target = new TradeStrategyService(tradeStrategyMapper);
    }

    /**
     * {@link TradeStrategyService#findAllTradeStrategy}
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
     * {@link TradeStrategyService#createOneTradeStrategy}
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
     * {@link TradeStrategyService#updateOneTradeStrategy}
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
     * {@link TradeStrategyService#deleteOneTradeStrategy}
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
}
