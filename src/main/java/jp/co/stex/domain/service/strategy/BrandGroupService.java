package jp.co.stex.domain.service.strategy;

import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.BrandGroup;
import jp.co.stex.domain.model.strategy.BrandGroups;
import jp.co.stex.domain.model.strategy.value.VGid;
import jp.co.stex.domain.repository.IBrandGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>取引戦略を操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
@Service
@RequiredArgsConstructor
public class BrandGroupService implements IBrandGroupService {

    private final IBrandGroupRepository brandGroupRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandGroups findAll(VUid uid) {
        return brandGroupRepository.findAll(uid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VGid createOne(VUid uid, BrandGroup brandGroup) {
        return brandGroupRepository.createOne(uid, brandGroup.generateGid());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOne(VUid uid, BrandGroup brandGroup) {
        brandGroupRepository.updateOne(uid, brandGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOne(VUid uid, VGid gid) {
        brandGroupRepository.deleteOne(uid, gid);
    }
}
