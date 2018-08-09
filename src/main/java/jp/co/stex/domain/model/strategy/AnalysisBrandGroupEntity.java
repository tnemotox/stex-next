package jp.co.stex.domain.model.strategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>分析対象銘柄を格納するエンティティです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class AnalysisBrandGroupEntity implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -3459117299010275314L;

    /**
     * ユーザID
     */
    private int uid;

    /**
     * 分析銘柄グループID
     */
    private int gid;

    /**
     * 分析対象銘柄グループ名
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
