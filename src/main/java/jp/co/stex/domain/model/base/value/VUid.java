package jp.co.stex.domain.model.base.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import java.util.UUID;

/**
 * ユーザIDを表す値オブジェクトです。
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@Builder
public class VUid {

    /**
     * コンストラクタ
     *
     * @param uid ユーザID
     */
    @JsonCreator
    public VUid(@JsonProperty("uid") String uid) {
        this.uid = UUID.fromString(uid);
    }

    /**
     * ユーザID
     */
    @JsonValue
    private UUID uid;
}
