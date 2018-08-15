package jp.co.stex.domain.model.strategy.code;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import jp.co.stex.base.exception.StexSystemException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

/**
 * <p>市場種別を表す列挙型です。</p>
 *
 * @author t.nemoto.x
 */
@Getter
@AllArgsConstructor
public enum MarketType {

    TSE_FIRST(1, "東証一部"),

    TSE_SECOND(2, "東証二部");

    /**
     * ID
     */
    private int id;

    /**
     * ラベル
     */
    @JsonValue
    private String label;

    /**
     * <p>IDに紐付く列挙型を取得する。存在しなければnullを返却する。</p>
     *
     * @param id ID
     * @return IDに紐付く列挙型
     */
    public static MarketType findById(int id) {
        for(MarketType value: MarketType.values()) {
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
    public static MarketType findByLabel(String label) {
        for(MarketType value: MarketType.values()) {
            if (ObjectUtils.equals(value.getLabel(), label)) {
                return value;
            }
        }
        return null;
    }
}
