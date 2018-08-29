package jp.co.stex.domain.model.strategy.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>売買フラグを表す列挙型です。</p>
 *
 * @author t.nemoto.x
 */
@Getter
@AllArgsConstructor
public enum BuyingAndSellingType {

    BUYING(true, "購入"),

    SELLING(false, "売却");

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
    public static BuyingAndSellingType findByFlag(boolean flag) {
        for(BuyingAndSellingType value: BuyingAndSellingType.values()) {
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
    public static BuyingAndSellingType findByLabel(String label) {
        for(BuyingAndSellingType value: BuyingAndSellingType.values()) {
            if (ObjectUtils.equals(value.getLabel(), label)) {
                return value;
            }
        }
        return null;
    }
}
