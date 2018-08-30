package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * <p>取引戦略カードのラベル名を表す値オブジェクトです。</p>
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VCardLabel {

    /**
     * コンストラクタ
     *
     * @param cardLabel カードラベル
     */
    @JsonCreator
    public VCardLabel(String cardLabel) {
        this.cardLabel = cardLabel;
    }

    /**
     * カードラベル
     */
    @JsonValue
    private String cardLabel;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
