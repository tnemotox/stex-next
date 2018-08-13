package jp.co.stex.web.controller.api.strategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>分析銘柄グループを格納するフォームです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class AnalysisBrandGroupForm implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 6689332271383810629L;

    /**
     * 分析銘柄グループ名
     */
    @NotBlank
    private String label;

    /**
     * 順序
     */
    @NotNull
    @Min(1)
    private Integer orderBy;

    /**
     * 分析銘柄グループ
     */
    @NotNull
    private List<Integer> brands;
}
