package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.TradeStrategies;
import jp.co.stex.domain.model.strategy.TradeStrategy;
import jp.co.stex.domain.model.strategy.value.*;
import jp.co.stex.domain.repository.TradeStrategyRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author t.nemoto.x
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TradeStrategyServiceTest {

    @Mock
    private TradeStrategyRepository tradeStrategyRepository;

    @InjectMocks
    private TradeStrategyService target;

    private final VUid uid = new VUid("00000000-0000-0000-0000-0001");

    private final VSid sid = new VSid("00000000-0000-0000-0000-0001");

    private final VSid newSid = new VSid("00000000-0000-0000-0000-0004");

    private final TradeStrategy newTradeStrategy = TradeStrategy.builder()
        .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
        .tradeStrategyName(new VTradeStrategyName("戦略4"))
        .termOfAnalysis(new VTermOfAnalysis(
            Arrays.asList(
                LocalDate.of(2017, 1, 1),
                LocalDate.of(2017, 12, 31)
            )
        ))
        .memo(new VMemo("コメント4"))
        .build();

    private final TradeStrategy updateTradeStrategy = TradeStrategy.builder()
        .sid(new VSid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
        .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
        .tradeStrategyName(new VTradeStrategyName("戦略111"))
        .termOfAnalysis(new VTermOfAnalysis(
            Arrays.asList(
                LocalDate.of(2017, 2, 1),
                LocalDate.of(2017, 11, 30)
            )
        ))
        .memo(new VMemo("コメント111"))
        .build();

    private final TradeStrategies tradeStrategies = new TradeStrategies(
        TradeStrategy.builder()
            .sid(new VSid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
            .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
            .tradeStrategyName(new VTradeStrategyName("戦略1"))
            .termOfAnalysis(new VTermOfAnalysis(
                Arrays.asList(
                    LocalDate.of(2017, 1, 1),
                    LocalDate.of(2017, 12, 31)
                )
            ))
            .memo(new VMemo("コメント1"))
            .analyzedAt(new VAnalyzedAt(LocalDate.of(2018, 4, 1)))
            .build(),
        TradeStrategy.builder()
            .sid(new VSid(UUID.fromString("00000000-0000-0000-0000-000000000002")))
            .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
            .tradeStrategyName(new VTradeStrategyName("戦略2"))
            .termOfAnalysis(new VTermOfAnalysis(
                Arrays.asList(
                    LocalDate.of(2017, 1, 1),
                    LocalDate.of(2017, 12, 31)
                )
            ))
            .memo(new VMemo("コメント2"))
            .analyzedAt(new VAnalyzedAt(LocalDate.of(2018, 4, 1)))
            .build()
    );

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        target = new TradeStrategyService(tradeStrategyRepository);
    }

    /**
     * {@link TradeStrategyService#findAll}
     */
    @Nested
    class findAll {

        @BeforeEach
        void setUp() {
            reset(tradeStrategyRepository);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("取引戦略一覧を取得する")
        @Test
        void _001() {
            when(tradeStrategyRepository.findAll(any())).thenReturn(tradeStrategies);
            TradeStrategies actual = target.findAll(uid);
            verify(tradeStrategyRepository, times(1)).findAll(any());
            assertEquals(tradeStrategies, actual);
        }

        @DisplayName("取引戦略一覧を取得できない")
        @Test
        void _002() {
            when(tradeStrategyRepository.findAll(any())).thenReturn(TradeStrategies.generateEmptyTradeStrategies());
            TradeStrategies actual = target.findAll(uid);
            verify(tradeStrategyRepository, times(1)).findAll(any());
            assertEquals(TradeStrategies.generateEmptyTradeStrategies(), actual);
        }
    }

    /**
     * {@link TradeStrategyService#createOne}
     */
    @Nested
    class createOne {

        @Captor
        private ArgumentCaptor<TradeStrategy> captor;

        @BeforeEach
        void setUp() {
            reset(tradeStrategyRepository);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("取引戦略を追加する")
        @Test
        void _001() {
            when(tradeStrategyRepository.createOne(any(), captor.capture())).thenReturn(newSid);
            VSid actual = target.createOne(uid, newTradeStrategy);
            verify(tradeStrategyRepository, times(1)).createOne(any(), any());
            assertEquals(new TradeStrategy(newTradeStrategy, captor.getValue().getSid()), captor.getValue());
            assertEquals(actual, newSid);
        }
    }

    /**
     * {@link TradeStrategyService#updateOne}
     */
    @Nested
    class updateOne {

        @Captor
        private ArgumentCaptor<TradeStrategy> captor;

        @BeforeEach
        void setUp() {
            reset(tradeStrategyRepository);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("取引戦略を更新する")
        @Test
        void _001() {
            doNothing().when(tradeStrategyRepository).updateOne(any(), captor.capture());
            target.updateOne(uid, updateTradeStrategy);
            verify(tradeStrategyRepository, times(1)).updateOne(any(), any());
            assertEquals(updateTradeStrategy, captor.getValue());
        }
    }

    /**
     * {@link TradeStrategyService#deleteOne}
     */
    @Nested
    class deleteOne {

        @BeforeEach
        void setUp() {
            reset(tradeStrategyRepository);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("取引戦略を削除する")
        @Test
        void _001() {
            doNothing().when(tradeStrategyRepository).deleteOne(any(), any());
            target.deleteOne(uid, sid);
            verify(tradeStrategyRepository, times(1)).deleteOne(any(), any());
        }
    }
}
