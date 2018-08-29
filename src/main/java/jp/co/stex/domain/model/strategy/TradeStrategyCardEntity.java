package jp.co.stex.domain.model.strategy;

import jp.co.stex.base.exception.StexSystemException;
import jp.co.stex.domain.model.strategy.code.CardType;
import jp.co.stex.domain.model.strategy.code.ComparisonType;
import jp.co.stex.domain.model.strategy.code.CrossType;
import jp.co.stex.domain.model.strategy.code.IndicatorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>取引戦略カードを格納するエンティティです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain=true)
public class TradeStrategyCardEntity implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 2635887942024944051L;

    /**
     * 取引戦略カードID
     */
    private int cid;

    /**
     * ユーザID
     */
    private int uid;

    /**
     * 取引戦略ID
     */
    private int sid;

    /**
     * ラベル
     */
    private String label;

    /**
     * 利用フラグ
     */
    private boolean used;

    /**
     * カード種別
     */
    private CardType cardType;

    /**
     * 左辺指標種別
     */
    private IndicatorType leftSideIndicatorType;

    /**
     * 左辺日数
     */
    private int leftSideDays;

    /**
     * 右辺指標種別
     */
    private IndicatorType rightSideIndicatorType;

    /**
     * 右辺日数
     */
    private int rightSideDays;

    /**
     * 右辺種別フラグ
     */
    private boolean rightSideFixOrFlex;

    /**
     * 右辺固定値
     */
    private int rightSideFixValue;

    /**
     * 係数
     */
    private double coefficient;

    /**
     * 比較種別
     */
    private ComparisonType comparisonType;

    /**
     * 交差種別
     */
    private CrossType crossType;

    /**
     * 経過日数
     */
    private int elapsedDay;

    /**
     * <p>取引戦略カードのラベルを組み立てる。</p>
     */
    public void makeLabel() {
        StringBuilder sb = new StringBuilder();
        switch (cardType) {
            // "比較"の場合
            case COMPARE:
            case CROSS:
                // 左辺の日付がある場合、"X日間の"を追加する
                if (IndicatorType.resolveDaysUsed(leftSideIndicatorType)) {
                    sb.append(leftSideDays).append("日間の");
                }
                // 左辺の指標値のラベル、"移動平均線が"、などを追加する
                sb.append(leftSideIndicatorType.getLabel()).append("が");
                // 右辺が固定値の場合、左辺と同様に"Y日間の〜〜が"を追加する
                if (rightSideFixOrFlex) {
                    sb.append(rightSideFixValue);
                }
                // 右辺が指標値の場合は、その値を追加する
                else {
                    if (IndicatorType.resolveDaysUsed(rightSideIndicatorType)) {
                        sb.append(rightSideDays).append("日間の");
                    }
                    sb.append(rightSideIndicatorType.getLabel());
                    // 係数が存在する場合、" x 係数"を追加する
                    if (coefficient != 0d) {
                        sb.append(" × ").append(coefficient);
                    }
                }

                // "比較"の場合は比較種別を追加する
                if (CardType.COMPARE.equals(cardType)) {
                    sb.append(comparisonType.getLabel());
                }
                // "交差"の場合は交差種別を追加する
                else {
                    sb.append("を").append(crossType.getLabel());
                }
                break;
            case TIME:
                sb.append(elapsedDay).append("日経過");
                break;
            default:
                throw new StexSystemException();
        }
        label = sb.toString();
    }
}
