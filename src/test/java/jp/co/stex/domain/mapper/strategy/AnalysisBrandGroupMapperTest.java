package jp.co.stex.domain.mapper.strategy;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import jp.co.stex.domain.mapper.MapperTestBase;
import jp.co.stex.domain.model.strategy.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 分析銘柄グループを操作するマッパーのテストクラスです。
 *
 * @author t.nemoto.x
 */
class AnalysisBrandGroupMapperTest extends MapperTestBase {

    @Autowired
    private AnalysisBrandGroupMapper target;

    private final int uid = 1;

    private final int gid = 1;

    private final List<AnalysisBrandGroupEntity> initialAnalyzeBrandGroups = Arrays.asList(
        AnalysisBrandGroupEntity.builder()
            .uid(uid)
            .gid(gid)
            .label("分析銘柄グループ1")
            .brands(Arrays.asList(1111, 2222))
            .build(),
        AnalysisBrandGroupEntity.builder()
            .uid(uid)
            .gid(2)
            .label("分析銘柄グループ2")
            .brands(Arrays.asList(3333, 4444))
            .build()
    );

    /**
     * {@link AnalysisBrandGroupMapper#findAll}
     */
    @Nested
    class findAll extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/AnalysisBrandGroupMapper/")
        @DisplayName("分析銘柄グループを全件取得する")
        void _001() {
            List<AnalysisBrandGroupEntity> actual = target.findAll(uid);
            assertEquals(initialAnalyzeBrandGroups, actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/AnalysisBrandGroupMapper/")
        @DisplayName("別ユーザの分析銘柄グループを取得できない")
        void _002() {
            List<AnalysisBrandGroupEntity> actual = target.findAll(-1);
            assertEquals(0, actual.size());
        }
    }

    /**
     * {@link AnalysisBrandGroupMapper#createOne}
     */
    @Nested
    class createOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/AnalysisBrandGroupMapper/")
        @DisplayName("分析銘柄グループを1件追加する")
        void _001() {
            AnalysisBrandGroupEntity created = AnalysisBrandGroupEntity.builder()
                .uid(uid)
                .label("分析銘柄グループ3")
                .brands(Arrays.asList(5555, 6666))
                .build();

            int gid = target.createOne(created);
            created.setGid(gid);
            AnalysisBrandGroupEntity actual = target.findOne(uid, gid);
            assertEquals(created, actual);
        }
    }

    /**
     * {@link AnalysisBrandGroupMapper#updateOne}
     */
    @Nested
    class updateOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/AnalysisBrandGroupMapper/")
        @DisplayName("分析銘柄グループを1件更新する")
        void _001() {
            AnalysisBrandGroupEntity updated = AnalysisBrandGroupEntity.builder()
                .uid(uid)
                .gid(gid)
                .label("分析銘柄グループ3")
                .brands(Arrays.asList(5555, 6666))
                .build();

            target.updateOne(updated);
            AnalysisBrandGroupEntity actual = target.findOne(uid, gid);
            assertEquals(updated, actual);
        }
    }

    /**
     * {@link AnalysisBrandGroupMapper#deleteOne}
     */
    @Nested
    class deleteOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/AnalysisBrandGroupMapper/")
        @DisplayName("分析銘柄グループを1件削除する")
        void _001() {
            target.deleteOne(uid, gid);
            AnalysisBrandGroupEntity actual = target.findOne(uid, gid);
            assertNull(actual);
        }
    }
}
