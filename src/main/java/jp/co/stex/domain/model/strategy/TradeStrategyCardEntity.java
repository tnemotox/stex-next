package jp.co.stex.domain.model.strategy;

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
     * 取引戦略パレットID
     */
    private int pid;

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
}
