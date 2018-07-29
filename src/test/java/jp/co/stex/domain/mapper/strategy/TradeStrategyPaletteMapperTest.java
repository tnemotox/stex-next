package jp.co.stex.domain.mapper.strategy;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import jp.co.stex.domain.mapper.MapperTestBase;
import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import jp.co.stex.domain.model.strategy.TradeStrategyPaletteEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 取引戦略パレットを操作するマッパーのテストクラスです。
 *
 * @author t.nemoto.x
 */
class TradeStrategyPaletteMapperTest extends MapperTestBase {

    @Autowired
    private TradeStrategyPaletteMapper target;

    @Autowired
    private TradeStrategyCardMapper cardMapper;

    private final int uid = 1;

    private final int pid = 1;

    private final List<TradeStrategyPaletteEntity> initialTradeStrategyPalettes = Arrays.asList(
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
            .build(),
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
            .build()
    );

    /**
     * @see TradeStrategyPaletteMapper#findOne(int, int)
     */
    @Nested
    class findOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyPaletteMapper/")
        @DisplayName("取引戦略パレットを1件取得する")
        void _001() {
            TradeStrategyPaletteEntity actual = target.findOne(uid, pid);
            assertEquals(initialTradeStrategyPalettes.get(0), actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyPaletteMapper/")
        @DisplayName("別ユーザの取引戦略パレットを取得できない")
        void _002() {
            TradeStrategyPaletteEntity actual = target.findOne(-1, pid);
            assertNull(actual);
        }
    }

    /**
     * @see TradeStrategyPaletteMapper#createAll(List)
     */
    @Nested
    class createAll extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyPaletteMapper/")
        @DisplayName("取引戦略パレットを追加する")
        void _001() {
            List<TradeStrategyPaletteEntity> addend = Arrays.asList(
                TradeStrategyPaletteEntity.builder()
                    .uid(1)
                    .rid(1)
                    .leftJointType(1)
                    .rightJointType(1)
                    .nestOpen(true)
                    .nestClose(true)
                    .orderBy(10)
                    .build(),
                TradeStrategyPaletteEntity.builder()
                    .uid(1)
                    .rid(1)
                    .leftJointType(2)
                    .rightJointType(2)
                    .nestOpen(true)
                    .nestClose(true)
                    .orderBy(11)
                    .build()
            );
            List<Integer> pids = target.createAll(addend);
            TradeStrategyPaletteEntity palette1 = target.findOne(uid, pids.get(0));
            TradeStrategyPaletteEntity palette2 = target.findOne(uid, pids.get(1));
            addend.get(0).setPid(pids.get(0));
            addend.get(1).setPid(pids.get(1));
            assertEquals(addend.get(0), palette1);
            assertEquals(addend.get(1), palette2);
        }
    }

    /**
     * @see TradeStrategyPaletteMapper#updateAll(List)
     */
    @Nested
    class updateAll extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyPaletteMapper/")
        @DisplayName("取引戦略パレットを更新する")
        void _001() {
            List<TradeStrategyPaletteEntity> updated = Arrays.asList(
                TradeStrategyPaletteEntity.builder()
                    .pid(1)
                    .uid(1)
                    .rid(1)
                    .leftJointType(2)
                    .rightJointType(2)
                    .nestOpen(false)
                    .nestClose(false)
                    .orderBy(20)
                    .build(),
                TradeStrategyPaletteEntity.builder()
                    .pid(2)
                    .uid(1)
                    .rid(1)
                    .leftJointType(1)
                    .rightJointType(1)
                    .nestOpen(false)
                    .nestClose(false)
                    .orderBy(21)
                    .build()
            );
            target.updateAll(updated);
            TradeStrategyPaletteEntity palette1 = target.findOne(uid, updated.get(0).getPid());
            TradeStrategyPaletteEntity palette2 = target.findOne(uid, updated.get(1).getPid());
            assertEquals(updated.get(0), palette1);
            assertEquals(updated.get(1), palette2);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyPaletteMapper/")
        @DisplayName("一意制約を確認する")
        void _002() {
            List<TradeStrategyPaletteEntity> updated = Collections.singletonList(
                TradeStrategyPaletteEntity.builder()
                    .pid(2)
                    .uid(1)
                    .rid(1)
                    .leftJointType(1)
                    .rightJointType(1)
                    .nestOpen(false)
                    .nestClose(false)
                    .orderBy(1)
                    .build()
            );

            assertThrows(DuplicateKeyException.class, () -> target.updateAll(updated));
        }
    }

    /**
     * @see TradeStrategyPaletteMapper#deleteAll(int, List)
     */
    @Nested
    class deleteAll extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyPaletteMapper/")
        @DisplayName("取引戦略パレットを削除する")
        void _001() {
            List<Integer> pids = Arrays.asList(1, 2, 3, 4);
            target.deleteAll(uid, pids);
            TradeStrategyPaletteEntity actual = target.findOne(uid, pid);
            assertNull(actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyPaletteMapper/")
        @DisplayName("取引戦略パレットと取引戦略カードの紐付けが外れる")
        void _002() {
            List<Integer> pids = Arrays.asList(1, 2, 3, 4);
            target.deleteAll(uid, pids);
            TradeStrategyCardEntity actual = cardMapper.findOne(uid, 2);
            assertEquals(actual.getPid(), 0);
        }
    }
}
