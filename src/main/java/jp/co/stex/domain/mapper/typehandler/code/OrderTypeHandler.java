package jp.co.stex.domain.mapper.typehandler.code;

import jp.co.stex.domain.model.strategy.code.OrderType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>市場種別をMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class OrderTypeHandler extends BaseTypeHandler<OrderType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, OrderType orderType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, orderType.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return OrderType.findById(resultSet.getInt(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return OrderType.findById(resultSet.getInt(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return OrderType.findById(callableStatement.getInt(i));
    }
}
