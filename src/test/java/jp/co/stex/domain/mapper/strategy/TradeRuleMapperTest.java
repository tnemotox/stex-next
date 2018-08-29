package jp.co.stex.domain.mapper.strategy;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import jp.co.stex.domain.mapper.MapperTestBase;
import jp.co.stex.domain.model.strategy.TradeRuleEntity;
import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import jp.co.stex.domain.model.strategy.TradeStrategyPaletteEntity;
import jp.co.stex.domain.model.strategy.code.*;
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

    private final TradeType tradeType = TradeType.ENTRY;

    private final TradeRuleEntity initialTradeRule = TradeRuleEntity.builder()
        .rid(1)
        .uid(1)
        .sid(1)
        .tradingDayType(TradingDayType.TODAY)
        .buyingAndSellingType(BuyingAndSellingType.BUYING)
        .orderType(OrderType.OPENING_ORDER)
        .tradeType(TradeType.ENTRY)
        .orderBy(1)
        .build();

    private final List<TradeRuleEntity> initialTradeRules = Arrays.asList(
        TradeRuleEntity.builder()
            .rid(1)
            .uid(1)
            .sid(1)
            .tradingDayType(TradingDayType.TODAY)
            .buyingAndSellingType(BuyingAndSellingType.BUYING)
            .orderType(OrderType.OPENING_ORDER)
            .tradeType(TradeType.ENTRY)
            .orderBy(1)
            .palettes(Arrays.asList(
                TradeStrategyPaletteEntity.builder()
                    .pid(1)
                    .uid(1)
                    .rid(1)
                    .cid(1)
                    .leftJointType(JointType.NOTHING)
                    .rightJointType(JointType.AND)
                    .nestOpen(NestType.ENABLE)
                    .nestClose(NestType.DISABLE)
                    .orderBy(1)
                    .build(),
                TradeStrategyPaletteEntity.builder()
                    .pid(2)
                    .uid(1)
                    .rid(1)
                    .cid(2)
                    .leftJointType(JointType.OR)
                    .rightJointType(JointType.NOTHING)
                    .nestOpen(NestType.DISABLE)
                    .nestClose(NestType.ENABLE)
                    .orderBy(2)
                    .build()
                )
            )
            .build(),
        TradeRuleEntity.builder()
            .rid(2)
            .uid(1)
            .sid(1)
            .tradingDayType(TradingDayType.TOMORROW)
            .buyingAndSellingType(BuyingAndSellingType.SELLING)
            .orderType(OrderType.ENDING_ORDER)
            .tradeType(TradeType.ENTRY)
            .orderBy(2)
            .palettes(Arrays.asList(
                TradeStrategyPaletteEntity.builder()
                    .pid(3)
                    .uid(1)
                    .rid(2)
                    .leftJointType(JointType.NOTHING)
                    .rightJointType(JointType.AND)
                    .nestOpen(NestType.ENABLE)
                    .nestClose(NestType.DISABLE)
                    .orderBy(1)
                    .build(),
                TradeStrategyPaletteEntity.builder()
                    .pid(4)
                    .uid(1)
                    .rid(2)
                    .leftJointType(JointType.OR)
                    .rightJointType(JointType.NOTHING)
                    .nestOpen(NestType.DISABLE)
                    .nestClose(NestType.ENABLE)
                    .orderBy(2)
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
     * @see TradeRuleMapper#findAllByTradeType(int, int, TradeType)
     */
    @Nested
    class findAllByTradeType extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeRuleMapper/")
        @DisplayName("仕掛けフラグが仕掛けの取引ルールを取得する")
        void _001() {
            List<TradeRuleEntity> actual = target.findAllByTradeType(uid, sid, tradeType);
            assertEquals(initialTradeRules, actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeRuleMapper/")
        @DisplayName("別ユーザの取引ルールを取得できない")
        void _002() {
            List<TradeRuleEntity> actual = target.findAllByTradeType(-1, sid, tradeType);
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
                    .tradingDayType(TradingDayType.TODAY)
                    .buyingAndSellingType(BuyingAndSellingType.BUYING)
                    .orderType(OrderType.OPENING_ORDER)
                    .tradeType(TradeType.ENTRY)
                    .orderBy(1)
                    .build(),
                TradeRuleEntity.builder()
                    .uid(1)
                    .sid(2)
                    .tradingDayType(TradingDayType.TODAY)
                    .buyingAndSellingType(BuyingAndSellingType.BUYING)
                    .orderType(OrderType.OPENING_ORDER)
                    .tradeType(TradeType.EXIT)
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
                    .tradingDayType(TradingDayType.TOMORROW)
                    .buyingAndSellingType(BuyingAndSellingType.SELLING)
                    .orderType(OrderType.ENDING_ORDER)
                    .tradeType(TradeType.ENTRY)
                    .orderBy(1)
                    .build(),
                TradeRuleEntity.builder()
                    .rid(2)
                    .uid(1)
                    .sid(1)
                    .tradingDayType(TradingDayType.TODAY)
                    .buyingAndSellingType(BuyingAndSellingType.SELLING)
                    .orderType(OrderType.MARKET_ORDER)
                    .tradeType(TradeType.EXIT)
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
                    .tradingDayType(TradingDayType.TOMORROW)
                    .buyingAndSellingType(BuyingAndSellingType.SELLING)
                    .orderType(OrderType.ENDING_ORDER)
                    .tradeType(TradeType.ENTRY)
                    .orderBy(1)
                    .build(),
                TradeRuleEntity.builder()
                    .rid(2)
                    .uid(1)
                    .sid(1)
                    .tradingDayType(TradingDayType.TODAY)
                    .buyingAndSellingType(BuyingAndSellingType.BUYING)
                    .orderType(OrderType.MARKET_ORDER)
                    .tradeType(TradeType.ENTRY)
                    .orderBy(1)
                    .build()
            );
            assertThrows(DuplicateKeyException.class, () -> target.updateAll(tradeRules));

            tradeRules.get(1).setOrderBy(2);
            tradeRules.get(1).setTradeType(TradeType.EXIT);
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
