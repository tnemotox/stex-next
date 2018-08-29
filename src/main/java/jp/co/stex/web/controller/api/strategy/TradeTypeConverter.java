package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.code.TradeType;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * <p>RestControllerにて取引種別を受け取るときに変換するコンバーターです。</p>
 *
 * @author t.nemoto.x
 */
@Component
public class TradeTypeConverter extends PropertyEditorSupport {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAsText(final String text) {
        setValue(TradeType.findByFlag(BooleanUtils.toBoolean(text)));
    }
}
