package jp.co.stex.domain.mapper.typehandler.code;

import jp.co.stex.domain.model.strategy.TradeStrategy;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>文字列とJSONを変換してMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class TradeStrategiesTypeHandler extends BaseTypeHandler<List<TradeStrategy>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<TradeStrategy> list, JdbcType jdbcType) throws SQLException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TradeStrategy> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TradeStrategy> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TradeStrategy> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
