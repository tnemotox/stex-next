package jp.co.stex.domain.mapper.typehandler.code;

import jp.co.stex.domain.model.strategy.code.MarketType;
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
public class MarketTypeHandler extends BaseTypeHandler<MarketType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, MarketType marketType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, marketType.getLabel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarketType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return MarketType.findByLabel(resultSet.getString(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarketType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return MarketType.findByLabel(resultSet.getString(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarketType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return MarketType.findByLabel(callableStatement.getString(i));
    }
}
