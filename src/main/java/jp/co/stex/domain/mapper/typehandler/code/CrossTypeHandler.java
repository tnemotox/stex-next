package jp.co.stex.domain.mapper.typehandler.code;

import jp.co.stex.domain.model.strategy.code.CrossType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>交差種別をMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class CrossTypeHandler extends BaseTypeHandler<CrossType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, CrossType crossType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, crossType.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CrossType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return CrossType.findById(resultSet.getInt(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CrossType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return CrossType.findById(resultSet.getInt(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CrossType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return CrossType.findById(callableStatement.getInt(i));
    }
}
