package jp.co.stex.domain.mapper.typehandler.strategy;

import jp.co.stex.domain.model.strategy.value.VMemo;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>コメントをMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class VMemoTypeHandler extends BaseTypeHandler<VMemo> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, VMemo comment, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, comment.getMemo());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMemo getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return new VMemo(resultSet.getString(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMemo getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return new VMemo(resultSet.getString(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMemo getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return new VMemo(callableStatement.getString(i));
    }
}
