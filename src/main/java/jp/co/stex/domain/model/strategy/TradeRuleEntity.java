package jp.co.stex.domain.model.strategy;

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
    private boolean todayOrTomorrow;

    /**
     * 売買フラグ
     */
    private boolean buyOrSell;

    /**
     * 取引タイミング種別
     */
    private int tradeTimingType;

    /**
     * 指値
     */
    private double limitOrderValue;

    /**
     * 仕掛けフラグ
     */
    private boolean inOrExit;

    /**
     * 順序
     */
    private int orderBy;

    /**
     * 取引戦略パレットリスト
     */
    private List<TradeStrategyPaletteEntity> palettes;
}
