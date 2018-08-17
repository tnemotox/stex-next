package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;

import java.util.List;

/**
 * <p>分析銘柄グループを操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
public interface IAnalysisBrandGroupService {

    /**
     * <p>分析対象銘柄を取得する。</p>
     *
     * @param uid ユーザID
     * @return 分析対象銘柄リスト
     */
    List<AnalysisBrandGroupEntity> findAllAnalysisBrandGroup(int uid);

    /**
     * <p>分析対象銘柄を新規作成する。</p>
     *
     * @param analysisBrandGroup 分析対象銘柄
     * @return 取引戦略戦略ID
     */
    int createOneAnalysisBrandGroup(AnalysisBrandGroupEntity analysisBrandGroup);

    /**
     * <p>分析対象銘柄を更新する。</p>
     *
     * @param analysisBrandGroup 分析対象銘柄
     */
    void updateOneAnalysisBrandGroup(AnalysisBrandGroupEntity analysisBrandGroup);

    /**
     * <p>分析対象銘柄を削除する。</p>
     *
     * @param uid ユーザID
     * @param sid 取引戦略ID
     */
    void deleteOneAnalysisBrandGroup(int uid, int sid);
}
