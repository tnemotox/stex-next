package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
import jp.co.stex.domain.model.strategy.BrandGroup;
import jp.co.stex.domain.model.strategy.BrandGroups;
import jp.co.stex.domain.model.strategy.value.VGid;

import java.util.List;

/**
 * <p>銘柄グループを操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
public interface IBrandGroupService {

    /**
     * <p>銘柄グループを取得する。</p>
     *
     * @param uid ユーザID
     * @return 銘柄グループ一覧
     */
    BrandGroups findAll(VUid uid);

    /**
     * <p>銘柄グループを新規作成する。</p>
     *
     * @param uid ユーザID
     * @param brandGroup 銘柄グループ
     * @return 取引戦略戦略ID
     */
    VGid createOne(VUid uid, BrandGroup brandGroup);

    /**
     * <p>銘柄グループを更新する。</p>
     *
     * @param uid ユーザID
     * @param brandGroup 銘柄グループ
     */
    void updateOne(VUid uid, BrandGroup brandGroup);

    /**
     * <p>銘柄グループを削除する。</p>
     *
     * @param uid ユーザID
     * @param gid 銘柄グループID
     */
    void deleteOne(VUid uid, VGid gid);
}
