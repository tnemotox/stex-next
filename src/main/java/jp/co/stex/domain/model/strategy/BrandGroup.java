package jp.co.stex.domain.model.strategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.value.VBrandGroupName;
import jp.co.stex.domain.model.strategy.value.VGid;
import jp.co.stex.domain.model.strategy.value.VTargetBrandCodes;
import jp.co.stex.domain.utility.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>分析銘柄グループを表すモデルです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@EqualsAndHashCode
@Builder
@Getter
@AllArgsConstructor
public class BrandGroup implements Serializable {

    /**
     * JacksonとMyBatisから利用するコンストラクタ。
     */
    public BrandGroup(
        @JsonProperty("gid") @Param("gid") VGid gid,
        @JsonProperty("brandGroupName") @Param("brandGroupName") VBrandGroupName brandGroupName,
        @JsonProperty("targetBrandCodes") @Param("targetBrandCodes") VTargetBrandCodes targetBrandCodes
    ) {
        this.gid = gid;
        this.brandGroupName = brandGroupName;
        this.targetBrandCodes = targetBrandCodes;
    }

    /**
     * 銘柄グループIDを明示的に設定するコンストラクタ。
     *
     * @param brandGroup 銘柄グループ
     * @param gid 銘柄グループID
     */
    public BrandGroup(
        BrandGroup brandGroup,
        VGid gid
    ) {
        this.gid = gid;
        this.brandGroupName = brandGroup.brandGroupName;
        this.targetBrandCodes = brandGroup.targetBrandCodes;
    }

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -6138701734142838596L;

    /**
     * ユーザID
     */
    private VUid uid;

    /**
     * 銘柄グループID
     */
    private VGid gid;

    /**
     * 銘柄グループ名
     */
    @NotNull
    private VBrandGroupName brandGroupName;

    /**
     * 対象銘柄コード一覧
     */
    @NotNull
    private VTargetBrandCodes targetBrandCodes;


    /**
     * <p>取引戦略IDを生成する。</p>
     *
     * @return 取引戦略
     */
    public BrandGroup generateGid() {
        return new BrandGroup(
            this,
            new VGid().generateGid()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
