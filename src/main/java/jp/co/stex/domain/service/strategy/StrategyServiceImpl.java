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
     * <p>全ての取引戦略を取得する。</p>
     *
     * @return 取引戦略リスト
     */
    @Override
    public List<TradeStrategyEntity> findAll(int loginUserId) {
        return tradeStrategyMapper.findAll(loginUserId);
    }

    @Override
    public void createOne(int uid, TradeStrategyEntity tradeStrategy) {

    }
}
