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
 * 取引ルールIDを表す値オブジェクトです。
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VRid {

    /**
     * コンストラクタ
     *
     * @param rid 取引ルールID
     */
    @JsonCreator
    public VRid(@JsonProperty("rid") int rid) {
        this.rid = rid;
    }

    /**
     * 取引ルールID
     */
    @JsonValue
    private int rid;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
