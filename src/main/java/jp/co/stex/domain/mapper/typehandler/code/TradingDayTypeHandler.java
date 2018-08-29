package jp.co.stex.domain.mapper.typehandler.code;

import jp.co.stex.domain.model.strategy.code.TradingDayType;
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
public class TradingDayTypeHandler extends BaseTypeHandler<TradingDayType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, TradingDayType tradingDayType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setBoolean(i, tradingDayType.isFlag());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradingDayType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return TradingDayType.findByFlag(resultSet.getBoolean(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradingDayType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return TradingDayType.findByFlag(resultSet.getBoolean(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradingDayType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return TradingDayType.findByFlag(callableStatement.getBoolean(i));
    }
}
