package jp.co.stex.domain.model.strategy;

import jp.co.stex.domain.model.strategy.code.BuyingAndSellingType;
import jp.co.stex.domain.model.strategy.code.OrderType;
import jp.co.stex.domain.model.strategy.code.TradeType;
import jp.co.stex.domain.model.strategy.code.TradingDayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>取引ルールを格納するモデルです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeRuleEntity implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -2983218133014348453L;

    /**
     * 取引ルールID
     */
    private int rid;

    /**
     * ユーザID
     */
    private int uid;

    /**
     * 取引戦略ID
     */
    private int sid;

    /**
     * 当日取引フラグ
     */
    private TradingDayType tradingDayType;

    /**
     * 売買フラグ
     */
    private BuyingAndSellingType buyingAndSellingType;

    /**
     * 取引タイミング種別
     */
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
    private int orderBy;

    /**
     * 取引戦略パレットリスト
     */
    private List<TradeStrategyPaletteEntity> palettes;
}
