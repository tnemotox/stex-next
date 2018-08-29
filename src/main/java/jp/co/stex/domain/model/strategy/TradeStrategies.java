package jp.co.stex.domain.model.strategy;

import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>取引戦略一覧を表すモデルです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@EqualsAndHashCode
@Getter
@Builder
public class TradeStrategies implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -1011626326230298754L;

    /**
     * コンストラクタ。
     */
    public TradeStrategies(List<TradeStrategy> tradeStrategies) {
        this.tradeStrategies = tradeStrategies;
    }

    /**
     * 引数で渡した取引戦略から取引戦略一覧を組み立てるコンストラクタ。
     */
    public TradeStrategies(TradeStrategy... tradeStrategies) {
        this.tradeStrategies = Arrays.asList(tradeStrategies);
    }

    /**
     * 取引戦略一覧
     */
    @JsonValue
    private List<TradeStrategy> tradeStrategies;

    /**
     * <p>取引戦略一覧から指定した添字の取引戦略を取得する。</p>
     *
     * @param i 添字
     * @return 取引戦略
     */
    public TradeStrategy get(int i) {
        return tradeStrategies.get(i);
    }

    /**
     * 空の取引戦略一覧を生成する。
     *
     * @return 取引戦略一覧
     */
    public static TradeStrategies generateEmptyTradeStrategies() {
        return new TradeStrategies(Collections.emptyList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
