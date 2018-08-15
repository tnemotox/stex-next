package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
import jp.co.stex.domain.model.strategy.BrandEntity;
import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import jp.co.stex.domain.model.strategy.TradeStrategyEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>取引戦略を操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
public interface StrategyService {

    /**
     * <p>全ての取引戦略を取得する。</p>
     *
     * @param loginUserId ログインユーザID
     * @return 取引戦略リスト
     */
    List<TradeStrategyEntity> findAllTradeStrategy(int loginUserId);

    /**
     * <p>取引戦略を新規作成する。</p>
     *
     * @param tradeStrategy 取引戦略
     * @return 取引戦略戦略ID
     */
    int createOneTradeStrategy(TradeStrategyEntity tradeStrategy);

    /**
     * <p>取引戦略を更新する。</p>
     *
     * @param tradeStrategy 取引戦略
     */
    void updateOneTradeStrategy(TradeStrategyEntity tradeStrategy);

    /**
     * <p>取引戦略を削除する。</p>
     *
     * @param uid ユーザID
     * @param sid 取引戦略ID
     */
    void deleteOneTradeStrategy(int uid, int sid);

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

    /**
     * <p>銘柄一覧を取得する。</p>
     *
     * @return 銘柄一覧
     */
    List<BrandEntity> findAllBrands();

    /**
     * <p>取引戦略カードを追加する。</p>
     *
     * @param tradeStrategyCard 取引戦略カード
     * @return 取引戦略カードID
     */
    int createOneTradeStrategyCard(TradeStrategyCardEntity tradeStrategyCard);

    /**
     * <p>取引戦略カードを更新する。</p>
     *
     * @param tradeStrategyCard 取引戦略カード
     */
    void updateOneTradeStrategyCard(TradeStrategyCardEntity tradeStrategyCard);

    /**
     * <p>取引戦略カードを削除する。</p>
     *
     * @param uid ユーザID
     * @param cid 取引戦略カードID
     */
    void deleteOneTradeStrategyCard(int uid, Integer cid);
}
