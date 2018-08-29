package jp.co.stex.domain.mapper.typehandler.strategy;

import jp.co.stex.domain.model.strategy.value.VSid;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * <p>取引戦略IDをMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class VSidTypeHandler extends BaseTypeHandler<VSid> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, VSid sid, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, sid.getSid());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VSid getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return new VSid(UUID.fromString(resultSet.getString(s)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VSid getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return new VSid(UUID.fromString(resultSet.getString(i)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VSid getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return new VSid(UUID.fromString(callableStatement.getString(i)));
    }
}
