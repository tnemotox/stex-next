package jp.co.stex.domain.mapper.strategy;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import jp.co.stex.domain.mapper.MapperTestBase;
import jp.co.stex.domain.model.strategy.TradeStrategyEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 取引戦略を操作するマッパーのテストクラスです。
 *
 * @author t.nemoto.x
 */
class TradeStrategyMapperTest extends MapperTestBase {

    @Autowired
    private TradeStrategyMapper target;

    private final int uid = 1;

    private final int sid = 1;

    private final List<TradeStrategyEntity> initialData = Arrays.asList(
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

    /**
     * @see TradeStrategyMapper#findAll(int)
     */
    @Nested
    class findAll extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/")
        @DisplayName("取引戦略を全件取得する")
        void _001() {
            List<TradeStrategyEntity> actual = target.findAll(uid);
            assertEquals(2, actual.size());
            assertEquals(initialData, actual);
        }
    }

    /**
     * @see TradeStrategyMapper#findOne(int, int)
     */
    @Nested
    class findOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/")
        @DisplayName("取引戦略を1件取得する")
        void _001() {
            TradeStrategyEntity actual = target.findOne(uid, sid);
            assertEquals(initialData.get(0), actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/")
        @DisplayName("別ユーザの取引戦略を取得できない")
        void _002() {
            TradeStrategyEntity actual = target.findOne(-1, sid);
            assertNull(actual);
        }
    }

    /**
     * @see TradeStrategyMapper#createOne(TradeStrategyEntity)
     */
    @Nested
    class createOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/")
        @DisplayName("取引戦略を1件追加する")
        void _001() {
            TradeStrategyEntity created = TradeStrategyEntity.builder()
                .gid(1)
                .label("label4")
                .analysisStartDate(LocalDate.of(2017, 1, 1))
                .analysisEndDate(LocalDate.of(2017, 12, 31))
                .analyzedAt(LocalDate.of(2018, 4, 1))
                .memo("memo4")
                .build();

            int sid = target.createOne(created);
            created.setSid(sid);
            TradeStrategyEntity actual = target.findOne(uid, sid);
            assertEquals(created, actual);
        }
    }

    /**
     * @see TradeStrategyMapper#updateOne(TradeStrategyEntity)
     */
    @Nested
    class updateOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/")
        @DisplayName("取引戦略を1件更新する")
        void _001() {
            TradeStrategyEntity updated = TradeStrategyEntity.builder()
                .sid(sid)
                .gid(1)
                .label("updated")
                .analysisStartDate(LocalDate.of(2017, 2, 1))
                .analysisEndDate(LocalDate.of(2017, 11, 30))
                .analyzedAt(LocalDate.of(2018, 5, 1))
                .memo("updated")
                .build();

            target.updateOne(updated);
            TradeStrategyEntity actual = target.findOne(uid, sid);
            assertEquals(updated, actual);
        }
    }

    /**
     * @see TradeStrategyMapper#deleteOne(int, int)
     */
    @Nested
    class deleteOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/TradeStrategyMapper/")
        @DisplayName("取引戦略を1件削除する")
        void _001() {
            target.deleteOne(uid, sid);
            TradeStrategyEntity actual = target.findOne(uid, sid);
            assertNull(actual);
        }
    }
}
