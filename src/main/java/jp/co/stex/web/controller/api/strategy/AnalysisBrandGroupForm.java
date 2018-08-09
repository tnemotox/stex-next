package jp.co.stex.web.controller.api.strategy;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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
     * ラベル
     */
    @NotBlank
    private String label;

    /**
     * 順序
     */
    @NotBlank
    @Min(1)
    private Integer orderBy;

    /**
     * 分析対象銘柄（JSON）
     */
    @NotBlank
    private String brandsOfJson;
}
