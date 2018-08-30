package jp.co.stex.domain.model.strategy;

import jp.co.stex.domain.model.strategy.code.CardType;
import jp.co.stex.domain.model.strategy.code.UsedType;
import jp.co.stex.domain.model.strategy.value.VCardLabel;
import jp.co.stex.domain.model.strategy.value.VCid;
import jp.co.stex.domain.model.strategy.value.VSid;
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
@Builder
@NoArgsConstructor
@Accessors(chain=true)
public abstract class TradeStrategyCard implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 2635887942024944051L;

    /**
     * カード種別
     */
    protected CardType cardType;

    /**
     * 取引戦略カードID
     */
    protected VCid cid;

    /**
     * 取引戦略ID
     */
    protected VSid sid;

    /**
     * 利用状況
     */
    protected UsedType used;

    /**
     * カードラベル
     */
    protected VCardLabel cardLabel;

    /**
     * 取引戦略カードのラベルを組み立てる。
     */
    abstract void makeCardLabel();
}
