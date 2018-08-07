package jp.co.stex.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.springboot.autoconfigure.DozerAutoConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
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
@SpringBootTest(classes = {
    JacksonAutoConfiguration.class,
    DozerAutoConfiguration.class
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ControllerTestBase {

    /**
     * ロガー
     */
    protected final Logger LOG = LogManager.getLogger(getClass().getName());

    /**
     * Jacksonのマッパー
     */
    @Autowired
    protected ObjectMapper mapper;

    /**
     * MockMVCオブジェクト
     */
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
}
