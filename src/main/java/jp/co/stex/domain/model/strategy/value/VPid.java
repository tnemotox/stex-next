package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 取引戦略パレットIDを表す値オブジェクトです。
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VPid {

    /**
     * コンストラクタ
     *
     * @param pid 取引戦略パレットID
     */
    @JsonCreator
    public VPid(@JsonProperty("pid") int pid) {
        this.pid = pid;
    }

    /**
     * 取引戦略パレットID
     */
    @JsonValue
    private int pid;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
