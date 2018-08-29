package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * <p>取引戦略名を表す値オブジェクトです。</p>
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VTradeStrategyName {

    /**
     * コンストラクタ
     *
     * @param tradeStrategyName 取引戦略名
     */
    @JsonCreator
    public VTradeStrategyName(String tradeStrategyName) {
        this.tradeStrategyName = tradeStrategyName;
    }

    /**
     * 取引戦略名
     */
    @JsonValue
    @NotBlank
    private String tradeStrategyName;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
