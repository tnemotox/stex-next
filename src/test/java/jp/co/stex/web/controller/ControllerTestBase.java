package jp.co.stex.web.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.dozermapper.springboot.autoconfigure.DozerAutoConfiguration;
import jp.co.stex.domain.service.base.MessageService;
import jp.co.stex.domain.service.base.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.Mapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * <p>コントローラをテストするための基底クラスです。</p>
 * <p>全てのコントローラのテストクラスで継承します。</p>
 *
 * @author t.nemoto.x
 */
@ExtendWith(SpringExtension.class)
@Import({DozerAutoConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ComponentScan("jp.co.stex.domain.service.base")
public abstract class ControllerTestBase {

    /**
     * ロガー
     */
    protected final Logger LOG = LogManager.getLogger(getClass().getName());

    /**
     * Jacksonのマッパー
     * {@link WebMvcTest}で自動的に読み込まれる
     */
    @Autowired
    protected ObjectMapper mapper;

    /**
     * SpringSecurityの認証に利用するサービス
     * 各サービス呼び出しで必ず利用するため、基底クラスでモック化する
     */
    @MockBean
    protected UserService userService;

    /**
     * GlobalExceptionHandlerでメッセージ解決のために利用するサービス
     * 利用するために"jp.co.stex.domain.service.base"をComponentScan対象とする
     * 入力チェックの試験で必ず利用するため、基底クラスでモック化する
     * 試験では、実際に利用するものをDIするようSpyBeanを利用する
     */
    @SpyBean
    protected MessageService messageService;

    /**
     * {@link DozerAutoConfiguration}をImportして利用するDozerマッパー
     * 各コントローラでフォームからマッピングする時に利用するため、基盤クラスでモック化する
     * 試験では、実際に利用するものをDIするようSpyBeanを利用する
     */
    @SpyBean
    protected Mapper dozerMapper;

    /**
     * コントローラを試験するためのモックオブジェクト
     * {@link WebMvcTest}で自動的に読み込まれる
     */
    @Autowired
    protected MockMvc mockMvc;

    /**
     * メッセージソースパス
     */
    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

    /**
     * <p>メッセージソースを取得する。</p>
     *
     * @return メッセージソース
     */
    protected MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @BeforeAll
    void setup() {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(SerializationFeature.INDENT_OUTPUT);
    }
}
