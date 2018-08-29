package jp.co.stex.domain.mapper.typehandler.code;

import jp.co.stex.domain.model.strategy.code.JointType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>結合種別をMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class JointTypeHandler extends BaseTypeHandler<JointType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, JointType jointType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, jointType.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JointType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return JointType.findById(resultSet.getInt(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JointType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return JointType.findById(resultSet.getInt(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JointType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return JointType.findById(callableStatement.getInt(i));
    }
}
