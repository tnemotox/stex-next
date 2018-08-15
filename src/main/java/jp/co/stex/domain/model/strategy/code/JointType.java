package jp.co.stex.domain.model.strategy.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>結合種別を表す列挙型です。</p>
 *
 * @author t.nemoto.x
 */
@Getter
@AllArgsConstructor
public enum JointType {

    AND(1, "または"),

    OR(2, "かつ");

    /**
     * ID
     */
    @JsonValue
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
    @JsonCreator
    public static JointType findById(int id) {
        for(JointType value: JointType.values()) {
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
    public static JointType findByLabel(String label) {
        for(JointType value: JointType.values()) {
            if (ObjectUtils.equals(value.getLabel(), label)) {
                return value;
            }
        }
        return null;
    }
}
