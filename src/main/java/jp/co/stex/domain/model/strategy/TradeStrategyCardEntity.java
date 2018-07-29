package jp.co.stex.domain.model.strategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>TODO 概要を記述してください</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private int cardType;

    /**
     * 左辺指標種別
     */
    private int leftSideIndicatorType;

    /**
     * 左辺日数
     */
    private int leftSideDays;

    /**
     * 右辺指標種別
     */
    private int rightSideIndicatorType;

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
    private int comparisonType;
}
