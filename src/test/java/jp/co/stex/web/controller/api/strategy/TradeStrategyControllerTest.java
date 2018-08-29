package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.value.VMemo;
import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.TradeStrategies;
import jp.co.stex.domain.model.strategy.TradeStrategy;
import jp.co.stex.domain.model.strategy.value.*;
import jp.co.stex.domain.service.strategy.ITradeStrategyService;
import jp.co.stex.web.controller.ControllerTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <p>取引戦略を操作するコントローラのテストクラスです。</p>
 *
 * @author t.nemoto.x
 */
@WebMvcTest(controllers = {TradeStrategyController.class})
class TradeStrategyControllerTest extends ControllerTestBase {

    @MockBean
    private ITradeStrategyService tradeStrategyService;

    /**
     * {@link TradeStrategyController#fetch}
     */
    @Nested
    class fetch {

        private final TradeStrategies tradeStrategies = new TradeStrategies(
            Arrays.asList(
                TradeStrategy.builder()
                    .sid(new VSid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                    .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                    .tradeStrategyName(new VTradeStrategyName("戦略1"))
                    .termOfAnalysis(new VTermOfAnalysis(
                        Arrays.asList(
                            LocalDate.of(2017, 1, 1),
                            LocalDate.of(2017, 12, 31)
                        )
                    ))
                    .memo(new VMemo("コメント1"))
                    .analyzedAt(new VAnalyzedAt(LocalDate.of(2018, 1, 1)))
                    .build(),
                TradeStrategy.builder()
                    .sid(new VSid(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                    .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                    .tradeStrategyName(new VTradeStrategyName("戦略2"))
                    .termOfAnalysis(new VTermOfAnalysis(
                        Arrays.asList(
                            LocalDate.of(2017, 1, 1),
                            LocalDate.of(2017, 12, 31)
                        )
                    ))
                    .memo(new VMemo("コメント2"))
                    .analyzedAt(new VAnalyzedAt(LocalDate.of(2018, 1, 1)))
                    .build()
            )
        );

        private final String expected = "[" +
            "{" +
            "  \"sid\" : \"00000000-0000-0000-0000-000000000001\"," +
            "  \"gid\" : \"00000000-0000-0000-0000-000000000001\"," +
            "  \"tradeStrategyName\" : \"戦略1\"," +
            "  \"termOfAnalysis\" : [\"2017-01-01\", \"2017-12-31\"]," +
            "  \"analyzedAt\" : \"2018-01-01\"," +
            "  \"memo\" : \"コメント1\"" +
            "}," +
            "{" +
            "  \"sid\" : \"00000000-0000-0000-0000-000000000002\"," +
            "  \"gid\" : \"00000000-0000-0000-0000-000000000001\"," +
            "  \"tradeStrategyName\" : \"戦略2\"," +
            "  \"termOfAnalysis\" : [\"2017-01-01\", \"2017-12-31\"]," +
            "  \"analyzedAt\" : \"2018-01-01\"," +
            "  \"memo\" : \"コメント2\"" +
            "}" +
            "]";

        @Test
        @DisplayName("取引戦略リストが期待されたJSONで返却される")
        @WithMockUser
        void _001() throws Exception {
            when(tradeStrategyService.findAll(any())).thenReturn(tradeStrategies);
            MvcResult result = mockMvc
                .perform(get("/api/trade-strategy").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andReturn();
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link TradeStrategyController#create}
     */
    @Nested
    class create {

        @Captor
        private ArgumentCaptor<TradeStrategy> captor;

        private final String postData = "{" +
            "  \"gid\" : \"00000000-0000-0000-0000-000000000001\"," +
            "  \"tradeStrategyName\" : \"戦略1\"," +
            "  \"termOfAnalysis\" : [\"2017-01-01\", \"2017-12-31\"]," +
            "  \"memo\" : \"コメント1\"" +
            "}";

        private final String postData_labelBlank = "{" +
            "  \"gid\" : \"00000000-0000-0000-0000-000000000001\"," +
            "  \"tradeStrategyName\" : \"\"," +
            "  \"termOfAnalysis\" : [\"2017-01-01\", \"2017-12-31\"]," +
            "  \"memo\" : \"コメント1\"" +
            "}";

        @BeforeEach
        void setUp() {
            reset(tradeStrategyService);
        }

        @Test
        @DisplayName("正しい引数が与えられたとき、取引戦略を作成する")
        @WithMockUser
        void _001() throws Exception {
            when(userService.findUserId()).thenReturn(new VUid(UUID.randomUUID()));
            when(tradeStrategyService.createOne(any(), captor.capture())).thenReturn(new VSid(UUID.fromString("00000000-0000-0000-0000-000000000001")));
            MvcResult result = mockMvc.perform(
                post("/api/trade-strategy")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
                .andExpect(status().isCreated())
                .andReturn();
            verify(tradeStrategyService, times(1)).createOne(any(), any());

            assertEquals(
                TradeStrategy.builder()
                    .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                    .tradeStrategyName(new VTradeStrategyName("戦略1"))
                    .termOfAnalysis(new VTermOfAnalysis(
                        Arrays.asList(
                            LocalDate.of(2017, 1, 1),
                            LocalDate.of(2017, 12, 31)
                        )
                    ))
                    .memo(new VMemo("コメント1"))
                    .build(),
                captor.getValue()
            );
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("未入力のフィールドがチェックエラーになることを確認する")
        @WithMockUser
        void _002() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/trade-strategy")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.['NotNull.gid'].message").value("分析銘柄グループが未入力です。"))
                .andExpect(jsonPath("$.['NotNull.tradeStrategyName'].message").value("取引戦略名が未入力です。"))
                .andExpect(jsonPath("$.['NotNull.termOfAnalysis'].message").value("分析期間が未入力です。"))
                .andReturn();

            verify(tradeStrategyService, never()).createOne(any(), any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("ラベルが空文字のときチェックエラーになることを確認する")
        @WithMockUser
        void _003() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/trade-strategy")
                        .content(postData_labelBlank)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['NotBlank.tradeStrategyName'].message").value("取引戦略名が未入力です。"))
                .andReturn();

            verify(tradeStrategyService, never()).createOne(any(), any());
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link TradeStrategyController#update}
     */
    @Nested
    class update {

        @Captor
        private ArgumentCaptor<TradeStrategy> captor;

        private final String putData = "{" +
            "  \"sid\" : \"00000000-0000-0000-0000-000000000001\"," +
            "  \"gid\" : \"00000000-0000-0000-0000-000000000001\"," +
            "  \"tradeStrategyName\" : \"戦略1\"," +
            "  \"termOfAnalysis\" : [\"2017-01-01\", \"2017-12-31\"]," +
            "  \"memo\" : \"コメント1\"" +
            "}";

        private final String putData_labelBlank = "{" +
            "  \"sid\" : \"00000000-0000-0000-0000-000000000001\"," +
            "  \"gid\" : \"00000000-0000-0000-0000-000000000001\"," +
            "  \"tradeStrategyName\" : \"\"," +
            "  \"termOfAnalysis\" : [\"2017-01-01\", \"2017-12-31\"]," +
            "  \"memo\" : \"コメント1\"" +
            "}";

        @BeforeEach
        void setUp() {
            reset(tradeStrategyService);
        }

        @Test
        @DisplayName("正しい引数が与えられたとき、取引戦略を更新する")
        @WithMockUser
        void _001() throws Exception {
            when(userService.findUserId()).thenReturn(new VUid(UUID.randomUUID()));
            doNothing().when(tradeStrategyService).updateOne(any(), captor.capture());
            MvcResult result = mockMvc.perform(
                put("/api/trade-strategy/00000000-0000-0000-0000-000000000001")
                    .content(putData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
                .andExpect(status().isNoContent())
                .andReturn();
            verify(tradeStrategyService, times(1)).updateOne(any(), any());

            assertEquals(
                TradeStrategy.builder()
                    .sid(new VSid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                    .gid(new VGid(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                    .tradeStrategyName(new VTradeStrategyName("戦略1"))
                    .termOfAnalysis(new VTermOfAnalysis(
                        Arrays.asList(
                            LocalDate.of(2017, 1, 1),
                            LocalDate.of(2017, 12, 31)
                        )
                    ))
                    .memo(new VMemo("コメント1"))
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
                    put("/api/trade-strategy/00000000-0000-0000-0000-000000000001")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.['NotNull.gid'].message").value("分析銘柄グループが未入力です。"))
                .andExpect(jsonPath("$.['NotNull.tradeStrategyName'].message").value("取引戦略名が未入力です。"))
                .andExpect(jsonPath("$.['NotNull.termOfAnalysis'].message").value("分析期間が未入力です。"))
                .andReturn();

            verify(tradeStrategyService, never()).updateOne(any(), any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("ラベルが空文字のときチェックエラーになることを確認する")
        @WithMockUser
        void _003() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    put("/api/trade-strategy/00000000-0000-0000-0000-000000000001")
                        .content(putData_labelBlank)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['NotBlank.tradeStrategyName'].message").value("取引戦略名が未入力です。"))
                .andReturn();

            verify(tradeStrategyService, never()).updateOne(any(), any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("パスで受け取る取引戦略IDが未入力のときチェックエラーになることを確認する")
        @WithMockUser
        void _004() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    put("/api/trade-strategy/")
                        .content(putData)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andReturn();

            verify(tradeStrategyService, never()).updateOne(any(), any());
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link TradeStrategyController#delete}
     */
    @Nested
    class delete {

        @BeforeEach
        void setUp() {
            reset(tradeStrategyService);
        }

        @Test
        @DisplayName("正しい引数が与えられたとき、取引戦略を削除する")
        @WithMockUser
        void _001() throws Exception {
            when(userService.findUserId()).thenReturn(new VUid(UUID.randomUUID()));
            doNothing().when(tradeStrategyService).deleteOne(any(), any());
            MvcResult result = mockMvc.perform(
                delete("/api/trade-strategy/00000000-0000-0000-0000-000000000001")
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
                .andExpect(status().isNoContent())
                .andReturn();
            verify(tradeStrategyService, times(1)).deleteOne(any(), any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("パスで受け取る取引戦略IDが未入力のときチェックエラーになることを確認する")
        @WithMockUser
        void _002() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    delete("/api/trade-strategy/")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andReturn();
            verify(tradeStrategyService, never()).updateOne(any(), any());
            LOG.info(result.getResponse().getContentAsString());
        }
    }
}
