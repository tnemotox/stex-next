package jp.co.stex.domain.mapper.base;

import jp.co.stex.domain.model.base.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>ユーザ情報を操作するリポジトリです。</p>
 *
 * @author t.nemoto.x
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * <p>ユーザ名と一致するユーザ情報を取得する。</p>
     *
     * @param name ユーザ名
     * @return ユーザ
     */
    UserEntity findOneByName(@Param("name") String name);
}
