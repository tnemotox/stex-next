package jp.co.stex.web.controller.api;

import com.fasterxml.jackson.databind.SerializationFeature;
import jp.co.stex.domain.model.strategy.TradeStrategyEntity;
import jp.co.stex.domain.service.base.MessageService;
import jp.co.stex.domain.service.base.UserService;
import jp.co.stex.domain.service.strategy.StrategyService;
import jp.co.stex.web.controller.ControllerTestBase;
import jp.co.stex.web.controller.GlobalRestExceptionHandler;
import org.dozer.Mapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <p>戦略を操作するコントローラのテストクラスです。</p>
 *
 * @author t.nemoto.x
 */
@Validated
class StrategyControllerTest extends ControllerTestBase {

    @Mock
    private StrategyService strategyService;

    @Mock
    private UserService userService;

    @Autowired
    private Mapper dozerMapper;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new StrategyController(dozerMapper, strategyService, userService))
            .setControllerAdvice(new GlobalRestExceptionHandler(new MessageService(messageSource())))
            .build();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * {@link StrategyController#fetch}
     */
    @Nested
    class fetch {

        private final List<TradeStrategyEntity> strategies = Arrays.asList(
            TradeStrategyEntity.builder()
                .sid(1)
                .gid(1)
                .label("すごい取引戦略")
                .analysisStartDate(LocalDate.of(2017, 1, 1))
                .analysisEndDate(LocalDate.of(2017, 12, 31))
                .analyzedAt(LocalDate.of(2018, 1, 1))
                .memo("とてもすごい取引戦略です")
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

        private final String expected = "[" +
            "{" +
            "  \"sid\" : 1," +
            "  \"gid\" : 1," +
            "  \"label\" : \"すごい取引戦略\"," +
            "  \"analysisStartDate\" : \"2017-01-01\"," +
            "  \"analysisEndDate\" : \"2017-12-31\"," +
            "  \"analyzedAt\" : \"2018-01-01\"," +
            "  \"memo\" : \"とてもすごい取引戦略です\"" +
            "}," +
            "{" +
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
                .perform(get("/api/strategy"))
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

        private final String postData = "{" +
            "  \"gid\" : 1," +
            "  \"label\" : \"すごい取引戦略\"," +
            "  \"analysisStartDate\" : \"2017-01-01\"," +
            "  \"analysisEndDate\" : \"2017-12-31\"," +
            "  \"analyzedAt\" : \"2018-01-01\"," +
            "  \"memo\" : \"とてもすごい取引戦略です\"" +
            "}";

        @Test
        @DisplayName("未入力のフィールドがチェックエラーになることを確認する")
        @WithMockUser
        void _001() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/strategy")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.['NotNull.tradeStrategyForm.gid'].message").value("分析銘柄グループが未入力です。"))
                .andExpect(jsonPath("$.['NotBlank.tradeStrategyForm.label'].message").value("取引戦略名が未入力です。"))
                .andExpect(jsonPath("$.['NotNull.tradeStrategyForm.analysisStartDate'].message").value("分析開始日が未入力です。"))
                .andExpect(jsonPath("$.['NotNull.tradeStrategyForm.analysisEndDate'].message").value("分析終了日が未入力です。"))
                .andReturn();
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("正しい引数が与えられたとき、取引戦略を作成する")
        @WithMockUser
        void _002() throws Exception {
            doNothing().when(strategyService).createOne(anyInt(), any());
            MvcResult result = mockMvc.perform(
                post("/api/strategy")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andReturn();
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link StrategyController#update}
     */
    @Nested
    class update {

        private final String postData = "{" +
            "  \"gid\" : 1," +
            "  \"label\" : \"すごい取引戦略\"," +
            "  \"analysisStartDate\" : \"2017-01-01\"," +
            "  \"analysisEndDate\" : \"2017-12-31\"," +
            "  \"analyzedAt\" : \"2018-01-01\"," +
            "  \"memo\" : \"とてもすごい取引戦略です\"" +
            "}";

        @Test
        @DisplayName("正しい引数が与えられたとき、取引戦略を更新する")
        @WithMockUser
        void _001() throws Exception {
            doNothing().when(strategyService).updateOne(anyInt(), anyInt(), any());
            MvcResult result = mockMvc.perform(
                put("/api/strategy/1")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent())
            .andReturn();
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("パスで受け取る取引戦略IDがnullで入力チェックエラー")
        @WithMockUser
        void _002() throws Exception {
            doNothing().when(strategyService).updateOne(anyInt(), anyInt(), any());
            MvcResult result = mockMvc.perform(
                put("/api/strategy/1")
                    .content(postData)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent())
            .andReturn();
            LOG.info(result.getResponse().getContentAsString());
        }
    }
}
