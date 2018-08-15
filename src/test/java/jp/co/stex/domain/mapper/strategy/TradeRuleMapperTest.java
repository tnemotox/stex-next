package jp.co.stex.domain.mapper.strategy;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import jp.co.stex.domain.mapper.MapperTestBase;
import jp.co.stex.domain.model.strategy.TradeRuleEntity;
import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import jp.co.stex.domain.model.strategy.TradeStrategyPaletteEntity;
import jp.co.stex.domain.model.strategy.code.CardType;
import jp.co.stex.domain.model.strategy.code.ComparisonType;
import jp.co.stex.domain.model.strategy.code.IndicatorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 取引ルールを操作するマッパーのテストクラスです。
 *
 * @author t.nemoto.x
 */
class TradeRuleMapperTest extends MapperTestBase {

    @Autowired
    private TradeRuleMapper target;

    private final int uid = 1;

    private final int sid = 1;

    private final int rid = 1;

    private final boolean inOrExit = true;

    private final TradeRuleEntity initialTradeRule = TradeRuleEntity.builder()
        .rid(1)
        .uid(1)
        .sid(1)
        .todayOrTomorrow(true)
        .buyOrSell(true)
        .tradeTimingType(1)
        .inOrExit(true)
        .orderBy(1)
        .build();

    private final List<TradeRuleEntity> initialTradeRules = Arrays.asList(
        TradeRuleEntity.builder()
            .rid(1)
            .uid(1)
            .sid(1)
            .todayOrTomorrow(true)
            .buyOrSell(true)
            .tradeTimingType(1)
            .inOrExit(true)
            .orderBy(1)
            .palettes(Arrays.asList(
                TradeStrategyPaletteEntity.builder()
                    .pid(1)
                    .uid(1)
                    .rid(1)
                    .leftJointType(0)
                    .rightJointType(1)
                    .nestOpen(true)
                    .nestClose(false)
                    .orderBy(1)
                    .build(),
                TradeStrategyPaletteEntity.builder()
                    .pid(2)
                    .uid(1)
                    .rid(1)
                    .leftJointType(2)
                    .rightJointType(0)
                    .nestOpen(false)
                    .nestClose(true)
                    .orderBy(2)
                    .card(TradeStrategyCardEntity.builder()
                        .cid(1)
                        .uid(1)
                        .pid(2)
                        .label("card1")
                        .used(true)
                        .cardType(CardType.COMPARE)
                        .leftSideIndicatorType(IndicatorType._1_移動平均線)
                        .leftSideDays(1)
                        .rightSideIndicatorType(IndicatorType._1_移動平均線)
                        .rightSideDays(1)
                        .rightSideFixOrFlex(true)
                        .rightSideFixValue(1)
                        .coefficient(1.1)
                        .comparisonType(ComparisonType.MORE)
                        .build()
                    )
                    .build()
                )
            )
            .build(),
        TradeRuleEntity.builder()
            .rid(2)
            .uid(1)
            .sid(1)
            .todayOrTomorrow(false)
            .buyOrSell(false)
            .tradeTimingType(2)
            .inOrExit(true)
            .orderBy(2)
            .palettes(Arrays.asList(
                TradeStrategyPaletteEntity.builder()
                    .pid(3)
                    .uid(1)
                    .rid(2)
                    .leftJointType(0)
                    .rightJointType(1)
                    .nestOpen(true)
                    .nestClose(false)
                    .orderBy(1)
                    .build(),
                TradeStrategyPaletteEntity.builder()
                    .pid(4)
                    .uid(1)
                    .rid(2)
                    .leftJointType(2)
                    .rightJointType(0)
                    .nestOpen(false)
                    .nestClose(true)
                    .orderBy(2)
                    .card(TradeStrategyCardEntity.builder()
                        .cid(2)
                        .uid(1)
                        .pid(4)
                        .label("card2")
                        .used(true)
                        .cardType(CardType.CROSS)
                        .leftSideIndicatorType(IndicatorType._2_移動平均線乖離率)
                        .leftSideDays(2)
                        .rightSideIndicatorType(IndicatorType._2_移動平均線乖離率)
                        .rightSideDays(2)
                        .rightSideFixOrFlex(false)
                        .rightSideFixValue(2)
                        .coefficient(2.2)
                        .comparisonType(ComparisonType.MORE_THAN)
                        .build()
                    )
                    .build()
                )
            )
            .build()
    );

    /**
     * @see TradeRuleMapper#findOne(int, int)
     */
    @Nested
    class findOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeRuleMapper/")
        @DisplayName("取引ルールを1件取得する")
        void _001() {
            TradeRuleEntity actual = target.findOne(uid, rid);
            assertEquals(initialTradeRule, actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeRuleMapper/")
        @DisplayName("別ユーザの取引ルールを取得できない")
        void _002() {
            TradeRuleEntity actual = target.findOne(-1, rid);
            assertNull(actual);
        }
    }

    /**
     * @see TradeRuleMapper#findAllByInOrExit(int, int, boolean)
     */
    @Nested
    class findAllByInOrExit extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeRuleMapper/")
        @DisplayName("仕掛けフラグが仕掛けの取引ルールを取得する")
        void _001() {
            List<TradeRuleEntity> actual = target.findAllByInOrExit(uid, sid, inOrExit);
            assertEquals(initialTradeRules, actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeRuleMapper/")
        @DisplayName("別ユーザの取引ルールを取得できない")
        void _002() {
            List<TradeRuleEntity> actual = target.findAllByInOrExit(-1, sid, inOrExit);
            assertEquals(0, actual.size());
        }
    }

    /**
     * @see TradeRuleMapper#createAll(List)
     */
    @Nested
    class createAll extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeRuleMapper/")
        @DisplayName("取引ルールを追加する")
        void _001() {
            List<TradeRuleEntity> tradeRules = Arrays.asList(
                TradeRuleEntity.builder()
                    .uid(1)
                    .sid(2)
                    .todayOrTomorrow(true)
                    .buyOrSell(true)
                    .tradeTimingType(1)
                    .inOrExit(true)
                    .orderBy(1)
                    .build(),
                TradeRuleEntity.builder()
                    .uid(1)
                    .sid(2)
                    .todayOrTomorrow(true)
                    .buyOrSell(true)
                    .tradeTimingType(1)
                    .inOrExit(false)
                    .orderBy(1)
                    .build()
            );

            List<Integer> rids = target.createAll(tradeRules);
            TradeRuleEntity tradeRule1 = target.findOne(uid, rids.get(0));
            TradeRuleEntity tradeRule2 = target.findOne(uid, rids.get(1));
            tradeRules.get(0).setRid(rids.get(0));
            tradeRules.get(1).setRid(rids.get(1));
            assertEquals(tradeRules.get(0), tradeRule1);
            assertEquals(tradeRules.get(1), tradeRule2);
        }
    }

    /**
     * @see TradeRuleMapper#updateAll(List)
     */
    @Nested
    class updateAll extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeRuleMapper/")
        @DisplayName("取引ルールを更新する")
        void _001() {
            List<TradeRuleEntity> tradeRules = Arrays.asList(
                TradeRuleEntity.builder()
                    .rid(1)
                    .uid(1)
                    .sid(1)
                    .todayOrTomorrow(false)
                    .buyOrSell(false)
                    .tradeTimingType(2)
                    .inOrExit(true)
                    .orderBy(1)
                    .build(),
                TradeRuleEntity.builder()
                    .rid(2)
                    .uid(1)
                    .sid(1)
                    .todayOrTomorrow(true)
                    .buyOrSell(true)
                    .tradeTimingType(3)
                    .inOrExit(false)
                    .orderBy(2)
                    .build()
            );

            target.updateAll(tradeRules);
            TradeRuleEntity tradeRule1 = target.findOne(uid, tradeRules.get(0).getRid());
            TradeRuleEntity tradeRule2 = target.findOne(uid, tradeRules.get(1).getRid());
            assertEquals(tradeRules.get(0), tradeRule1);
            assertEquals(tradeRules.get(1), tradeRule2);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeRuleMapper/")
        @DisplayName("一意制約を確認する")
        void _002() {
            List<TradeRuleEntity> tradeRules = Arrays.asList(
                TradeRuleEntity.builder()
                    .rid(1)
                    .uid(1)
                    .sid(1)
                    .todayOrTomorrow(false)
                    .buyOrSell(false)
                    .tradeTimingType(2)
                    .inOrExit(true)
                    .orderBy(1)
                    .build(),
                TradeRuleEntity.builder()
                    .rid(2)
                    .uid(1)
                    .sid(1)
                    .todayOrTomorrow(true)
                    .buyOrSell(true)
                    .tradeTimingType(3)
                    .inOrExit(true)
                    .orderBy(1)
                    .build()
            );
            assertThrows(DuplicateKeyException.class, () -> target.updateAll(tradeRules));

            tradeRules.get(1).setOrderBy(2);
            tradeRules.get(1).setInOrExit(false);
            target.updateAll(tradeRules);
        }
    }

    /**
     * @see TradeRuleMapper#deleteAll(int, List)
     */
    @Nested
    class deleteAll extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeRuleMapper/")
        @DisplayName("取引ルールを削除する")
        void _001() {
            List<Integer> rids = Arrays.asList(1, 2, 3);
            target.deleteAll(uid, rids);
            TradeRuleEntity actual = target.findOne(uid, rid);
            assertNull(actual);
        }
    }
}
