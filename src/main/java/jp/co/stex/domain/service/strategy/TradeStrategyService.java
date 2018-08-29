package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.TradeStrategies;
import jp.co.stex.domain.model.strategy.TradeStrategy;
import jp.co.stex.domain.model.strategy.value.VSid;
import jp.co.stex.domain.repository.TradeStrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>取引戦略を操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
@Service
@RequiredArgsConstructor
public class TradeStrategyService implements ITradeStrategyService {

    /**
     * 取引戦略マッパー
     */
    private final TradeStrategyRepository tradeStrategyRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public TradeStrategies findAll(VUid uid) {
        return tradeStrategyRepository.findAll(uid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VSid createOne(VUid uid, TradeStrategy tradeStrategy) {
        return tradeStrategyRepository.createOne(uid, tradeStrategy.generateSid());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOne(VUid uid, TradeStrategy tradeStrategy) {
        tradeStrategyRepository.updateOne(uid, tradeStrategy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOne(VUid uid, VSid sid) {
        tradeStrategyRepository.deleteOne(uid, sid);
    }
}
