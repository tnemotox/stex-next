package jp.co.stex.domain.model.base.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Pattern;

/**
 * 銘柄コードを表す値オブジェクトです。
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VCode {

    /**
     * コンストラクタ
     *
     * @param code ユーザID
     */
    @JsonCreator
    public VCode(@JsonProperty("code") int code) {
        this.code = code;
    }

    /**
     * ユーザID
     */
    @JsonValue
    @Pattern(regexp = "^998407$|^\\d{4}$")
    private int code;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
