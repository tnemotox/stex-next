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
 * 取引戦略カードIDを表す値オブジェクトです。
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VCid {

    /**
     * コンストラクタ
     *
     * @param cid 取引戦略カードID
     */
    @JsonCreator
    public VCid(@JsonProperty("cid") int cid) {
        this.cid = cid;
    }

    /**
     * 取引戦略カードID
     */
    @JsonValue
    private int cid;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
