package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.*;

import java.util.UUID;

/**
 * 分析銘柄グループIDを表す値オブジェクトです。
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VGid {

    /**
     * コンストラクタ
     *
     * @param gid 分析銘柄グループID
     */
    @JsonCreator
    public VGid(String gid) {
        this.gid = UUID.fromString(gid);
    }

    /**
     * 分析銘柄グループID
     */
    @JsonValue
    private UUID gid;

    /**
     * 銘柄グループIDを生成する。
     *
     * @return 取引戦略ID
     */
    public VGid generateGid() {
        return new VGid(UUID.randomUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
