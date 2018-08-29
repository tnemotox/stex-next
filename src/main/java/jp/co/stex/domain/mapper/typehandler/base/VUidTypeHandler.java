package jp.co.stex.domain.mapper.typehandler.base;

import jp.co.stex.domain.model.base.value.VUid;
import jp.co.stex.domain.model.strategy.value.VGid;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * <p>分析銘柄グループIDをMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class VUidTypeHandler extends BaseTypeHandler<VUid> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, VUid uid, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, uid.getUid());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VUid getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return new VUid(UUID.fromString(resultSet.getString(s)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VUid getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return new VUid(UUID.fromString(resultSet.getString(i)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VUid getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return new VUid(UUID.fromString(callableStatement.getString(i)));
    }
}
