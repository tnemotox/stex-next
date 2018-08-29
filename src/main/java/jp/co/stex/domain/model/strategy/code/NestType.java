package jp.co.stex.domain.model.strategy.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>ネスト種別を表す列挙型です。</p>
 *
 * @author t.nemoto.x
 */
@Getter
@AllArgsConstructor
public enum NestType {

    ENABLE(true, "有効"),

    DISABLE(false, "無効");

    /**
     * ID
     */
    @JsonValue
    private boolean flag;

    /**
     * ラベル
     */
    private String label;

    /**
     * <p>フラグに紐付く列挙型を取得する。存在しなければnullを返却する。</p>
     *
     * @param flag ID
     * @return IDに紐付く列挙型
     */
    @JsonCreator
    public static NestType findByFlag(boolean flag) {
        for(NestType value: NestType.values()) {
            if (value.isFlag() == flag) {
                return value;
            }
        }
        return null;
    }

    /**
     * <p>ラベルに紐付く列挙型を取得する。存在しなければnullを返却する。</p>
     *
     * @param label ラベル
     * @return ラベルに紐付く列挙型
     */
    public static NestType findByLabel(String label) {
        for(NestType value: NestType.values()) {
            if (ObjectUtils.equals(value.getLabel(), label)) {
                return value;
            }
        }
        return null;
    }
}
