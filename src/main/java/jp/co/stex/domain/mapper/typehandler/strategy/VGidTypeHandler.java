package jp.co.stex.domain.mapper.typehandler.strategy;

import jp.co.stex.domain.model.strategy.value.VGid;
import jp.co.stex.domain.model.strategy.value.VSid;
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
public class VGidTypeHandler extends BaseTypeHandler<VGid> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, VGid gid, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, gid.getGid());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VGid getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return new VGid(UUID.fromString(resultSet.getString(s)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VGid getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return new VGid(UUID.fromString(resultSet.getString(i)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VGid getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return new VGid(UUID.fromString(callableStatement.getString(i)));
    }
}
