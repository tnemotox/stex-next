package jp.co.stex.domain.repository;

import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.BrandGroup;
import jp.co.stex.domain.model.strategy.BrandGroups;
import jp.co.stex.domain.model.strategy.value.VGid;

/**
 * <p>銘柄グループを操作するリポジトリです。</p>
 *
 * @author t.nemoto.x
 */
public interface IBrandGroupRepository {

    /**
     * <p>銘柄グループ一覧を取得する。</p>
     *
     * @param uid ユーザID
     * @param gid 銘柄グループID
     * @return 銘柄グループ一覧
     */
    BrandGroup findOne(VUid uid, VGid gid);

    /**
     * <p>銘柄グループ一覧を取得する。</p>
     *
     * @param uid ユーザID
     * @return 銘柄グループ一覧
     */
    BrandGroups findAll(VUid uid);

    /**
     * <p>銘柄グループを追加する。</p>
     *
     * @param uid ユーザID
     * @param brandGroup 銘柄グループ
     * @return 銘柄グループID
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
