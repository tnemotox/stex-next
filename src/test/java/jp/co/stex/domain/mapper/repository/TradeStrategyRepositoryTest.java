package jp.co.stex.domain.mapper.repository;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import jp.co.stex.domain.mapper.MapperTestBase;
import jp.co.stex.domain.mapper.strategy.TradeStrategyMapper;
import jp.co.stex.domain.model.strategy.value.VMemo;
import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.TradeStrategies;
import jp.co.stex.domain.model.strategy.TradeStrategy;
import jp.co.stex.domain.model.strategy.value.*;
import jp.co.stex.domain.repository.ITradeStrategyRepository;
import jp.co.stex.domain.repository.TradeStrategyRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 取引戦略を操作するリポジトリのテストクラスです。
 *
 * @author t.nemoto.x
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TradeStrategyRepositoryTest extends MapperTestBase {

    @BeforeAll
    void setUp() {
        target = new TradeStrategyRepository(tradeStrategyMapper);
    }

    @Autowired
    private TradeStrategyMapper tradeStrategyMapper;

    private ITradeStrategyRepository target;

    private final VUid uid = new VUid("00000000-0000-0000-0000-0001");

    private final VUid unknownUid = new VUid("10000000-0000-0000-0000-0001");

    private final VSid sid = new VSid("00000000-0000-0000-0000-0001");

    private final VSid unknownSid = new VSid("10000000-0000-0000-0000-0001");

    private final VSid newSid = new VSid("00000000-0000-0000-0000-0004");

    private final TradeStrategy newTradeStrategy = TradeStrategy.builder()
        .sid(new VSid(UUID.fromString("00000000-0000-0000-0000-000000000004")))
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

    /**
     * @see TradeStrategyRepository#findOne
     */
    @Nested
    class findOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/setup.xml")
        @DisplayName("取引戦略を1件取得する")
        void _001() {
            TradeStrategy actual = target.findOne(uid, sid);
            assertEquals(tradeStrategies.get(0), actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/setup.xml")
        @DisplayName("ユーザが異なる取引戦略を取得できない")
        void _002() {
            TradeStrategy actual = target.findOne(unknownUid, sid);
            assertNull(actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/setup.xml")
        @DisplayName("存在しない取引戦略を取得できない")
        void _003() {
            TradeStrategy actual = target.findOne(uid, unknownSid);
            assertNull(actual);
        }
    }

    /**
     * @see TradeStrategyRepository#findAll
     */
    @Nested
    class findAll extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/setup.xml")
        @DisplayName("取引戦略を全件取得する")
        void _001() {
            TradeStrategies actual = target.findAll(uid);
            assertEquals(tradeStrategies, actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/setup.xml")
        @DisplayName("ユーザが異なる取引戦略を取得できない")
        void _002() {
            TradeStrategies actual = target.findAll(unknownUid);
            assertEquals(TradeStrategies.generateEmptyTradeStrategies(), actual);
        }
    }

    /**
     * @see TradeStrategyRepository#createOne
     */
    @Nested
    class createOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/setup.xml")
        @ExpectedDatabase(value = "/database/TradeStrategyMapper/createOneExpected.xml", table = "trade_strategies")
        @DisplayName("取引戦略を1件追加する")
        void _001() {
            VSid sid = target.createOne(uid, newTradeStrategy);
            assertEquals(newSid, sid);
        }
    }

    /**
     * @see TradeStrategyRepository#updateOne
     */
    @Nested
    class updateOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/setup.xml")
        @ExpectedDatabase(value = "/database/TradeStrategyMapper/updateOneExpected.xml", table = "trade_strategies")
        @DisplayName("取引戦略を1件更新する")
        void _001() {
            target.updateOne(uid, updateTradeStrategy);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/setup.xml")
        @ExpectedDatabase(value = "/database/TradeStrategyMapper/setup.xml", table = "trade_strategies")
        @DisplayName("ユーザIDが異なる取引戦略を削除できない")
        void _002() {
            target.updateOne(unknownUid, updateTradeStrategy);
        }
    }

    /**
     * @see TradeStrategyRepository#deleteOne
     */
    @Nested
    class deleteOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/setup.xml")
        @ExpectedDatabase(value = "/database/TradeStrategyMapper/deleteOneExpected.xml", table = "trade_strategies")
        @DisplayName("取引戦略を1件削除する")
        void _001() {
            target.deleteOne(uid, sid);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/setup.xml")
        @ExpectedDatabase(value = "/database/TradeStrategyMapper/setup.xml", table = "trade_strategies")
        @DisplayName("ユーザIDが異なる取引戦略を削除できない")
        void _002() {
            target.deleteOne(unknownUid, sid);
        }
    }
}
