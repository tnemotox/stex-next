package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.mapper.strategy.TradeStrategyMapper;
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
public class TradeStrategyService implements ITradeStrategyService {

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
}
