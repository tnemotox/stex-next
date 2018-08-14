package jp.co.stex.domain.model.strategy;

import jp.co.stex.domain.model.strategy.code.MarketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>銘柄を格納するエンティティです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class BrandEntity implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -2386133789105962600L;

    /**
     * 銘柄コード
     */
    private int code;

    /**
     * 銘柄名
     */
    private String name;

    /**
     * 市場種別
     */
    private MarketType market;

    /**
     * 銘柄詳細
     */
    private String detail;
}
