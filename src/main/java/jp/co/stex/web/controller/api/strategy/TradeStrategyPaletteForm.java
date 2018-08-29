package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.code.JointType;
import jp.co.stex.domain.model.strategy.code.NestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>取引戦略パレットを格納するフォームです。</p>
 *
 * @author t.nemoto.x
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
class TradeStrategyPaletteForm implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -1357527548056458664L;

    /**
     * 取引戦略カードID
     */
    private Integer cid;

    /**
     * 取引戦略パレットID
     */
    private Integer pid;

    /**
     * 左結合
     */
    @NotNull
    private JointType leftJointType;

    /**
     * 右結合
     */
    @NotNull
    private JointType rightJointType;

    /**
     * ネスト開始
     */
    @NotNull
    private NestType nestOpen;

    /**
     * ネスト終了
     */
    @NotNull
    private NestType nestClose;

    /**
     * 順序
     */
    @NotNull
    private Integer orderBy;
}
