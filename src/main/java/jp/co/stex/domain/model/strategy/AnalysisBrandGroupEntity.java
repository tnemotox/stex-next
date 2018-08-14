package jp.co.stex.domain.model.strategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.stex.base.exception.StexSystemException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * <p>分析銘柄グループを格納するエンティティです。</p>
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
     * 分析銘柄グループ名
     */
    private String label;

    /**
     * 分析銘柄
     */
    private List<Integer> brands;
}
