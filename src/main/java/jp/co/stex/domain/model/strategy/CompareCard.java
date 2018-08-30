package jp.co.stex.domain.model.strategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.stex.domain.model.strategy.code.*;
import jp.co.stex.domain.model.strategy.value.*;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * <p>"比較"の取引戦略カードを格納するエンティティです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain=true)
public class CompareCard extends TradeStrategyCard {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 6797501805381745801L;

    /**
     * 利用状況を更新する時にJacksonから利用するコンストラクタ。
     *
     * @param sid 取引戦略ID
     * @param cid 取引戦略カードID
     * @param used 利用状況
     */
    public CompareCard(
        @JsonProperty("sid") VSid sid,
        @JsonProperty("cid") VCid cid,
        @JsonProperty("usedType") UsedType used
    ) {
        super.sid = sid;
        super.cid = cid;
        super.used = used;
        cardType = CardType.COMPARE;
    }

    public CompareCard(
        @JsonProperty("leftSideIndicatorType") IndicatorType leftSideIndicatorType,
        @JsonProperty("rightSideIndicatorType") IndicatorType rightSideIndicatorType,
        @JsonProperty("comparisonType") ComparisonType comparisonType
    ) {
        cardType = CardType.COMPARE;
    }

    /**
     * 左辺指標種別
     */
    @NotNull
    private VLeftSideIndicator leftSideIndicator;

    /**
     * 右辺指標種別
     */
    @NotNull
    private IndicatorType rightSideIndicatorType;

    /**
     * 右辺日数
     */
    private VRightSideDays rightSideDays;

    /**
     * 右辺種別フラグ
     */
    @NotNull
    private RightSideType rightSideType;

    /**
     * 右辺固定値
     */
    private VRightFixValue rightSideFixValue;

    /**
     * 係数
     */
    private VCoefficient coefficient;

    /**
     * 比較種別
     */
    @NotNull
    private ComparisonType comparisonType;

    /**
     * {@inheritDoc}
     */
    @Override
    public void makeCardLabel() {
        StringBuilder sb = new StringBuilder();
        // 左辺の日付がある場合、"X日間の"を追加する
        if (IndicatorType.resolveDaysUsed(leftSideIndicatorType)) {
            sb.append(leftSideDays).append("日間の");
        }
        // 左辺の指標値のラベル、"移動平均線が"、などを追加する
        sb.append(leftSideIndicatorType.getLabel()).append("が");
        // 右辺が固定値の場合、左辺と同様に"Y日間の〜〜が"を追加する
        if (RightSideType.FIX.equals(rightSideType)) {
            sb.append(rightSideFixValue);
        }
        // 右辺が指標値の場合は、その値を追加する
        else {
            if (IndicatorType.resolveDaysUsed(rightSideIndicatorType)) {
                sb.append(rightSideDays).append("日間の");
            }
            sb.append(rightSideIndicatorType.getLabel());
            // 係数が存在する場合、" x 係数"を追加する
            if (Objects.nonNull(coefficient) && coefficient.isEnable()) {
                sb.append(" × ").append(coefficient);
            }
        }
        sb.append(comparisonType.getLabel());
        super.cardLabel = new VCardLabel(sb.toString());
    }
}
