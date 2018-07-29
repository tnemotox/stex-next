package jp.co.stex.domain.model.strategy.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>比較種別を表す列挙型です。</p>
 *
 * @author t.nemoto.x
 */
@Getter
@AllArgsConstructor
public enum ComparisonType {

    MORE(1, "より大きい"),

    MORE_THAN(2, "以上"),

    LESS(3, "より小さい"),

    LESS_THAN(4, "以下"),

    EQUALS(5, "と等しい");

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
    public static ComparisonType findById(int id) {
        for(ComparisonType value: ComparisonType.values()) {
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
    public static ComparisonType findByLabel(String label) {
        for(ComparisonType value: ComparisonType.values()) {
            if (ObjectUtils.equals(value.getLabel(), label)) {
                return value;
            }
        }
        return null;
    }
}
