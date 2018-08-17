package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.mapper.strategy.AnalysisBrandGroupMapper;
import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
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
public class AnalysisBrandGroupService implements IAnalysisBrandGroupService {

    private final AnalysisBrandGroupMapper analysisBrandGroupMapper;

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
}
