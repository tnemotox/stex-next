package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.model.strategy.code.IndicatorType;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * <p>左辺日数を表す値オブジェクトです。</p>
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VLeftSideIndicator {

    /**
     * コンストラクタ
     *
     * @param leftSideDays 左辺日数
     */
    @JsonCreator
    public VLeftSideIndicator(int leftSideDays) {
        this.leftSideDays = leftSideDays;
    }

    /**
     * 左辺日数
     */
    @JsonValue
    private int leftSideDays;

    /**
     * 左辺指標種別
     */
    @NotNull
    private IndicatorType leftSideIndicatorType;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
