package jp.co.stex.domain.repository;

import jp.co.stex.domain.mapper.strategy.TradeStrategyMapper;
import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.TradeStrategies;
import jp.co.stex.domain.model.strategy.TradeStrategy;
import jp.co.stex.domain.model.strategy.value.VSid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>取引戦略を操作するリポジトリです。</p>
 *
 * @author t.nemoto.x
 */
@Repository
@RequiredArgsConstructor
public class TradeStrategyRepository implements ITradeStrategyRepository{

    /**
     * 取引戦略マッパー
     */
    private final TradeStrategyMapper tradeStrategyMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public TradeStrategy findOne(VUid uid, VSid sid) {
        return tradeStrategyMapper.findOne(uid, sid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradeStrategies findAll(VUid uid) {
        List<TradeStrategy> tradeStrategies = tradeStrategyMapper.findAll(uid);
        return CollectionUtils.isEmpty(tradeStrategies) ? TradeStrategies.generateEmptyTradeStrategies() : new TradeStrategies(tradeStrategies);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VSid createOne(VUid uid, TradeStrategy tradeStrategy) {
        return tradeStrategyMapper.createOne(uid, tradeStrategy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOne(VUid uid, TradeStrategy tradeStrategy) {
        tradeStrategyMapper.updateOne(uid, tradeStrategy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOne(VUid uid, VSid sid) {
        tradeStrategyMapper.deleteOne(uid, sid);
    }
}
