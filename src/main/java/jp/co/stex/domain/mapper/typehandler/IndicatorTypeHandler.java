package jp.co.stex.domain.mapper.typehandler;

import jp.co.stex.domain.model.strategy.code.IndicatorType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>指標種別をMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class IndicatorTypeHandler extends BaseTypeHandler<IndicatorType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, IndicatorType indicatorType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, indicatorType.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return IndicatorType.findById(resultSet.getInt(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return IndicatorType.findById(resultSet.getInt(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return IndicatorType.findById(callableStatement.getInt(i));
    }
}
