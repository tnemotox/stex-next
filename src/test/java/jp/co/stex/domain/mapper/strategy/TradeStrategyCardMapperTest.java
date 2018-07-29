package jp.co.stex.domain.mapper.strategy;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import jp.co.stex.domain.mapper.MapperTestBase;
import jp.co.stex.domain.model.strategy.TradeRuleEntity;
import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 取引戦略カードを操作するマッパーのテストクラスです。
 *
 * @author t.nemoto.x
 */
class TradeStrategyCardMapperTest extends MapperTestBase {

    @Autowired
    private TradeStrategyCardMapper target;

    private final int uid = 1;

    private final int pid = 1;

    private final int cid = 1;

    private final List<TradeStrategyCardEntity> initialStrategyCards = Arrays.asList(
        TradeStrategyCardEntity.builder()
            .cid(1)
            .uid(1)
            .pid(2)
            .label("card1")
            .used(true)
            .cardType(1)
            .leftSideIndicatorType(1)
            .leftSideDays(1)
            .rightSideIndicatorType(1)
            .rightSideDays(1)
            .rightSideFixOrFlex(true)
            .rightSideFixValue(1)
            .coefficient(1.1)
            .comparisonType(1)
            .build(),
        TradeStrategyCardEntity.builder()
            .cid(2)
            .uid(1)
            .pid(4)
            .label("card2")
            .used(true)
            .cardType(2)
            .leftSideIndicatorType(2)
            .leftSideDays(2)
            .rightSideIndicatorType(2)
            .rightSideDays(2)
            .rightSideFixOrFlex(false)
            .rightSideFixValue(2)
            .coefficient(2.2)
            .comparisonType(2)
            .build()
    );

    /**
     * @see TradeStrategyCardMapper#findOne(int, int)
     */
    @Nested
    class findOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyCardMapper/")
        @DisplayName("取引戦略カードを1件取得する")
        void _001() {
            TradeStrategyCardEntity actual = target.findOne(uid, cid);
            assertEquals(initialStrategyCards.get(0), actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyCardMapper/")
        @DisplayName("別ユーザの取引戦略カードを取得できない")
        void _002() {
            TradeStrategyCardEntity actual = target.findOne(-1, cid);
            assertNull(actual);
        }
    }

    /**
     * @see TradeStrategyCardMapper#createOne(TradeStrategyCardEntity)
     */
    @Nested
    class createOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyCardMapper/")
        @DisplayName("取引戦略カードを1件作成する")
        void _001() {
            TradeStrategyCardEntity addend = TradeStrategyCardEntity.builder()
                .uid(1)
                .label("label1")
                .cardType(1)
                .leftSideIndicatorType(1)
                .leftSideDays(1)
                .rightSideIndicatorType(1)
                .rightSideDays(1)
                .rightSideFixOrFlex(true)
                .rightSideFixValue(1)
                .coefficient(1)
                .comparisonType(1)
                .build();
            int cid = target.createOne(addend);
            TradeStrategyCardEntity actual = target.findOne(uid, cid);
            addend.setCid(cid);
            assertEquals(addend, actual);
        }
    }

    /**
     * @see TradeStrategyCardMapper#updateOne(TradeStrategyCardEntity)
     */
    @Nested
    class updateOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyCardMapper/")
        @DisplayName("取引戦略カードを1件更新する")
        void _001() {
            TradeStrategyCardEntity updated = TradeStrategyCardEntity.builder()
                .cid(1)
                .uid(1)
                .pid(2)
                .label("label2")
                .used(true)
                .cardType(2)
                .leftSideIndicatorType(2)
                .leftSideDays(2)
                .rightSideIndicatorType(2)
                .rightSideDays(2)
                .rightSideFixOrFlex(false)
                .rightSideFixValue(2)
                .coefficient(2)
                .comparisonType(2)
                .build();
            target.updateOne(updated);
            TradeStrategyCardEntity actual = target.findOne(uid, cid);
            assertEquals(updated, actual);
        }
    }

    /**
     * @see TradeStrategyCardMapper#associateOneWithPalette(TradeStrategyCardEntity)
     */
    @Nested
    class associateOneWithPalette extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyCardMapper/")
        @DisplayName("取引戦略カードと取引戦略パレットを関連付ける")
        void _001() {
            TradeStrategyCardEntity updated = TradeStrategyCardEntity.builder()
                .uid(1)
                .cid(1)
                .pid(1)
                .used(true)
                .build();
            target.associateOneWithPalette(updated);
            TradeStrategyCardEntity actual = target.findOne(uid, cid);
            initialStrategyCards.get(0).setPid(1);
            initialStrategyCards.get(0).setUsed(true);
            assertEquals(initialStrategyCards.get(0), actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyCardMapper/")
        @DisplayName("取引戦略カードと取引戦略パレットを関連を外す")
        void _002() {
            TradeStrategyCardEntity updated = TradeStrategyCardEntity.builder()
                .uid(1)
                .cid(1)
                .pid(0)
                .used(false)
                .build();
            target.associateOneWithPalette(updated);
            TradeStrategyCardEntity actual = target.findOne(uid, cid);
            initialStrategyCards.get(0).setPid(0);
            initialStrategyCards.get(0).setUsed(false);
            assertEquals(initialStrategyCards.get(0), actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyCardMapper/")
        @DisplayName("一意制約を確認する")
        void _003() {
            TradeStrategyCardEntity updated = TradeStrategyCardEntity.builder()
                .uid(1)
                .cid(1)
                .pid(4)
                .used(true)
                .build();
            assertThrows(DuplicateKeyException.class, () -> target.associateOneWithPalette(updated));

            // pidがnullの場合は一意制約違反にならない
            TradeStrategyCardEntity updated2 = TradeStrategyCardEntity.builder()
                .uid(1)
                .cid(1)
                .pid(0)
                .used(false)
                .build();
            target.associateOneWithPalette(updated2);
            updated2 = TradeStrategyCardEntity.builder()
                .uid(1)
                .cid(2)
                .pid(0)
                .used(false)
                .build();
            target.associateOneWithPalette(updated2);
        }
    }

    /**
     * @see TradeStrategyCardMapper#deleteOne(int, int)
     */
    @Nested
    class deleteOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyCardMapper/")
        @DisplayName("取引戦略カードを1件削除する")
        void _001() {
            target.deleteOne(uid, cid);
            TradeStrategyCardEntity actual = target.findOne(uid, cid);
            assertNull(actual);
        }
    }
}
