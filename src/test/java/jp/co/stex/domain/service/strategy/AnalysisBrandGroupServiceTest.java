package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.mapper.strategy.AnalysisBrandGroupMapper;
import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author t.nemoto.x
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AnalysisBrandGroupServiceTest {

    @Mock
    private AnalysisBrandGroupMapper analysisBrandGroupMapper;

    @InjectMocks
    private AnalysisBrandGroupService target;

    private final int uid = 1;

    private final int sid = 1;

    private final int gid = 1;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        target = new AnalysisBrandGroupService(analysisBrandGroupMapper);
    }

    /**
     * {@link AnalysisBrandGroupService#findAllAnalysisBrandGroup}
     */
    @Nested
    class findAllAnalysisBrandGroup {

        private final List<AnalysisBrandGroupEntity> expected = Arrays.asList(
            AnalysisBrandGroupEntity.builder()
                .uid(uid)
                .gid(gid)
                .label("分析銘柄グループ1")
                .brands(Arrays.asList(1111, 2222))
                .build(),
            AnalysisBrandGroupEntity.builder()
                .uid(uid)
                .gid(2)
                .brands(Arrays.asList(3333, 4444))
                .label("分析銘柄グループ2")
                .build()
        );

        @BeforeEach
        void setUp() {
            reset(analysisBrandGroupMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("分析銘柄グループを全件取得する")
        @Test
        void _001() {
            when(target.findAllAnalysisBrandGroup(anyInt())).thenReturn(expected);
            List<AnalysisBrandGroupEntity> actual = target.findAllAnalysisBrandGroup(uid);
            verify(analysisBrandGroupMapper, times(1)).findAll(anyInt());
            assertEquals(expected, actual);
        }

        @DisplayName("分析銘柄グループを0件取得する")
        @Test
        void _002() {
            when(target.findAllAnalysisBrandGroup(anyInt())).thenReturn(Collections.emptyList());
            List<AnalysisBrandGroupEntity> actual = target.findAllAnalysisBrandGroup(uid);
            verify(analysisBrandGroupMapper, times(1)).findAll(anyInt());
            assertEquals(Collections.emptyList(), actual);
        }
    }

    /**
     * {@link AnalysisBrandGroupService#createOneAnalysisBrandGroup}
     */
    @Nested
    class createOneAnalysisBrandGroup {

        @Captor
        private ArgumentCaptor<AnalysisBrandGroupEntity> captor;

        private final AnalysisBrandGroupEntity args = AnalysisBrandGroupEntity.builder()
            .uid(uid)
            .gid(gid)
            .label("分析銘柄グループ1")
            .brands(Arrays.asList(1111, 2222))
            .build();

        @BeforeEach
        void setUp() {
            reset(analysisBrandGroupMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("分析銘柄グループを追加する")
        @Test
        void _001() {
            when(target.createOneAnalysisBrandGroup(args)).thenReturn(gid);
            int actual = target.createOneAnalysisBrandGroup(args);
            verify(analysisBrandGroupMapper, times(1)).createOne(any());
            verify(analysisBrandGroupMapper).createOne(captor.capture());
            assertEquals(args, captor.getValue());
            assertEquals(actual, gid);
        }
    }

    /**
     * {@link AnalysisBrandGroupService#updateOneAnalysisBrandGroup}
     */
    @Nested
    class updateOneAnalysisBrandGroup {

        @Captor
        private ArgumentCaptor<AnalysisBrandGroupEntity> captor;

        private final AnalysisBrandGroupEntity args = AnalysisBrandGroupEntity.builder()
            .uid(uid)
            .gid(gid)
            .label("分析銘柄グループ1")
            .brands(Arrays.asList(1111, 2222))
            .build();

        @BeforeEach
        void setUp() {
            reset(analysisBrandGroupMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("分析銘柄グループを更新する")
        @Test
        void _001() {
            doNothing().when(analysisBrandGroupMapper).updateOne(captor.capture());
            target.updateOneAnalysisBrandGroup(args);
            verify(analysisBrandGroupMapper, times(1)).updateOne(any());
            assertEquals(args, captor.getValue());
        }
    }

    /**
     * {@link AnalysisBrandGroupService#deleteOneAnalysisBrandGroup}
     */
    @Nested
    class deleteOneAnalysisBrandGroup {

        @BeforeEach
        void setUp() {
            reset(analysisBrandGroupMapper);
            MockitoAnnotations.initMocks(this);
        }

        @DisplayName("分析銘柄グループを削除する")
        @Test
        void _001() {
            doNothing().when(analysisBrandGroupMapper).deleteOne(anyInt(), anyInt());
            target.deleteOneAnalysisBrandGroup(uid, sid);
            verify(analysisBrandGroupMapper, times(1)).deleteOne(anyInt(), anyInt());
        }
    }
}
