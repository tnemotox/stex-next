package jp.co.stex.domain.mapper.typehandler.code;

import jp.co.stex.domain.model.strategy.code.ComparisonType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>比較種別をMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class ComparisonTypeHandler extends BaseTypeHandler<ComparisonType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ComparisonType comparisonType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, comparisonType.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComparisonType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return ComparisonType.findById(resultSet.getInt(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComparisonType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return ComparisonType.findById(resultSet.getInt(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComparisonType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return ComparisonType.findById(callableStatement.getInt(i));
    }
}
