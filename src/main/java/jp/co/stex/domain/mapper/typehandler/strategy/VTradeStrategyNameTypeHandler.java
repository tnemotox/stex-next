package jp.co.stex.domain.mapper.typehandler.strategy;

import jp.co.stex.domain.model.strategy.value.VTradeStrategyName;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>取引戦略名をMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class VTradeStrategyNameTypeHandler extends BaseTypeHandler<VTradeStrategyName> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, VTradeStrategyName tradeStrategyName, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, tradeStrategyName.getTradeStrategyName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VTradeStrategyName getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return new VTradeStrategyName(resultSet.getString(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VTradeStrategyName getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return new VTradeStrategyName(resultSet.getString(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VTradeStrategyName getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return new VTradeStrategyName(callableStatement.getString(i));
    }
}
