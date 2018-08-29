package jp.co.stex.domain.model.strategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.web.controller.api.strategy.TradeRuleForm;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

import java.io.Serializable;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * <p>取引ルールを格納するモデルです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Data
public class TradeRules implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -7196828975847812036L;

    /**
     * 取引ルールリスト
     */
    private List<TradeRuleEntity> tradeRules;

    /**
     * <p>コンストラクタ。取引ルールフォームから値を詰め替える。</p>
     *
     * @param rules 取引ルールフォームリスト
     */
    public TradeRules(List<TradeRuleEntity> rules) {
        Mapper mapper = DozerBeanMapperBuilder.create().build();
        tradeRules = rules.stream().map(r -> mapper.map(r, TradeRuleEntity.class)).collect(toList());
    }

    public List<TradeRuleEntity> makeWillCreatedRules() {
        return tradeRules.stream()
            .filter(r -> r.getRid() <= 0)
            .sorted((r1, r2) -> r1.getOrderBy() < r2.getOrderBy() ? 1 : -1)
            .collect(toList());
    }

    public List<TradeRuleEntity> makeWillUpdatedRules() {
        return tradeRules.stream()
            .filter(r -> r.getRid() > 0)
            .sorted((r1, r2) -> r1.getOrderBy() < r2.getOrderBy() ? 1 : -1)
            .collect(toList());
    }

    public List<Integer> extractRids() {
        return tradeRules.stream().map(TradeRuleEntity::getRid).collect(toList());
    }
}
