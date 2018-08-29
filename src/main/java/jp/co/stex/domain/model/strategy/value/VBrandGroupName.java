package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * <p>銘柄グループ名を表す値オブジェクトです。</p>
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VBrandGroupName {

    /**
     * コンストラクタ
     *
     * @param brandGroupName 銘柄グループ名
     */
    @JsonCreator
    public VBrandGroupName(String brandGroupName) {
        this.brandGroupName = brandGroupName;
    }

    /**
     * 取引戦略名
     */
    @JsonValue
    @NotBlank
    private String brandGroupName;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
