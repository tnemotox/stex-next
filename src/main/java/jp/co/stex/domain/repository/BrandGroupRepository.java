package jp.co.stex.domain.repository;

import jp.co.stex.domain.mapper.strategy.BrandGroupMapper;
import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.BrandGroup;
import jp.co.stex.domain.model.strategy.BrandGroups;
import jp.co.stex.domain.model.strategy.value.VGid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>銘柄グループを操作するリポジトリです。</p>
 *
 * @author t.nemoto.x
 */
@Repository
@RequiredArgsConstructor
public class BrandGroupRepository implements IBrandGroupRepository {

    /**
     * 銘柄グループマッパー
     */
    private final BrandGroupMapper brandGroupMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandGroup findOne(VUid uid, VGid gid) {
        return brandGroupMapper.findOne(uid, gid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandGroups findAll(VUid uid) {
        List<BrandGroup> brandGroups = brandGroupMapper.findAll(uid);
        return CollectionUtils.isEmpty(brandGroups) ? BrandGroups.generateEmptyBrandGroup() : new BrandGroups(brandGroups);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VGid createOne(VUid uid, BrandGroup brandGroup) {
        return brandGroupMapper.createOne(uid, brandGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOne(VUid uid, BrandGroup brandGroup) {
        brandGroupMapper.updateOne(uid, brandGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOne(VUid uid, VGid gid) {
        brandGroupMapper.deleteOne(uid, gid);
    }
}
