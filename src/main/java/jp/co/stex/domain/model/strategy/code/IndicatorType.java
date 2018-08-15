package jp.co.stex.domain.model.strategy.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>指標種別を表す列挙型です。</p>
 *
 * @author t.nemoto.x
 */
@Getter
@AllArgsConstructor
public enum IndicatorType {

    _1_移動平均線(1, "移動平均線"),

    _2_移動平均線乖離率(2, "移動平均線乖離率"),

    _3_移動平均線の傾き(3, "移動平均線の傾き"),

    _4_ボリンジャーバンド(4, "ボリンジャーバンド"),

    _5_RSI_指数移動平均(5, "RSI（指数移動平均）"),

    _6_RSI_移動平均(6, "RSI（移動平均）"),

    _7_ボリュームレシオ_百分率(7, "ボリュームレシオ（百分率）"),

    _8_ボリュームレシオ上下幅(8, "ボリュームレシオ（上下幅）"),

    _9_EMA(9, "EMA"),

    _10_MACD(10, "MACD"),

    _11_MACD移動平均(11, "MACD移動平均"),

    _12_始値(12, "始値"),

    _13_終値(13, "終値"),

    _14_高値(14, "高値"),

    _15_安値(15, "安値"),

    _16_出来高(16, "出来高"),

    _17_前日比(17, "前日比"),

    _18_過去最高価格(18, "過去最高価格"),

    _19_過去最低価格(19, "過去最低価格"),

    _20_年初来高値(20, "年初来高値"),

    _21_年初来安値(21, "年初来安値"),

    _22_PER(22, "PER"),

    _23_PBR(23, "PBR"),

    _24_EPS(24, "EPS"),

    _25_BPS(25, "BPS");

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
    public static IndicatorType findById(int id) {
        for(IndicatorType value: IndicatorType.values()) {
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
    public static IndicatorType findByLabel(String label) {
        for(IndicatorType value: IndicatorType.values()) {
            if (ObjectUtils.equals(value.getLabel(), label)) {
                return value;
            }
        }
        return null;
    }

    /**
     * <p>指定した指標種別が日付を利用するかどうかを解決する。</p>
     *
     * @param type 指標種別
     * @return 日付を利用する指標の場合はtrue
     */
    public static boolean resolveDaysUsed(IndicatorType type) {
        switch(type.getId()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 18:
            case 19:
                return true;
            default:
                return false;
        }
    }
}
