package jp.co.stex.web.controller.api.strategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>取引戦略を格納するフォームです。</p>
 *
 * @author t.nemoto.x
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}
