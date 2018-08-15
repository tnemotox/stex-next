package jp.co.stex.domain.mapper.typehandler;

import jp.co.stex.domain.model.strategy.code.MarketType;
import jp.co.stex.domain.model.strategy.code.TradeTimingType;
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
public class TradeTimingTypeHandler extends BaseTypeHandler<TradeTimingType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, TradeTimingType tradeTimingType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, tradeTimingType.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradeTimingType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return TradeTimingType.findById(resultSet.getInt(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradeTimingType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return TradeTimingType.findById(resultSet.getInt(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradeTimingType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return TradeTimingType.findById(callableStatement.getInt(i));
    }
}
