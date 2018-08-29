package jp.co.stex.domain.mapper.strategy;

import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.BrandGroup;
import jp.co.stex.domain.model.strategy.value.VGid;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>銘柄グループを操作するマッパーです。</p>
 * <p>ユーザIDが合致するもののみ操作可能です。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Mapper
@Repository
public interface BrandGroupMapper {

    /**
     * <p>銘柄グループを全て取得する。</p>
     *
     * @param uid ユーザID
     * @param gid 銘柄グループID
     * @return 銘柄グループリスト
     */
    BrandGroup findOne(@Param("uid") VUid uid, @Param("gid") VGid gid);

    /**
     * <p>銘柄グループ一覧を取得する。</p>
     *
     * @param uid ユーザID
     * @return 銘柄グループ一覧
     */
    List<BrandGroup> findAll(@Param("uid") VUid uid);

    /**
     * <p>銘柄グループを新規作成する。</p>
     *
     * @param uid ユーザID
     * @param brandGroup 銘柄グループ
     * @return 銘柄グループID
     */
    VGid createOne(@Param("uid") VUid uid, @Param("brandGroup") BrandGroup brandGroup);

    /**
     * <p>銘柄グループIDが合致するレコードを更新する。</p>
     *
     * @param uid ユーザID
     * @param brandGroup 銘柄グループ
     */
    void updateOne(@Param("uid") VUid uid, @Param("brandGroup") BrandGroup brandGroup);

    /**
     * <p>銘柄グループIDが合致するレコードを削除する。</p>
     *
     * @param uid ユーザID
     * @param gid 銘柄グループID
     */
    void deleteOne(@Param("uid") VUid uid, @Param("gid") VGid gid);
}
