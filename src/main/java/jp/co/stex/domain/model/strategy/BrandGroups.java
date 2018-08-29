package jp.co.stex.domain.model.strategy;

import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>銘柄グループ一覧を表すモデルです。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@EqualsAndHashCode
@Getter
@Builder
public class BrandGroups implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -1011626326230298754L;

    /**
     * コンストラクタ。
     */
    public BrandGroups(List<BrandGroup> brandGroups) {
        this.brandGroups = brandGroups;
    }

    /**
     * 引数で渡した銘柄グループから銘柄グループ一覧を組み立てるコンストラクタ。
     */
    public BrandGroups(BrandGroup... brandGroups) {
        this.brandGroups = Arrays.asList(brandGroups);
    }

    /**
     * 銘柄グループ一覧
     */
    @JsonValue
    private List<BrandGroup> brandGroups;

    /**
     * 銘柄グループ一覧から指定した添字の銘柄グループを取得する。
     *
     * @param i 添字
     * @return 銘柄グループ
     */
    public BrandGroup get(int i) {
        return brandGroups.get(i);
    }

    /**
     * 空の銘柄グループ一覧を生成する。
     *
     * @return 銘柄グループ一覧
     */
    public static BrandGroups generateEmptyBrandGroup() {
        return new BrandGroups(Collections.emptyList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
