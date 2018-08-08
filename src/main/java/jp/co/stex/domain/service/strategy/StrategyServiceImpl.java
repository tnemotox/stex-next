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
public class StrategyServiceImpl implements StrategyService {

    private final TradeStrategyMapper tradeStrategyMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TradeStrategyEntity> findAll(int loginUserId) {
        return tradeStrategyMapper.findAll(loginUserId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createOne(TradeStrategyEntity tradeStrategy) {
        return tradeStrategyMapper.createOne(tradeStrategy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOne(TradeStrategyEntity tradeStrategy) {
        tradeStrategyMapper.updateOne(tradeStrategy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOne(int uid, int sid) {
        tradeStrategyMapper.deleteOne(uid, sid);
    }
}
