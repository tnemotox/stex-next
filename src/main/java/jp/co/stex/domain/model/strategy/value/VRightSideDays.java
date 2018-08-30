package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <p>右辺日数を表す値オブジェクトです。</p>
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VRightSideDays {

    /**
     * コンストラクタ
     *
     * @param rightSideDays 右辺日数
     */
    @JsonCreator
    public VRightSideDays(int rightSideDays) {
        this.rightSideDays = rightSideDays;
    }

    /**
     * 右辺日数
     */
    @JsonValue
    private int rightSideDays;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
