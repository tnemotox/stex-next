package jp.co.stex.domain.model.strategy.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>取引種別を表す列挙型です。</p>
 *
 * @author t.nemoto.x
 */
@Getter
@AllArgsConstructor
public enum TradeType {

    ENTRY(true, "仕掛け"),

    EXIT(false, "手仕舞い");

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
    public static TradeType findByFlag(boolean flag) {
        for(TradeType value: TradeType.values()) {
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
    public static TradeType findByLabel(String label) {
        for(TradeType value: TradeType.values()) {
            if (ObjectUtils.equals(value.getLabel(), label)) {
                return value;
            }
        }
        return null;
    }
}
