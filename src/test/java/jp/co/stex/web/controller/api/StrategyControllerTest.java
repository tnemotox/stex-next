package jp.co.stex.web.controller.api;

import com.fasterxml.jackson.databind.SerializationFeature;
import jp.co.stex.domain.model.strategy.TradeStrategyEntity;
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

import java.time.LocalDate;
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
@WebMvcTest(controllers = {StrategyController.class})
class StrategyControllerTest extends ControllerTestBase {

    @MockBean
    private StrategyService strategyService;

    @BeforeAll
    void setup() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * {@link StrategyController#fetch}
     */
    @Nested
    class fetch {

        private final List<TradeStrategyEntity> strategies = Arrays.asList(
            TradeStrategyEntity.builder()
                .uid(1)
                .sid(1)
                .gid(1)
                .label("すごい取引戦略")
                .analysisStartDate(LocalDate.of(2017, 1, 1))
                .analysisEndDate(LocalDate.of(2017, 12, 31))
                .analyzedAt(LocalDate.of(2018, 1, 1))
                .memo("とてもすごい取引戦略です")
                .build(),
            TradeStrategyEntity.builder()
                .uid(1)
                .sid(2)
                .gid(1)
                .label("label2")
                .analysisStartDate(LocalDate.of(2017, 1, 1))
                .analysisEndDate(LocalDate.of(2017, 12, 31))
                .analyzedAt(LocalDate.of(2018, 4, 1))
                .memo("memo2")
                .build()
        );

        private final String expected = "[" +
            "{" +
            "  \"uid\" : 1," +
            "  \"sid\" : 1," +
            "  \"gid\" : 1," +
            "  \"label\" : \"すごい取引戦略\"," +
            "  \"analysisStartDate\" : \"2017-01-01\"," +
            "  \"analysisEndDate\" : \"2017-12-31\"," +
            "  \"analyzedAt\" : \"2018-01-01\"," +
            "  \"memo\" : \"とてもすごい取引戦略です\"" +
            "}," +
            "{" +
            "  \"uid\" : 1," +
            "  \"sid\" : 2," +
            "  \"gid\" : 1," +
            "  \"label\" : \"label2\"," +
            "  \"analysisStartDate\" : \"2017-01-01\"," +
            "  \"analysisEndDate\" : \"2017-12-31\"," +
            "  \"analyzedAt\" : \"2018-04-01\"," +
            "  \"memo\" : \"memo2\"" +
            "  }" +
            "]";

        @Test
        @DisplayName("取引戦略リストが期待されたJSONで返却される")
        @WithMockUser
        void _001() throws Exception {
            when(strategyService.findAll(anyInt())).thenReturn(strategies);
            MvcResult result = mockMvc
                .perform(get("/api/strategy").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andReturn();
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link StrategyController#create}
     */
    @Nested
    class create {

        @Captor
        private ArgumentCaptor<TradeStrategyEntity> captor;

        private final String postData = "{" +
            "  \"gid\" : 1," +
            "  \"label\" : \"すごい取引戦略\"," +
            "  \"analysisStartDate\" : \"2017-01-01\"," +
            "  \"analysisEndDate\" : \"2017-12-31\"," +
            "  \"analyzedAt\" : \"2018-01-01\"," +
            "  \"memo\" : \"とてもすごい取引戦略です\"" +
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
            when(strategyService.createOne(captor.capture())).thenReturn(1);
            MvcResult result = mockMvc.perform(
                post("/api/strategy")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isCreated())
                .andReturn();
            verify(strategyService, times(1)).createOne(any());

            assertEquals(
                TradeStrategyEntity.builder()
                    .uid(1)
                    .gid(1)
                    .label("すごい取引戦略")
                    .analysisStartDate(LocalDate.of(2017, 1, 1))
                    .analysisEndDate(LocalDate.of(2017, 12, 31))
                    .memo("とてもすごい取引戦略です")
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
                    post("/api/strategy")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*", hasSize(4)))
                    .andExpect(jsonPath("$.['NotNull.tradeStrategyForm.gid'].message").value("分析銘柄グループが未入力です。"))
                    .andExpect(jsonPath("$.['NotBlank.tradeStrategyForm.label'].message").value("取引戦略名が未入力です。"))
                    .andExpect(jsonPath("$.['NotNull.tradeStrategyForm.analysisStartDate'].message").value("分析開始日が未入力です。"))
                    .andExpect(jsonPath("$.['NotNull.tradeStrategyForm.analysisEndDate'].message").value("分析終了日が未入力です。"))
                    .andReturn();

            verify(strategyService, never()).createOne(any());
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link StrategyController#update}
     */
    @Nested
    class update {

        @Captor
        private ArgumentCaptor<TradeStrategyEntity> captor;

        private final String postData = "{" +
            "  \"gid\" : 1," +
            "  \"label\" : \"すごい取引戦略\"," +
            "  \"analysisStartDate\" : \"2017-01-01\"," +
            "  \"analysisEndDate\" : \"2017-12-31\"," +
            "  \"analyzedAt\" : \"2018-01-01\"," +
            "  \"memo\" : \"とてもすごい取引戦略です\"" +
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
            doNothing().when(strategyService).updateOne(captor.capture());
            MvcResult result = mockMvc.perform(
                put("/api/strategy/1")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isNoContent())
                .andReturn();
            verify(strategyService, times(1)).updateOne(any());

            assertEquals(
                TradeStrategyEntity.builder()
                    .uid(1)
                    .sid(1)
                    .gid(1)
                    .label("すごい取引戦略")
                    .analysisStartDate(LocalDate.of(2017, 1, 1))
                    .analysisEndDate(LocalDate.of(2017, 12, 31))
                    .memo("とてもすごい取引戦略です")
                    .build(),
                captor.getValue());

            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("パスで受け取る取引戦略IDがnullで入力チェックエラー")
        @WithMockUser
        void _002() throws Exception {
            MvcResult result = mockMvc.perform(
                put("/api/strategy/")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['NotNull.StrategyController.update.sid'].message").value("取引戦略IDが未指定です。"))
                .andReturn();
            verify(strategyService, never()).updateOne(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("パスで受け取る取引戦略IDが型違いでタイプミスマッチ")
        @WithMockUser
        void _003() throws Exception {
            MvcResult result = mockMvc.perform(
                put("/api/strategy/aaa")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['w.stex.sy.0001'].message").value("不正な値が送信されました。"))
                .andReturn();
            verify(strategyService, never()).updateOne(any());
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link StrategyController#delete}
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
            doNothing().when(strategyService).deleteOne(anyInt(), anyInt());
            MvcResult result = mockMvc
                .perform(delete("/api/strategy/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent())
                .andReturn();
            verify(strategyService, times(1)).deleteOne(anyInt(), anyInt());

            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("パスで受け取る取引戦略IDがnullで入力チェックエラー")
        @WithMockUser
        void _002() throws Exception {
            MvcResult result = mockMvc
                .perform(delete("/api/strategy/").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['NotNull.StrategyController.delete.sid'].message").value("取引戦略IDが未指定です。"))
                .andReturn();
            verify(strategyService, never()).deleteOne(anyInt(), anyInt());
            LOG.info(result.getResponse().getContentAsString());
        }
    }
}
