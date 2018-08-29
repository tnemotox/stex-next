package jp.co.stex.domain.mapper.typehandler.code;

import jp.co.stex.domain.model.strategy.code.TradeType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>取引日種別をMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class TradeTypeHandler extends BaseTypeHandler<TradeType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, TradeType tradeType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setBoolean(i, tradeType.isFlag());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradeType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return TradeType.findByFlag(resultSet.getBoolean(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradeType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return TradeType.findByFlag(resultSet.getBoolean(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradeType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return TradeType.findByFlag(callableStatement.getBoolean(i));
    }
}
