package jp.co.stex.domain.mapper.repository;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import jp.co.stex.domain.mapper.MapperTestBase;
import jp.co.stex.domain.mapper.strategy.BrandGroupMapper;
import jp.co.stex.domain.model.base.value.VCode;
import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.BrandGroup;
import jp.co.stex.domain.model.strategy.BrandGroups;
import jp.co.stex.domain.model.strategy.value.VBrandGroupName;
import jp.co.stex.domain.model.strategy.value.VGid;
import jp.co.stex.domain.model.strategy.value.VTargetBrandCodes;
import jp.co.stex.domain.repository.BrandGroupRepository;
import jp.co.stex.domain.repository.IBrandGroupRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 銘柄グループを操作するリポジトリのテストクラスです。
 *
 * @author t.nemoto.x
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BrandGroupRepositoryTest extends MapperTestBase {

    @BeforeAll
    void setUp() {
        target = new BrandGroupRepository(brandGroupMapper);
    }

    @Autowired
    private BrandGroupMapper brandGroupMapper;

    private IBrandGroupRepository target;

    private final VUid uid = new VUid("00000000-0000-0000-0000-000000000001");

    private final VUid unknownUid = new VUid("10000000-0000-0000-0000-000000000001");

    private final VGid gid = new VGid("00000000-0000-0000-0000-000000000001");

    private final VGid unknownGid = new VGid("10000000-0000-0000-0000-000000000001");

    private final VGid newGid = new VGid("00000000-0000-0000-0000-000000000004");

    private final BrandGroup newBrandGroup = BrandGroup.builder()
        .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000004")))
        .brandGroupName(new VBrandGroupName("分析銘柄グループ4"))
        .targetBrandCodes(new VTargetBrandCodes(new VCode(1111), new VCode(3333)))
        .build();

    private final BrandGroup updateBrandGroup = BrandGroup.builder()
        .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
        .brandGroupName(new VBrandGroupName("分析銘柄グループ111"))
        .targetBrandCodes(new VTargetBrandCodes(new VCode(2222), new VCode(4444)))
        .build();

    private final BrandGroups brandGroups = new BrandGroups(
        BrandGroup.builder()
            .gid(gid)
            .brandGroupName(new VBrandGroupName("分析銘柄グループ1"))
            .targetBrandCodes(new VTargetBrandCodes(new VCode(1111), new VCode(2222)))
            .build(),
        BrandGroup.builder()
            .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-0002")))
            .brandGroupName(new VBrandGroupName("分析銘柄グループ2"))
            .targetBrandCodes(new VTargetBrandCodes(new VCode(3333), new VCode(4444)))
            .build()
    );

    /**
     * {@link BrandGroupRepository#findOne}
     */
    @Nested
    class findOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/BrandGroupMapper/setup.xml")
        @DisplayName("銘柄グループを1件取得する")
        void _001() {
            BrandGroup actual = target.findOne(uid, gid);
            assertEquals(brandGroups.get(0), actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/BrandGroupMapper/setup.xml")
        @DisplayName("ユーザが異なる銘柄グループを取得できない")
        void _002() {
            BrandGroup actual = target.findOne(unknownUid, gid);
            assertNull(actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/BrandGroupMapper/setup.xml")
        @DisplayName("存在しない銘柄グループを取得できない")
        void _003() {
            BrandGroup actual = target.findOne(uid, unknownGid);
            assertNull(actual);
        }
    }

    /**
     * {@link BrandGroupRepository#findAll}
     */
    @Nested
    class findAll extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/BrandGroupMapper/setup.xml")
        @DisplayName("銘柄グループを全件取得する")
        void _001() {
            BrandGroups actual = target.findAll(uid);
            assertEquals(brandGroups, actual);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/BrandGroupMapper/setup.xml")
        @DisplayName("別ユーザの銘柄グループを取得できない")
        void _002() {
            BrandGroups actual = target.findAll(unknownUid);
            assertEquals(BrandGroups.generateEmptyBrandGroup(), actual);
        }
    }

    /**
     * {@link BrandGroupRepository#createOne}
     */
    @Nested
    class createOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/BrandGroupMapper/setup.xml")
        @ExpectedDatabase(value = "/database/BrandGroupMapper/createOneExpected.xml", table = "brand_groups")
        @DisplayName("銘柄グループを1件追加する")
        void _001() {
            VGid gid = target.createOne(uid, newBrandGroup);
            assertEquals(newGid, gid);
        }
    }

    /**
     * {@link BrandGroupRepository#updateOne}
     */
    @Nested
    class updateOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/BrandGroupMapper/setup.xml")
        @ExpectedDatabase(value = "/database/BrandGroupMapper/updateOneExpected.xml", table = "brand_groups")
        @DisplayName("銘柄グループを1件更新する")
        void _001() {
            target.updateOne(uid, updateBrandGroup);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/BrandGroupMapper/setup.xml")
        @ExpectedDatabase(value = "/database/BrandGroupMapper/setup.xml", table = "brand_groups")
        @DisplayName("ユーザIDが異なる銘柄グループを更新できない")
        void _002() {
            target.updateOne(unknownUid, updateBrandGroup);
        }
    }

    /**
     * {@link BrandGroupRepository#deleteOne}
     */
    @Nested
    class deleteOne extends MapperTestBase {

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/BrandGroupMapper/setup.xml")
        @ExpectedDatabase(value = "/database/BrandGroupMapper/deleteOneExpected.xml", table = "brand_groups")
        @DisplayName("銘柄グループを1件削除する")
        void _001() {
            target.deleteOne(uid, gid);
        }

        @Test
        @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/database/BrandGroupMapper/setup.xml")
        @ExpectedDatabase(value = "/database/BrandGroupMapper/setup.xml", table = "brand_groups")
        @DisplayName("ユーザIDが異なる銘柄グループを削除できない")
        void _002() {
            target.deleteOne(unknownUid, gid);
        }
    }
}
