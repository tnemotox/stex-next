package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * <p>取引戦略カードが使用中かどうかを表す値オブジェクトです。</p>
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VUsed {

    /**
     * コンストラクタ
     *
     * @param used 使用中かどうか
     */
    @JsonCreator
    public VUsed(boolean used) {
        this.used = used;
    }

    /**
     * 使用中かどうか
     */
    @JsonValue
    private boolean used;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
