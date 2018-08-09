package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.mapper.strategy.TradeStrategyMapper;
import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
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
        // TODO 空のメソッド
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createOneAnalysisBrandGroup(AnalysisBrandGroupEntity analysisBrandGroup) {
        // TODO 空のメソッド
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOneAnalysisBrandGroup(AnalysisBrandGroupEntity analysisBrandGroup) {
        // TODO 空のメソッド
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOneAnalysisBrandGroup(int uid, int sid) {
        // TODO 空のメソッド
        
    }
}
