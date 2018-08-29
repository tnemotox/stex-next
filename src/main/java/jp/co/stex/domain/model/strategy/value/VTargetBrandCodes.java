package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.stex.base.exception.StexSystemException;
import jp.co.stex.domain.model.base.value.VCode;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static jp.co.stex.base.MessageCode.W0001;

/**
 * <p>対象銘柄コード一覧を格納するモデルです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@EqualsAndHashCode
@Getter
@Builder
public class VTargetBrandCodes implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -5246826085664399307L;

    /**
     * コンストラクタ。
     */
    public VTargetBrandCodes(List<VCode> targetBrandCodes) {
        this.targetBrandCodes = targetBrandCodes;
    }

    @JsonCreator
    public VTargetBrandCodes(int... code) {
        this.targetBrandCodes = Arrays.stream(code).mapToObj(VCode::new).collect(toList());
    }

    /**
     * 引数で渡した銘柄コードから対象銘柄コード一覧を組み立てるコンストラクタ。
     */
    public VTargetBrandCodes(VCode... code) {
        this.targetBrandCodes = Arrays.asList(code);
    }

    /**
     * 対象銘柄コード一覧
     */
    @JsonValue
    @Valid
    private List<@Pattern(regexp = "^998407$|^\\d{4}$") VCode> targetBrandCodes;

    /**
     * <p>対象銘柄コード一覧から指定した添字の取引戦略を取得する。</p>
     *
     * @param i 添字
     * @return 取引戦略
     */
    public VCode get(int i) {
        return targetBrandCodes.get(i);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }

    /**
     * 対象銘柄コード一覧をJSON文字列にシリアライズする
     */
    public static String serializeToJson(VTargetBrandCodes targetBrandCodes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(targetBrandCodes);
        } catch (JsonProcessingException e) {
            throw new StexSystemException(W0001);
        }
    }

    /**
     * JSON文字列の対象銘柄コード一覧を対象銘柄コード一覧デシリアライズする
     */
    public static VTargetBrandCodes deserializeFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<Integer> codes;
        try {
            codes = mapper.readValue(json, new TypeReference<List<Integer>>(){});
        } catch (IOException e) {
            throw new StexSystemException(W0001);
        }
        return new VTargetBrandCodes(codes.stream().map(VCode::new).collect(toList()));
    }
}
