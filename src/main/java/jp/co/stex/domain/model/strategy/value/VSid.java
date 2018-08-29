package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.*;

import java.util.UUID;

/**
 * 取引戦略IDを表す値オブジェクトです。
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VSid {

    /**
     * コンストラクタ
     *
     * @param sid 取引戦略ID
     */
    @JsonCreator
    public VSid(String sid) {
        this.sid = UUID.fromString(sid);
    }

    /**
     * 取引戦略ID
     */
    @JsonValue
    private UUID sid;

    /**
     * 取引戦略IDを生成する。
     *
     * @return 取引戦略ID
     */
    public VSid generateSid() {
        return new VSid(UUID.randomUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
