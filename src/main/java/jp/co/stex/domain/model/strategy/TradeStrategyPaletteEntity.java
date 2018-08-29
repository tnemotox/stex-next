package jp.co.stex.domain.model.strategy;

import jp.co.stex.domain.model.strategy.code.JointType;
import jp.co.stex.domain.model.strategy.code.NestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>取引戦略パレットを格納するモデルです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeStrategyPaletteEntity implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 3146384635367712280L;

    /**
     * 取引戦略パレットID
     */
    private int pid;

    /**
     * ユーザID
     */
    private int uid;

    /**
     * 取引ルールID
     */
    private int rid;

    /**
     * 取引戦略カードID
     */
    private int cid;

    /**
     * 左結合
     */
    private JointType leftJointType;

    /**
     * 右結合
     */
    private JointType rightJointType;

    /**
     * ネスト開始
     */
    private NestType nestOpen;

    /**
     * ネスト終了
     */
    private NestType nestClose;

    /**
     * 順序
     */
    private int orderBy;

    /**
     * 取引戦略カードラベル
     */
    private String cardLabel;
}
