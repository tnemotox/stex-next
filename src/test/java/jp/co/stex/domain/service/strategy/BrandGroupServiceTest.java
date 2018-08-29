package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.base.value.VCode;
import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.BrandGroup;
import jp.co.stex.domain.model.strategy.BrandGroups;
import jp.co.stex.domain.model.strategy.value.VBrandGroupName;
import jp.co.stex.domain.model.strategy.value.VGid;
import jp.co.stex.domain.model.strategy.value.VTargetBrandCodes;
import jp.co.stex.domain.repository.IBrandGroupRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author t.nemoto.x
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BrandGroupServiceTest {

    @Mock
    private IBrandGroupRepository brandGroupRepository;

    @InjectMocks
    private BrandGroupService target;

    private final VUid uid = new VUid("00000000-0000-0000-0000-000000000001");

    private final VGid gid = new VGid("00000000-0000-0000-0000-000000000001");

    private final VGid newGid = new VGid("00000000-0000-0000-0000-000000000004");

    private final BrandGroup newBrandGroup = BrandGroup.builder()
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
            .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000002")))
            .brandGroupName(new VBrandGroupName("分析銘柄グループ2"))
            .targetBrandCodes(new VTargetBrandCodes(new VCode(3333), new VCode(4444)))
            .build()
    );

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        target = new BrandGroupService(brandGroupRepository);
    }

    /**
     * {@link BrandGroupService#findAll}
     */
    @Nested
    class findAll {

        @BeforeEach
        void setUp() {
            reset(brandGroupRepository);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("銘柄グループ一覧を取得する")
        @Test
        void _001() {
            when(brandGroupRepository.findAll(any())).thenReturn(brandGroups);
            BrandGroups actual = target.findAll(uid);
            verify(brandGroupRepository, times(1)).findAll(any());
            assertEquals(brandGroups, actual);
        }

        @DisplayName("銘柄グループ一覧を取得できない")
        @Test
        void _002() {
            when(brandGroupRepository.findAll(any())).thenReturn(BrandGroups.generateEmptyBrandGroup());
            BrandGroups actual = target.findAll(uid);
            verify(brandGroupRepository, times(1)).findAll(any());
            assertEquals(BrandGroups.generateEmptyBrandGroup(), actual);
        }
    }

    /**
     * {@link BrandGroupService#createOne}
     */
    @Nested
    class createOne {

        @Captor
        private ArgumentCaptor<BrandGroup> captor;

        @BeforeEach
        void setUp() {
            reset(brandGroupRepository);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("銘柄グループを追加する")
        @Test
        void _001() {
            when(brandGroupRepository.createOne(any(), captor.capture())).thenReturn(newGid);
            VGid actual = target.createOne(uid, newBrandGroup);
            verify(brandGroupRepository, times(1)).createOne(any(), any());
            verify(brandGroupRepository).createOne(any(), captor.capture());
            assertEquals(new BrandGroup(newBrandGroup, captor.getValue().getGid()), captor.getValue());
            assertEquals(actual, newGid);
        }
    }

    /**
     * {@link BrandGroupService#updateOne}
     */
    @Nested
    class updateOne {

        @Captor
        private ArgumentCaptor<BrandGroup> captor;

        @BeforeEach
        void setUp() {
            reset(brandGroupRepository);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("分析銘柄グループを更新する")
        @Test
        void _001() {
            doNothing().when(brandGroupRepository).updateOne(any(), captor.capture());
            target.updateOne(uid, updateBrandGroup);
            verify(brandGroupRepository, times(1)).updateOne(any(), any());
            assertEquals(updateBrandGroup, captor.getValue());
        }
    }

    /**
     * {@link BrandGroupService#deleteOne}
     */
    @Nested
    class deleteOne {

        @BeforeEach
        void setUp() {
            reset(brandGroupRepository);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("銘柄グループを削除する")
        @Test
        void _001() {
            doNothing().when(brandGroupRepository).deleteOne(any(), any());
            target.deleteOne(uid, gid);
            verify(brandGroupRepository, times(1)).deleteOne(any(), any());
        }
    }
}
