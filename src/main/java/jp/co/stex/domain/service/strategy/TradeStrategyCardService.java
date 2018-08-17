package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.mapper.strategy.TradeStrategyCardMapper;
import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
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
public class TradeStrategyCardService implements ITradeStrategyCardService {

    private final TradeStrategyCardMapper tradeStrategyCardMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TradeStrategyCardEntity> findAllTradeStrategyCard(int uid, int sid) {
        return tradeStrategyCardMapper.findAllBySid(uid, sid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createOneTradeStrategyCard(TradeStrategyCardEntity tradeStrategyCard) {
        tradeStrategyCard.makeLabel();
        return tradeStrategyCardMapper.createOne(tradeStrategyCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOneTradeStrategyCard(TradeStrategyCardEntity tradeStrategyCard) {
        tradeStrategyCard.makeLabel();
        tradeStrategyCardMapper.updateOne(tradeStrategyCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOneTradeStrategyCard(int uid, int cid) {
        tradeStrategyCardMapper.deleteOne(uid, cid);
    }
}
