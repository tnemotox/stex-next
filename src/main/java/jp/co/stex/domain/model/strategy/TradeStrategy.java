package jp.co.stex.domain.model.strategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.stex.domain.model.strategy.value.VMemo;
import jp.co.stex.domain.model.strategy.value.*;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.ibatis.annotations.Param;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>取引戦略を表すモデルです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@EqualsAndHashCode
@Builder
@Getter
public class TradeStrategy implements Serializable {

    /**
     * MyBatisから利用するコンストラクタ。
     */
    public TradeStrategy(
        @Param("sid") VSid sid,
        @Param("gid") VGid gid,
        @Param("tradeStrategyName") VTradeStrategyName tradeStrategyName,
        @Param("termOfAnalysis") VTermOfAnalysis termOfAnalysis,
        @Param("analyzedAt") VAnalyzedAt analyzedAt,
        @Param("memo") VMemo memo
    ) {
        this.sid = sid;
        this.gid = gid;
        this.tradeStrategyName = tradeStrategyName;
        this.termOfAnalysis = termOfAnalysis;
        this.analyzedAt = analyzedAt;
        this.memo = memo;
    }

    /**
     * Jacksonから利用するコンストラクタ。
     */
    @JsonCreator
    public TradeStrategy(
        @JsonProperty("sid") VSid sid,
        @JsonProperty("gid") VGid gid,
        @JsonProperty("tradeStrategyName") VTradeStrategyName tradeStrategyName,
        @JsonProperty("termOfAnalysis") VTermOfAnalysis termOfAnalysis,
        @JsonProperty("memo") VMemo memo
    ) {
        this.sid = sid;
        this.gid = gid;
        this.tradeStrategyName = tradeStrategyName;
        this.termOfAnalysis = termOfAnalysis;
        this.memo = memo;
    }

    /**
     * 取引戦略IDを明示的に設定するコンストラクタ。
     *
     * @param tradeStrategy 取引戦略
     * @param sid 取引戦略ID
     */
    public TradeStrategy(
        TradeStrategy tradeStrategy,
        VSid sid
    ) {
        this.sid = sid;
        this.gid = tradeStrategy.gid;
        this.tradeStrategyName = tradeStrategy.tradeStrategyName;
        this.termOfAnalysis = tradeStrategy.termOfAnalysis;
        this.analyzedAt = tradeStrategy.analyzedAt;
        this.memo = tradeStrategy.memo;
    }

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -5824654551348884157L;

    /**
     * 取引戦略ID
     */
    private VSid sid;

    /**
     * 分析銘柄ID
     */
    @NotNull
    private VGid gid;

    /**
     * ラベル
     */
    @Valid
    @NotNull
    private VTradeStrategyName tradeStrategyName;

    /**
     * 分析日
     */
    @NotNull
    private VTermOfAnalysis termOfAnalysis;

    /**
     * 最終分析日
     */
    private VAnalyzedAt analyzedAt;

    /**
     * コメント
     */
    private VMemo memo;

    /**
     * <p>取引戦略IDを生成する。</p>
     *
     * @return 取引戦略
     */
    public TradeStrategy generateSid() {
        return new TradeStrategy(
            this,
            new VSid().generateSid()
        );
    }

    /**
     * <p>最終分析日を生成する。</p>
     *
     * @return 取引戦略
     */
    public TradeStrategy generateAnalyzedAt() {
        return new TradeStrategy(
            this.sid,
            this.gid,
            this.tradeStrategyName,
            this.termOfAnalysis,
            analyzedAt.generateAnalyzedAt(),
            this.memo
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
