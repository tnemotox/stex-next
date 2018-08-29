package jp.co.stex.domain.mapper.typehandler.code;

import jp.co.stex.domain.model.strategy.code.BuyingAndSellingType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>売買フラグをMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class BuyingAndSellingTypeHandler extends BaseTypeHandler<BuyingAndSellingType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BuyingAndSellingType buyingAndSelling, JdbcType jdbcType) throws SQLException {
        preparedStatement.setBoolean(i, buyingAndSelling.isFlag());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuyingAndSellingType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return BuyingAndSellingType.findByFlag(resultSet.getBoolean(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuyingAndSellingType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return BuyingAndSellingType.findByFlag(resultSet.getBoolean(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuyingAndSellingType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return BuyingAndSellingType.findByFlag(callableStatement.getBoolean(i));
    }
}
