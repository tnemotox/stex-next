package jp.co.stex.domain.model.strategy.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>右辺種別どうかを表す列挙型です。</p>
 *
 * @author t.nemoto.x
 */
@Getter
@AllArgsConstructor
public enum RightSideType {

    FIX(true, "固定値"),

    FLEX(false, "指標値");

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
    public static RightSideType findByFlag(boolean flag) {
        for(RightSideType value: RightSideType.values()) {
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
    public static RightSideType findByLabel(String label) {
        for(RightSideType value: RightSideType.values()) {
            if (ObjectUtils.equals(value.getLabel(), label)) {
                return value;
            }
        }
        return null;
    }
}
