package jp.co.stex.web.controller.api.strategy;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>分析対象銘柄を格納するフォームです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
public class AnalysisBrandGroupForm implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 6689332271383810629L;

    /**
     * 分析対象銘柄グループ名
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
     * 分析対象銘柄（JSON）
     */
    @NotNull
    private List<Integer> brands;
}
