package jp.co.stex.web.controller.api.strategy;

import com.fasterxml.jackson.databind.SerializationFeature;
import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
import jp.co.stex.domain.service.strategy.StrategyService;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <p>戦略を操作するコントローラのテストクラスです。</p>
 *
 * @author t.nemoto.x
 */
@WebMvcTest(controllers = {AnalysisBrandGroupController.class})
class AnalysisBrandGroupControllerTest extends ControllerTestBase {

    @MockBean
    private StrategyService strategyService;

    @BeforeAll
    void setup() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * {@link AnalysisBrandGroupController#fetch}
     */
    @Nested
    class fetch {

        private final List<AnalysisBrandGroupEntity> groups = Arrays.asList(
            AnalysisBrandGroupEntity.builder()
                .uid(1)
                .gid(1)
                .label("分析対象銘柄1")
                .orderBy(1)
                .brands(Arrays.asList(1111, 2222))
                .build(),
            AnalysisBrandGroupEntity.builder()
                .uid(1)
                .gid(2)
                .label("分析対象銘柄2")
                .orderBy(2)
                .brands(Arrays.asList(3333, 4444))
                .build()
        );

        private final String expected = "[" +
            "{" +
            "  \"uid\" : 1," +
            "  \"gid\" : 1," +
            "  \"label\" : \"分析対象銘柄1\"," +
            "  \"orderBy\" : 1," +
            "  \"brands\" : [1111, 2222]" +
            "}," +
            "{" +
            "  \"uid\" : 1," +
            "  \"gid\" : 2," +
            "  \"label\" : \"分析対象銘柄2\"," +
            "  \"orderBy\" : 2," +
            "  \"brands\" : [1111, 2222]" +
            "}" +
            "]";

        @Test
        @DisplayName("分析対象銘柄リストが期待されたJSONで返却される")
        @WithMockUser
        void _001() throws Exception {
            when(strategyService.findAllAnalysisBrandGroup(anyInt())).thenReturn(groups);
            MvcResult result = mockMvc
                .perform(get("/api/analysis-brand-group").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andReturn();
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link AnalysisBrandGroupController#create}
     */
    @Nested
    class create {

        @Captor
        private ArgumentCaptor<AnalysisBrandGroupEntity> captor;

        private final String postData = "{" +
            "  \"label\" : \"分析対象銘柄1\"," +
            "  \"orderBy\" : 1," +
            "  \"brands\" : [1111, 2222]" +
            "}";

        @BeforeEach
        void setUp() {
            reset(strategyService);
        }

        @Test
        @DisplayName("正しい引数が与えられたとき、取引戦略を作成する")
        @WithMockUser
        void _001() throws Exception {
            when(userService.findUserId(anyString())).thenReturn(1);
            when(strategyService.createOneAnalysisBrandGroup(captor.capture())).thenReturn(1);
            MvcResult result = mockMvc.perform(
                post("/api/analysis-brand-group")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isCreated())
                .andReturn();
            verify(strategyService, times(1)).createOneAnalysisBrandGroup(any());

            assertEquals(
                AnalysisBrandGroupEntity.builder()
                    .uid(1)
                    .label("分析対象銘柄1")
                    .orderBy(1)
                    .brands(Arrays.asList(1111, 2222))
                    .build(),
                captor.getValue());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("未入力のフィールドがチェックエラーになることを確認する")
        @WithMockUser
        void _002() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/analysis-brand-group")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*", hasSize(3)))
                    .andExpect(jsonPath("$.['NotBlank.analysisBrandGroupForm.label'].message").value("分析対象銘柄グループが未入力です。"))
                    .andExpect(jsonPath("$.['NotBlank.analysisBrandGroupForm.brands'].message").value("分析対象銘柄グループが正しく設定されていません。"))
                    .andExpect(jsonPath("$.['NotBlank.analysisBrandGroupForm.orderBy'].message").value("分析対象銘柄グループが正しく設定されていません。"))
                    .andReturn();

            verify(strategyService, never()).createOneAnalysisBrandGroup(any());
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link AnalysisBrandGroupController#update}
     */
    @Nested
    class update {

        @Captor
        private ArgumentCaptor<AnalysisBrandGroupEntity> captor;

        private final String postData = "{" +
            "  \"label\" : \"分析対象銘柄1\"," +
            "  \"orderBy\" : 1," +
            "  \"brands\" : [1111, 2222]" +
            "}";

        @BeforeEach
        void setUp() {
            reset(strategyService);
        }

        @Test
        @DisplayName("正しい引数が与えられたとき、取引戦略を更新する")
        @WithMockUser
        void _001() throws Exception {
            when(userService.findUserId(anyString())).thenReturn(1);
            doNothing().when(strategyService).updateOneAnalysisBrandGroup(captor.capture());
            MvcResult result = mockMvc.perform(
                put("/api/analysis-brand-group/1")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isNoContent())
                .andReturn();
            verify(strategyService, times(1)).updateOneAnalysisBrandGroup(any());

            assertEquals(
                AnalysisBrandGroupEntity.builder()
                    .uid(1)
                    .gid(1)
                    .label("分析対象銘柄1")
                    .orderBy(1)
                    .brands(Arrays.asList(1111, 2222))
                    .build(),
                captor.getValue());

            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("パスで受け取る取引戦略IDがnullで入力チェックエラー")
        @WithMockUser
        void _002() throws Exception {
            MvcResult result = mockMvc.perform(
                put("/api/analysis-brand-group/")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['NotNull.AnalysisBrandGroupController.update.gid'].message").value("分析対象銘柄IDが未指定です。"))
                .andReturn();
            verify(strategyService, never()).updateOneAnalysisBrandGroup(any());
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link AnalysisBrandGroupController#delete}
     */
    @Nested
    class delete {

        @BeforeEach
        void setUp() {
            reset(strategyService);
        }

        @Test
        @DisplayName("正しい引数が与えられたとき、取引戦略を削除する")
        @WithMockUser
        void _001() throws Exception {
            doNothing().when(strategyService).deleteOneAnalysisBrandGroup(anyInt(), anyInt());
            MvcResult result = mockMvc
                .perform(delete("/api/analysis-brand-group/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent())
                .andReturn();
            verify(strategyService, times(1)).deleteOneAnalysisBrandGroup(anyInt(), anyInt());

            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("パスで受け取る取引戦略IDがnullで入力チェックエラー")
        @WithMockUser
        void _002() throws Exception {
            MvcResult result = mockMvc
                .perform(delete("/api/analysis-brand-group/").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['NotNull.AnalysisBrandGroupController.delete.gid'].message").value("分析対象銘柄IDが未指定です。"))
                .andReturn();
            verify(strategyService, never()).deleteOneAnalysisBrandGroup(anyInt(), anyInt());
            LOG.info(result.getResponse().getContentAsString());
        }
    }
}
