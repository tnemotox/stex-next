package jp.co.stex.domain.model.strategy.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>取引タイミングを表す列挙型です。</p>
 *
 * @author t.nemoto.x
 */
@Getter
@AllArgsConstructor
public enum TradeTimingType {

    OPENING_ORDER(1, "寄成"),

    ENDING_ORDER(2, "引成"),

    MARKET_ORDER(3, "成行"),

    LIMIT_ORDER(4, "指値");

    /**
     * ID
     */
    private int id;

    /**
     * ラベル
     */
    private String label;

    /**
     * <p>IDに紐付く列挙型を取得する。存在しなければnullを返却する。</p>
     *
     * @param id ID
     * @return IDに紐付く列挙型
     */
    public static TradeTimingType findById(int id) {
        for(TradeTimingType value: TradeTimingType.values()) {
            if (value.getId() == id) {
                return value;
            }
        }
        return null;
    }

    /**
     * <p>ラベルに紐付く列挙型を取得する。存在しなければnullを返却する。</p>
     *
     * @param label ラベル
     * @return IDに紐付く列挙型
     */
    public static TradeTimingType findByLabel(String label) {
        for(TradeTimingType value: TradeTimingType.values()) {
            if (ObjectUtils.equals(value.getLabel(), label)) {
                return value;
            }
        }
        return null;
    }
}
