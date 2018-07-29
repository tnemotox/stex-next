package jp.co.stex.domain.model.strategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>TODO 概要を記述してください</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalysisBrandGroupEntity implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -3459117299010275314L;

    /**
     * 分析銘柄グループID
     */
    private int gid;

    /**
     * ラベル
     */
    private String label;

    /**
     * 順序
     */
    private int orderBy;

    /**
     * 分析対象銘柄
     */
    private List<Integer> brands;

    /**
     * 分析対象銘柄（JSON）
     */
    private String brandsOfJson;
}
