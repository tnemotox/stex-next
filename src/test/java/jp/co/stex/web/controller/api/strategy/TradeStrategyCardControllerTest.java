package jp.co.stex.web.controller.api.strategy;

import com.fasterxml.jackson.databind.SerializationFeature;
import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import jp.co.stex.domain.model.strategy.code.CardType;
import jp.co.stex.domain.model.strategy.code.ComparisonType;
import jp.co.stex.domain.model.strategy.code.CrossType;
import jp.co.stex.domain.model.strategy.code.IndicatorType;
import jp.co.stex.domain.service.strategy.ITradeStrategyCardService;
import jp.co.stex.web.controller.ControllerTestBase;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>取引戦略カードを操作するコントローラのテストクラスです。</p>
 *
 * @author t.nemoto.x
 */
@WebMvcTest(controllers = {TradeStrategyCardController.class})
class TradeStrategyCardControllerTest extends ControllerTestBase {

    @MockBean
    private ITradeStrategyCardService tradeStrategyCardService;

    @SpyBean
    private TradeStrategyCardFormValidator validator;

    @BeforeAll
    void setup() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * カード種別が比較の正常な入力
     */
    private final String postCompareCard = "{" +
        "  \"cardType\" : 1," +
        "  \"sid\" : 1," +
        "  \"leftSideIndicatorType\" : 1," +
        "  \"leftSideDays\" : 1," +
        "  \"rightSideFixOrFlex\" : true," +
        "  \"rightSideFixValue\" : 1," +
        "  \"comparisonType\" : 1" +
        "}";

    /**
     * カード種別が交差の正常な入力
     */
    private final String postCrossCard = "{" +
        "  \"cardType\" : 2," +
        "  \"sid\" : 1," +
        "  \"leftSideIndicatorType\" : 1," +
        "  \"leftSideDays\" : 1," +
        "  \"rightSideFixOrFlex\" : true," +
        "  \"rightSideFixValue\" : 1," +
        "  \"crossType\" : 1" +
        "}";

    /**
     * カード種別が日数の正常な入力
     */
    private final String postTimeCard = "{" +
        "  \"cardType\" : 3," +
        "  \"sid\" : 1," +
        "  \"elapsedDay\" : 1" +
        "}";

    /**
     * カード種別が比較、かつその他が未入力
     */
    private final String postCard_CompareAndOtherNullCorrelationError = "{" +
        "  \"cardType\" : 1," +
        "  \"sid\" : 1" +
        "}";

    /**
     * 右辺種別が固定値かつ固定値が未入力
     */
    private final String postCard_rightSideFixCorrelationError = "{" +
        "  \"cardType\" : 1," +
        "  \"sid\" : 1," +
        "  \"leftSideIndicatorType\" : 1," +
        "  \"leftSideDays\" : 1," +
        "  \"rightSideFixOrFlex\" : true," +
        "  \"comparisonType\" : 1" +
        "}";

    /**
     * 右辺種別が指標値かつ指標値が未入力
     */
    private final String postCard_rightSideFlexCorrelationError = "{" +
        "  \"cardType\" : 1," +
        "  \"sid\" : 1," +
        "  \"leftSideIndicatorType\" : 1," +
        "  \"leftSideDays\" : 1," +
        "  \"rightSideFixOrFlex\" : false," +
        "  \"comparisonType\" : 1" +
        "}";

    /**
     * 右辺種別が指標値、かつ右辺指標値が日付利用、かつ右辺日付が未入力
     */
    private final String postCard_rightSideDaysIndicatorCorrelationError = "{" +
        "  \"cardType\" : 1," +
        "  \"sid\" : 1," +
        "  \"leftSideIndicatorType\" : 1," +
        "  \"leftSideDays\" : 1," +
        "  \"rightSideFixOrFlex\" : false," +
        "  \"rightSideIndicatorType\" : 1," +
        "  \"comparisonType\" : 1" +
        "}";

    /**
     * 左辺指標値が日付利用、かつ左辺日付が未入力
     */
    private final String postCard_leftSideDaysIndicatorCorrelationError = "{" +
        "  \"cardType\" : 1," +
        "  \"sid\" : 1," +
        "  \"leftSideIndicatorType\" : 1," +
        "  \"rightSideFixOrFlex\" : true," +
        "  \"rightSideFixValue\" : 1," +
        "  \"comparisonType\" : 1" +
        "}";

    /**
     * 比較種別が未入力
     */
    private final String postCard_comparisonTypeCorrelationError = "{" +
        "  \"cardType\" : 1," +
        "  \"sid\" : 1," +
        "  \"leftSideIndicatorType\" : 1," +
        "  \"leftSideDays\" : 1," +
        "  \"rightSideFixOrFlex\" : true," +
        "  \"rightSideFixValue\" : 1" +
        "}";

    /**
     * 交差種別が未入力
     */
    private final String postCard_crossTypeCorrelationError = "{" +
        "  \"cardType\" : 2," +
        "  \"sid\" : 1," +
        "  \"leftSideIndicatorType\" : 1," +
        "  \"leftSideDays\" : 1," +
        "  \"rightSideFixOrFlex\" : true," +
        "  \"rightSideFixValue\" : 1" +
        "}";

    /**
     * 経過日数が未入力
     */
    private final String postCard_elapsedDayCorrelationError = "{" +
        "  \"sid\" : 1," +
        "  \"cardType\" : 3" +
        "}";

    /**
     * {@link TradeStrategyCardController#create}
     */
    @Nested
    class create {

        @Captor
        private ArgumentCaptor<TradeStrategyCardEntity> captor;

        @BeforeEach
        void setUp() {
            reset(tradeStrategyCardService);
        }

        @Test
        @DisplayName("最小限の正しい引数が与えられたとき、比較の取引戦略を作成する")
        @WithMockUser
        void _001() throws Exception {
            when(userService.findUserId()).thenReturn(new VUid(UUID.randomUUID()));
            when(tradeStrategyCardService.createOneTradeStrategyCard(captor.capture())).thenReturn(1);
            MvcResult result = mockMvc.perform(
                post("/api/trade-strategy-card")
                    .content(postCompareCard)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isCreated())
                .andReturn();
            verify(tradeStrategyCardService, times(1)).createOneTradeStrategyCard(any());

            assertEquals(
                TradeStrategyCardEntity.builder()
                    .uid(1)
                    .sid(1)
                    .cardType(CardType.COMPARE)
                    .leftSideIndicatorType(IndicatorType._1_移動平均線)
                    .leftSideDays(1)
                    .rightSideFixOrFlex(true)
                    .rightSideFixValue(1)
                    .comparisonType(ComparisonType.MORE)
                    .build(),
                captor.getValue());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("最小限の正しい引数が与えられたとき、交差の取引戦略を作成する")
        @WithMockUser
        void _002() throws Exception {
            when(userService.findUserId()).thenReturn(new VUid(UUID.randomUUID()));
            when(tradeStrategyCardService.createOneTradeStrategyCard(captor.capture())).thenReturn(1);
            MvcResult result = mockMvc.perform(
                post("/api/trade-strategy-card")
                    .content(postCrossCard)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
                .andExpect(status().isCreated())
                .andReturn();
            verify(tradeStrategyCardService, times(1)).createOneTradeStrategyCard(any());

            assertEquals(
                TradeStrategyCardEntity.builder()
                    .uid(1)
                    .sid(1)
                    .cardType(CardType.CROSS)
                    .leftSideIndicatorType(IndicatorType._1_移動平均線)
                    .leftSideDays(1)
                    .rightSideFixOrFlex(true)
                    .rightSideFixValue(1)
                    .crossType(CrossType.GO_UP)
                    .build(),
                captor.getValue());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("最小限の正しい引数が与えられたとき、時間の取引戦略カードを作成する")
        @WithMockUser
        void _003() throws Exception {
            when(userService.findUserId()).thenReturn(new VUid(UUID.randomUUID()));
            when(tradeStrategyCardService.createOneTradeStrategyCard(captor.capture())).thenReturn(1);
            MvcResult result = mockMvc.perform(
                post("/api/trade-strategy-card")
                    .content(postTimeCard)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
                .andExpect(status().isCreated())
                .andReturn();
            verify(tradeStrategyCardService, times(1)).createOneTradeStrategyCard(any());

            assertEquals(
                TradeStrategyCardEntity.builder()
                    .uid(1)
                    .sid(1)
                    .cardType(CardType.TIME)
                    .elapsedDay(1)
                    .build(),
                captor.getValue());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("未入力のフィールドがチェックエラーになることを確認する")
        @WithMockUser
        void _004() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/trade-strategy-card")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.['NotNull.tradeStrategyCardForm.cardType'].message").value("カード種別が正しく入力されていません。"))
                .andExpect(jsonPath("$.['NotNull.tradeStrategyCardForm.sid'].message").value("取引戦略IDが正しく入力されていません。"))
                .andReturn();

            verify(tradeStrategyCardService, never()).createOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("カード種別が比較、かつその他が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _005() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/trade-strategy-card")
                        .content(postCard_CompareAndOtherNullCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.RightSideFixOrFlexNull'].message").value("右辺種別が未入力です。"))
                .andExpect(jsonPath("$.['TradeStrategyCardController.ComparisonTypeNull'].message").value("比較種別が未入力です。"))
                .andExpect(jsonPath("$.['TradeStrategyCardController.LeftSideIndicatorNull'].message").value("左辺指標値が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).createOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("右辺種別が固定値、かつ右辺の値が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _006() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/trade-strategy-card")
                        .content(postCard_rightSideFixCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.RightSideTypeFixAndValueNull'].message").value("右辺種別が固定値ですが、右辺の値が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).createOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("右辺種別が指標値、かつ右辺の指標値が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _007() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/trade-strategy-card")
                        .content(postCard_rightSideFlexCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.RightSideTypeFlexAndIndicatorNull'].message").value("右辺種別が指標値ですが、右辺の指標値が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).createOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("左辺指標値が日付を利用するもの、かつ日付が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _008() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/trade-strategy-card")
                        .content(postCard_leftSideDaysIndicatorCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.LeftSideIndicatorDaysNull'].message").value("左辺は日付を利用する指標値ですが、日付が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).createOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("右辺指標値が日付を利用するもの、かつ日付が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _009() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/trade-strategy-card")
                        .content(postCard_rightSideDaysIndicatorCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.RightSideIndicatorDaysNull'].message").value("右辺は日付を利用する指標値ですが、日付が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).createOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("カード種別が比較、かつ比較種別が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _010() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/trade-strategy-card")
                        .content(postCard_comparisonTypeCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.ComparisonTypeNull'].message").value("比較種別が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).createOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("カード種別が交差、かつ交差種別が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _011() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/trade-strategy-card")
                        .content(postCard_crossTypeCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.CrossTypeNull'].message").value("交差種別が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).createOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("カード種別が時間、かつ経過日数が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _012() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    post("/api/trade-strategy-card")
                        .content(postCard_elapsedDayCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.ElapsedDayNull'].message").value("経過日数が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).createOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link TradeStrategyCardController#update}
     */
    @Nested
    class update {

        @Captor
        private ArgumentCaptor<TradeStrategyCardEntity> captor;

        @BeforeEach
        void setUp() {
            reset(tradeStrategyCardService);
        }

        @Test
        @DisplayName("最小限の正しい引数が与えられたとき、取引戦略カードを更新する")
        @WithMockUser
        void _001() throws Exception {
            when(userService.findUserId()).thenReturn(new VUid(UUID.randomUUID()));
            doNothing().when(tradeStrategyCardService).updateOneTradeStrategyCard(captor.capture());
            MvcResult result = mockMvc.perform(
                put("/api/trade-strategy-card/1")
                    .content(postCompareCard)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isNoContent())
                .andReturn();
            verify(tradeStrategyCardService, times(1)).updateOneTradeStrategyCard(any());

            assertEquals(
                TradeStrategyCardEntity.builder()
                    .uid(1)
                    .cid(1)
                    .sid(1)
                    .cardType(CardType.COMPARE)
                    .leftSideIndicatorType(IndicatorType._1_移動平均線)
                    .leftSideDays(1)
                    .rightSideFixOrFlex(true)
                    .rightSideFixValue(1)
                    .comparisonType(ComparisonType.MORE)
                    .build(),
                captor.getValue());

            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("最小限の正しい引数が与えられたとき、交差の取引戦略を更新する")
        @WithMockUser
        void _002() throws Exception {
            when(userService.findUserId()).thenReturn(new VUid(UUID.randomUUID()));
            doNothing().when(tradeStrategyCardService).updateOneTradeStrategyCard(captor.capture());
            MvcResult result = mockMvc.perform(
                put("/api/trade-strategy-card/1")
                    .content(postCrossCard)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
                .andExpect(status().isNoContent())
                .andReturn();
            verify(tradeStrategyCardService, times(1)).updateOneTradeStrategyCard(any());

            assertEquals(
                TradeStrategyCardEntity.builder()
                    .uid(1)
                    .cid(1)
                    .sid(1)
                    .cardType(CardType.CROSS)
                    .leftSideIndicatorType(IndicatorType._1_移動平均線)
                    .leftSideDays(1)
                    .rightSideFixOrFlex(true)
                    .rightSideFixValue(1)
                    .crossType(CrossType.GO_UP)
                    .build(),
                captor.getValue());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("最小限の正しい引数が与えられたとき、時間の取引戦略カードを更新する")
        @WithMockUser
        void _003() throws Exception {
            when(userService.findUserId()).thenReturn(new VUid(UUID.randomUUID()));
            doNothing().when(tradeStrategyCardService).updateOneTradeStrategyCard(captor.capture());
            MvcResult result = mockMvc.perform(
                put("/api/trade-strategy-card/1")
                    .content(postTimeCard)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
                .andExpect(status().isNoContent())
                .andReturn();
            verify(tradeStrategyCardService, times(1)).updateOneTradeStrategyCard(any());

            assertEquals(
                TradeStrategyCardEntity.builder()
                    .uid(1)
                    .cid(1)
                    .sid(1)
                    .cardType(CardType.TIME)
                    .elapsedDay(1)
                    .build(),
                captor.getValue());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("未入力のフィールドがチェックエラーになることを確認する")
        @WithMockUser
        void _004() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    put("/api/trade-strategy-card/1")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.['NotNull.tradeStrategyCardForm.cardType'].message").value("カード種別が正しく入力されていません。"))
                .andExpect(jsonPath("$.['NotNull.tradeStrategyCardForm.sid'].message").value("取引戦略IDが正しく入力されていません。"))
                .andReturn();

            verify(tradeStrategyCardService, never()).updateOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("カード種別が比較、かつその他が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _005() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    put("/api/trade-strategy-card/1")
                        .content(postCard_CompareAndOtherNullCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.RightSideFixOrFlexNull'].message").value("右辺種別が未入力です。"))
                .andExpect(jsonPath("$.['TradeStrategyCardController.ComparisonTypeNull'].message").value("比較種別が未入力です。"))
                .andExpect(jsonPath("$.['TradeStrategyCardController.LeftSideIndicatorNull'].message").value("左辺指標値が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).updateOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("右辺種別が固定値、かつ右辺の値が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _006() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    put("/api/trade-strategy-card/1")
                        .content(postCard_rightSideFixCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.RightSideTypeFixAndValueNull'].message").value("右辺種別が固定値ですが、右辺の値が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).updateOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("右辺種別が指標値、かつ右辺の指標値が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _007() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    put("/api/trade-strategy-card/1")
                        .content(postCard_rightSideFlexCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.RightSideTypeFlexAndIndicatorNull'].message").value("右辺種別が指標値ですが、右辺の指標値が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).updateOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("左辺指標値が日付を利用するもの、かつ日付が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _008() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    put("/api/trade-strategy-card/1")
                        .content(postCard_leftSideDaysIndicatorCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.LeftSideIndicatorDaysNull'].message").value("左辺は日付を利用する指標値ですが、日付が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).updateOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("右辺指標値が日付を利用するもの、かつ日付が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _009() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    put("/api/trade-strategy-card/1")
                        .content(postCard_rightSideDaysIndicatorCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.RightSideIndicatorDaysNull'].message").value("右辺は日付を利用する指標値ですが、日付が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).updateOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("カード種別が比較、かつ比較種別が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _010() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    put("/api/trade-strategy-card/1")
                        .content(postCard_comparisonTypeCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.ComparisonTypeNull'].message").value("比較種別が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).updateOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("カード種別が交差、かつ交差種別が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _011() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    put("/api/trade-strategy-card/1")
                        .content(postCard_crossTypeCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.CrossTypeNull'].message").value("交差種別が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).updateOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("カード種別が時間、かつ経過日数が未入力のとき、相関チェックエラーとなる")
        @WithMockUser
        void _012() throws Exception {
            MvcResult result = mockMvc
                .perform(
                    put("/api/trade-strategy-card/1")
                        .content(postCard_elapsedDayCorrelationError)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.['TradeStrategyCardController.ElapsedDayNull'].message").value("経過日数が未入力です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).updateOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("パスで受け取る取引戦略カードIDがnullで入力チェックエラー")
        @WithMockUser
        void _013() throws Exception {
            MvcResult result = mockMvc.perform(
                put("/api/trade-strategy-card/")
                    .content(postCompareCard)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['NotNull.TradeStrategyCardController.update.cid'].message").value("取引戦略カードIDが未指定です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).updateOneTradeStrategyCard(any());
            LOG.info(result.getResponse().getContentAsString());
        }
    }

    /**
     * {@link TradeStrategyCardController#delete}
     */
    @Nested
    class delete {

        @BeforeEach
        void setUp() {
            reset(tradeStrategyCardService);
        }

        @Test
        @DisplayName("正しい引数が与えられたとき、取引戦略カードを削除する")
        @WithMockUser
        void _001() throws Exception {
            doNothing().when(tradeStrategyCardService).deleteOneTradeStrategyCard(anyInt(), anyInt());
            MvcResult result = mockMvc
                .perform(delete("/api/trade-strategy-card/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent())
                .andReturn();
            verify(tradeStrategyCardService, times(1)).deleteOneTradeStrategyCard(anyInt(), anyInt());
            LOG.info(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("パスで受け取る取引戦略カードIDがnullで入力チェックエラー")
        @WithMockUser
        void _002() throws Exception {
            MvcResult result = mockMvc
                .perform(delete("/api/trade-strategy-card/").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['NotNull.TradeStrategyCardController.delete.cid'].message").value("取引戦略カードIDが未指定です。"))
                .andReturn();
            verify(tradeStrategyCardService, never()).deleteOneTradeStrategyCard(anyInt(), anyInt());
            LOG.info(result.getResponse().getContentAsString());
        }
    }
}
