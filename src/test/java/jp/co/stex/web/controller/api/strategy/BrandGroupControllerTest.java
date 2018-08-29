package jp.co.stex.web.controller.api.strategy;

import com.fasterxml.jackson.databind.SerializationFeature;
import jp.co.stex.domain.model.base.value.VCode;
import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.BrandGroup;
import jp.co.stex.domain.model.strategy.BrandGroups;
import jp.co.stex.domain.model.strategy.value.VBrandGroupName;
import jp.co.stex.domain.model.strategy.value.VGid;
import jp.co.stex.domain.model.strategy.value.VTargetBrandCodes;
import jp.co.stex.domain.service.strategy.IBrandGroupService;
import jp.co.stex.web.controller.ControllerTestBase;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <p>戦略を操作するコントローラのテストクラスです。</p>
 *
 * @author t.nemoto.x
 */
@WebMvcTest(controllers = {BrandGroupController.class})
class BrandGroupControllerTest extends ControllerTestBase {

    @MockBean
    private IBrandGroupService brandGroupService;

    @BeforeAll
    void setup() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private final VUid uid = new VUid("00000000-0000-0000-0000-000000000001");

    private final VGid gid = new VGid("00000000-0000-0000-0000-000000000001");

    private final VGid newGid = new VGid("00000000-0000-0000-0000-000000000004");

    private final BrandGroups brandGroups = new BrandGroups(
        BrandGroup.builder()
            .gid(gid)
            .brandGroupName(new VBrandGroupName("銘柄グループ1"))
            .targetBrandCodes(new VTargetBrandCodes(new VCode(1111), new VCode(2222)))
            .build(),
        BrandGroup.builder()
            .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000002")))
            .brandGroupName(new VBrandGroupName("銘柄グループ2"))
            .targetBrandCodes(new VTargetBrandCodes(new VCode(3333), new VCode(4444)))
            .build()
    );

    private final BrandGroup newBrandGroup = BrandGroup.builder()
        .brandGroupName(new VBrandGroupName("銘柄グループ1"))
        .targetBrandCodes(new VTargetBrandCodes(new VCode(1111), new VCode(2222)))
        .build();

    private final BrandGroup updateBrandGroup = BrandGroup.builder()
        .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
        .brandGroupName(new VBrandGroupName("銘柄グループ111"))
        .targetBrandCodes(new VTargetBrandCodes(new VCode(2222), new VCode(4444)))
        .build();

    /**
     * {@link BrandGroupController#fetch}
     */
    @Nested
    class fetch {

        private final String expected = "[" +
            "{" +
            "  \"gid\" : \"00000000-0000-0000-0000-000000000001\"," +
            "  \"brandGroupName\" : \"銘柄グループ1\"," +
            "  \"targetBrandCodes\" : [1111, 2222]" +
            "}," +
            "{" +
            "  \"gid\" : \"00000000-0000-0000-0000-000000000002\"," +
            "  \"brandGroupName\" : \"銘柄グループ2\"," +
            "  \"targetBrandCodes\" : [3333, 4444]" +
            "}" +
            "]";

        @Test
        @DisplayName("銘柄グループ一覧が期待されたJSONで返却される")
        @WithMockUser
        void _001() throws Exception {
            when(userService.findUserId()).thenReturn(uid);
            when(brandGroupService.findAll(any())).thenReturn(brandGroups);
            MvcResult result = mockMvc
                .perform(get("/api/brand-group").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andReturn();
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link BrandGroupController#create}
     */
    @Nested
    class create {

        @Captor
        private ArgumentCaptor<BrandGroup> captor;

        private final String postData = "{" +
            "  \"brandGroupName\" : \"銘柄グループ1\"," +
            "  \"targetBrandCodes\" : [1111, 2222]" +
            "}";

        @BeforeEach
        void setUp() {
            reset(brandGroupService);
        }

        @Test
        @DisplayName("正しい引数が与えられたとき、銘柄グループを作成する")
        @WithMockUser
        void _001() throws Exception {
            when(userService.findUserId()).thenReturn(uid);
            when(brandGroupService.createOne(any(), captor.capture())).thenReturn(newGid);
            MvcResult result = mockMvc.perform(
                post("/api/brand-group")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isCreated())
                .andReturn();
            verify(brandGroupService, times(1)).createOne(any(), any());

            assertEquals(newBrandGroup, captor.getValue());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("未入力のフィールドがチェックエラーになることを確認する")
        @WithMockUser
        void _002() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/brand-group")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*", hasSize(2)))
                    .andExpect(jsonPath("$.['NotNull.brandGroupName'].message").value("銘柄グループ名が未入力です。"))
                    .andExpect(jsonPath("$.['NotNull.targetBrandCodes'].message").value("銘柄グループが正しく設定されていません。"))
                    .andReturn();

            verify(brandGroupService, never()).createOne(any(), any());
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link BrandGroupController#update}
     */
    @Nested
    class update {

        @Captor
        private ArgumentCaptor<BrandGroup> captor;

        private final String postData = "{" +
            "  \"brandGroupName\" : \"銘柄グループ111\"," +
            "  \"targetBrandCodes\" : [2222, 4444]" +
            "}";

        @BeforeEach
        void setUp() {
            reset(brandGroupService);
        }

        @Test
        @DisplayName("正しい引数が与えられたとき、銘柄グループを更新する")
        @WithMockUser
        void _001() throws Exception {
            when(userService.findUserId()).thenReturn(uid);
            doNothing().when(brandGroupService).updateOne(any(), captor.capture());
            MvcResult result = mockMvc.perform(
                put("/api/brand-group/00000000-0000-0000-0000-0001")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isNoContent())
                .andReturn();
            verify(brandGroupService, times(1)).updateOne(any(), any());

            assertEquals(updateBrandGroup, captor.getValue());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("パスで受け取る銘柄グループIDがnullで入力チェックエラー")
        @WithMockUser
        void _002() throws Exception {
            MvcResult result = mockMvc.perform(
                put("/api/brand-group/")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['NotNull.BrandGroupController.update.gid'].message").value("銘柄グループIDが未指定です。"))
                .andReturn();
            verify(brandGroupService, never()).updateOne(any(), any());
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link BrandGroupController#delete}
     */
    @Nested
    class delete {

        @BeforeEach
        void setUp() {
            reset(brandGroupService);
        }

        @Test
        @DisplayName("正しい引数が与えられたとき、銘柄グループを削除する")
        @WithMockUser
        void _001() throws Exception {
            doNothing().when(brandGroupService).deleteOne(any(), any());
            MvcResult result = mockMvc
                .perform(delete("/api/brand-group/00000000-0000-0000-0000-0001").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent())
                .andReturn();
            verify(brandGroupService, times(1)).deleteOne(any(), any());

            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("パスで受け取る銘柄グループIDがnullで入力チェックエラー")
        @WithMockUser
        void _002() throws Exception {
            MvcResult result = mockMvc
                .perform(delete("/api/brand-group/").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['NotNull.BrandGroupController.delete.gid'].message").value("銘柄グループIDが未指定です。"))
                .andReturn();
            verify(brandGroupService, never()).deleteOne(any(), any());
            LOG.info(result.getResponse().getContentAsString());
        }
    }
}
