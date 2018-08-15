package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.mapper.strategy.AnalysisBrandGroupMapper;
import jp.co.stex.domain.mapper.strategy.BrandMapper;
import jp.co.stex.domain.mapper.strategy.TradeStrategyMapper;
import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
import jp.co.stex.domain.model.strategy.BrandEntity;
import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import jp.co.stex.domain.model.strategy.TradeStrategyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>取引戦略を操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
@Service
@RequiredArgsConstructor
public class StrategyServiceImpl implements StrategyService {

    private final TradeStrategyMapper tradeStrategyMapper;

    private final AnalysisBrandGroupMapper analysisBrandGroupMapper;

    private final BrandMapper brandMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TradeStrategyEntity> findAllTradeStrategy(int loginUserId) {
        return tradeStrategyMapper.findAll(loginUserId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createOneTradeStrategy(TradeStrategyEntity tradeStrategy) {
        return tradeStrategyMapper.createOne(tradeStrategy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOneTradeStrategy(TradeStrategyEntity tradeStrategy) {
        tradeStrategyMapper.updateOne(tradeStrategy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOneTradeStrategy(int uid, int sid) {
        tradeStrategyMapper.deleteOne(uid, sid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnalysisBrandGroupEntity> findAllAnalysisBrandGroup(int uid) {
        return analysisBrandGroupMapper.findAll(uid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createOneAnalysisBrandGroup(AnalysisBrandGroupEntity analysisBrandGroup) {
        return analysisBrandGroupMapper.createOne(analysisBrandGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOneAnalysisBrandGroup(AnalysisBrandGroupEntity analysisBrandGroup) {
        analysisBrandGroupMapper.updateOne(analysisBrandGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOneAnalysisBrandGroup(int uid, int sid) {
        analysisBrandGroupMapper.deleteOne(uid, sid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BrandEntity> findAllBrands() {
        return brandMapper.findAll();
    }

    @Override
    public int createOneTradeStrategyCard(TradeStrategyCardEntity tradeStrategyCard) {
        return 0;
    }

    @Override
    public void updateOneTradeStrategyCard(TradeStrategyCardEntity tradeStrategyCard) {

    }

    @Override
    public void deleteOneTradeStrategyCard(int uid, Integer cid) {

    }
}
