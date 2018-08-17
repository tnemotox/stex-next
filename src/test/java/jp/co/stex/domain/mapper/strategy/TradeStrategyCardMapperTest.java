package jp.co.stex.domain.mapper.strategy;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import jp.co.stex.domain.mapper.MapperTestBase;
import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import jp.co.stex.domain.model.strategy.code.CardType;
import jp.co.stex.domain.model.strategy.code.ComparisonType;
import jp.co.stex.domain.model.strategy.code.CrossType;
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

    private final int sid = 1;

    private final List<TradeStrategyCardEntity> initialStrategyCards = Arrays.asList(
        TradeStrategyCardEntity.builder()
            .cid(1)
            .uid(1)
            .pid(2)
            .sid(1)
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
            .build(),
        TradeStrategyCardEntity.builder()
            .cid(2)
            .uid(1)
            .pid(4)
            .sid(1)
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
            .crossType(CrossType.GO_UP)
            .build(),
        TradeStrategyCardEntity.builder()
            .cid(3)
            .uid(1)
            .sid(1)
            .label("card3")
            .used(false)
            .cardType(CardType.TIME)
            .elapsedDay(1)
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
     * @see TradeStrategyCardMapper#findAllBySid(int, int)
     */
    @Nested
    class findAllBySid extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyCardMapper/")
        @DisplayName("取引戦略IDが合致する取引戦略カードを全て取得する")
        void _001() {
            List<TradeStrategyCardEntity> actual = target.findAllBySid(uid, sid);
            assertEquals(initialStrategyCards, actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyCardMapper/")
        @DisplayName("ユーザIDが合致しないため取引戦略カードを取得できない")
        void _002() {
            List<TradeStrategyCardEntity> actual = target.findAllBySid(-1, sid);
            assertEquals(0, actual.size());
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyCardMapper/")
        @DisplayName("取引戦略IDが合致しないため取引戦略カードを取得できない")
        void _003() {
            List<TradeStrategyCardEntity> actual = target.findAllBySid(uid, -1);
            assertEquals(0, actual.size());
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
                .sid(1)
                .label("label1")
                .cardType(CardType.COMPARE)
                .leftSideIndicatorType(IndicatorType._1_移動平均線)
                .leftSideDays(1)
                .rightSideIndicatorType(IndicatorType._1_移動平均線)
                .rightSideDays(1)
                .rightSideFixOrFlex(true)
                .rightSideFixValue(1)
                .coefficient(1)
                .comparisonType(ComparisonType.MORE)
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
                .sid(1)
                .label("label2")
                .used(true)
                .cardType(CardType.CROSS)
                .leftSideIndicatorType(IndicatorType._2_移動平均線乖離率)
                .leftSideDays(2)
                .rightSideIndicatorType(IndicatorType._2_移動平均線乖離率)
                .rightSideDays(2)
                .rightSideFixOrFlex(false)
                .rightSideFixValue(2)
                .coefficient(2)
                .comparisonType(ComparisonType.MORE_THAN)
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
                .sid(1)
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
                .sid(1)
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
