package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <p>係数を表す値オブジェクトです。</p>
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VCoefficient {

    /**
     * コンストラクタ
     *
     * @param coefficient 係数
     */
    @JsonCreator
    public VCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    /**
     * 係数
     */
    @JsonValue
    private double coefficient;

    /**
     * 係数が有効か確認する。
     *
     * @return true/false
     */
    public boolean isEnable() {
        return coefficient != 0d;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
