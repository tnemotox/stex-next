package jp.co.stex.domain.model.strategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>取引戦略を格納するモデルです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeStrategyEntity implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -4766420741788947937L;

    /**
     * 取引戦略ID
     */
    private int sid;

    /**
     * ユーザID
     */
    private int uid;

    /**
     * 分析銘柄ID
     */
    private int gid;

    /**
     * ラベル
     */
    private String label;

    /**
     * 分析開始日
     */
    private LocalDate analysisStartDate;

    /**
     * 分析終了日
     */
    private LocalDate analysisEndDate;

    /**
     * 最終分析日
     */
    private LocalDate analyzedAt;

    /**
     * コメント
     */
    private String memo;

}
