package jp.co.stex.web.controller.api.strategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>取引戦略を格納するフォームです。</p>
 *
 * @author t.nemoto.x
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class TradeStrategyForm implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 5687071108530825614L;

    /**
     * 分析銘柄ID
     */
    @NotNull
    private Integer gid;

    /**
     * ラベル
     */
    @NotBlank
    private String label;

    /**
     * 分析開始日
     */
    @NotNull
    private LocalDate analysisStartDate;

    /**
     * 分析終了日
     */
    @NotNull
    private LocalDate analysisEndDate;

    /**
     * コメント
     */
    private String memo;

    /**
     * 仕掛けルール
     */
    @Valid
    private List<TradeRuleForm> entryRules;

    /**
     * 手仕舞いルール
     */
    @Valid
    private List<TradeRuleForm> exitRules;
}
