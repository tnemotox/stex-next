package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.code.BuyingAndSellingType;
import jp.co.stex.domain.model.strategy.code.OrderType;
import jp.co.stex.domain.model.strategy.code.TradeType;
import jp.co.stex.domain.model.strategy.code.TradingDayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>取引ルールを格納するフォームです。</p>
 *
 * @author t.nemoto.x
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class TradeRuleForm implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 7175815255547695034L;

    /**
     * 取引ルールID
     */
    @NotNull
    private Integer rid;

    /**
     * 当日取引フラグ
     */
    @NotNull
    private TradingDayType tradingDayType;

    /**
     * 売買フラグ
     */
    @NotNull
    private BuyingAndSellingType buyingAndSellingType;

    /**
     * 取引タイミング種別
     */
    @NotNull
    private OrderType orderType;

    /**
     * 指値
     */
    private double limitOrderValue;

    /**
     * 取引種別
     */
    private TradeType tradeType;

    /**
     * 順序
     */
    @NotNull
    private Integer orderBy;

    /**
     * 取引戦略パレットリスト
     */
    @Valid
    private List<TradeStrategyPaletteForm> palettes;
}
