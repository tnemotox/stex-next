package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <p>右辺固定値を表す値オブジェクトです。</p>
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VRightFixValue {

    /**
     * コンストラクタ
     *
     * @param rightFixValue 右辺固定値
     */
    @JsonCreator
    public VRightFixValue(int rightFixValue) {
        this.rightFixValue = rightFixValue;
    }

    /**
     * 右辺固定値
     */
    @JsonValue
    private int rightFixValue;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
