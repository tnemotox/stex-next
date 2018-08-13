package jp.co.stex.domain.mapper.strategy;

import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>分析銘柄グループを操作するマッパーです。</p>
 * <p>ユーザIDが合致するもののみ操作可能です。</p>
 *
 * @author t.nemoto.x
 * @since 1.0
 */
@Mapper
@Repository
public interface AnalysisBrandGroupMapper {

    /**
     * <p>分析銘柄グループを全て取得する。</p>
     *
     * @param uid ユーザID
     * @param gid 銘柄グループID
     * @return 分析銘柄グループリスト
     */
    AnalysisBrandGroupEntity findOne(@Param("uid") int uid, @Param("gid") int gid);

    /**
     * <p>分析銘柄グループを全て取得する。</p>
     *
     * @param uid ユーザID
     * @return 分析銘柄グループリスト
     */
    List<AnalysisBrandGroupEntity> findAll(@Param("uid") int uid);

    /**
     * <p>分析銘柄グループを新規作成する。</p>
     *
     * @param analysisBrandGroupEntity 分析銘柄グループ
     * @return 分析銘柄グループID
     */
    int createOne(@Param("analysisBrandGroup") AnalysisBrandGroupEntity analysisBrandGroupEntity);

    /**
     * <p>分析銘柄グループIDが合致するレコードを更新する。</p>
     *
     * @param analysisBrandGroupEntity 分析銘柄グループ
     */
    void updateOne(@Param("analysisBrandGroup") AnalysisBrandGroupEntity analysisBrandGroupEntity);

    /**
     * <p>分析銘柄グループIDが合致するレコードを削除する。</p>
     *
     * @param uid ユーザID
     * @param gid 分析銘柄グループグループID
     */
    void deleteOne(@Param("uid") int uid, @Param("gid") int gid);
}
