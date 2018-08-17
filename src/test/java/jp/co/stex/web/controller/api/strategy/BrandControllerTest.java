package jp.co.stex.web.controller.api.strategy;

import com.fasterxml.jackson.databind.SerializationFeature;
import jp.co.stex.domain.model.strategy.BrandEntity;
import jp.co.stex.domain.model.strategy.code.MarketType;
import jp.co.stex.domain.service.strategy.IBrandService;
import jp.co.stex.web.controller.ControllerTestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>戦略を操作するコントローラのテストクラスです。</p>
 *
 * @author t.nemoto.x
 */
@WebMvcTest(controllers = {BrandController.class})
class BrandControllerTest extends ControllerTestBase {

    @MockBean
    private IBrandService brandService;

    @BeforeAll
    void setup() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * {@link BrandController#fetch}
     */
    @Nested
    class fetch {

        private final List<BrandEntity> brands = Arrays.asList(
            BrandEntity.builder()
                .code(1111)
                .name("銘柄1")
                .market(MarketType.TSE_FIRST)
                .detail("説明1")
                .build(),
            BrandEntity.builder()
                .code(3333)
                .name("銘柄3")
                .market(MarketType.TSE_SECOND)
                .detail("説明3")
                .build()
        );


        private final String expected = "[" +
            "{" +
            "  \"code\" : 1111," +
            "  \"name\" : \"銘柄1\"," +
            "  \"market\" : \"東証一部\"," +
            "  \"detail\" : \"説明1\"" +
            "}," +
            "{" +
            "  \"code\" : 3333," +
            "  \"name\" : \"銘柄3\"," +
            "  \"market\" : \"東証二部\"," +
            "  \"detail\" : \"説明3\"" +
            "}" +
            "]";

        @Test
        @DisplayName("銘柄リストが期待されたJSONで返却される")
        @WithMockUser
        void _001() throws Exception {
            when(brandService.findAllBrands()).thenReturn(brands);
            MvcResult result = mockMvc
                .perform(get("/api/brand").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andReturn();
            LOG.info(result.getResponse().getContentAsString());
        }
    }
}
