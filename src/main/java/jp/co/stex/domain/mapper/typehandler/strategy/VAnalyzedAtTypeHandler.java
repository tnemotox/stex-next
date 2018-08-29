package jp.co.stex.domain.mapper.typehandler.strategy;

import jp.co.stex.domain.model.strategy.value.VAnalyzedAt;
import jp.co.stex.domain.model.strategy.value.VTradeStrategyName;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.time.LocalDate;

/**
 * <p>最終分析日をMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class VAnalyzedAtTypeHandler extends BaseTypeHandler<VAnalyzedAt> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, VAnalyzedAt analyzedAt, JdbcType jdbcType) throws SQLException {
        preparedStatement.setDate(i, Date.valueOf(analyzedAt.getAnalyzedAt()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VAnalyzedAt getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return new VAnalyzedAt(getLocalDate(resultSet.getDate(s)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VAnalyzedAt getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return new VAnalyzedAt(getLocalDate(resultSet.getDate(i)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VAnalyzedAt getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return new VAnalyzedAt(getLocalDate(callableStatement.getDate(i)));
    }

    private static LocalDate getLocalDate(Date date) {
        return date != null ? date.toLocalDate() : null;
    }
}
