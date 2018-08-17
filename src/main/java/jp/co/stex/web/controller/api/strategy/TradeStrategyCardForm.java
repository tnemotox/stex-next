package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.code.CardType;
import jp.co.stex.domain.model.strategy.code.ComparisonType;
import jp.co.stex.domain.model.strategy.code.CrossType;
import jp.co.stex.domain.model.strategy.code.IndicatorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>取引戦略カードを格納するフォームです。</p>
 *
 * @author t.nemoto.x
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class TradeStrategyCardForm implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -2211279206291574346L;

    /**
     * 取引戦略ID
     */
    @NotNull
    private Integer sid;

    /**
     * カード種別
     */
    @NotNull
    private CardType cardType;

    /**
     * 左辺指標種別
     */
    private IndicatorType leftSideIndicatorType;

    /**
     * 左辺日数
     */
    private Integer leftSideDays;

    /**
     * 右辺種別フラグ
     */
    private Boolean rightSideFixOrFlex;

    /**
     * 右辺指標種別
     */
    private IndicatorType rightSideIndicatorType;

    /**
     * 右辺日数
     */
    private Integer rightSideDays;

    /**
     * 右辺固定値
     */
    private Integer rightSideFixValue;

    /**
     * 係数
     */
    private Double coefficient;

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
    private Integer elapsedDay;
}
