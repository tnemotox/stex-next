package jp.co.stex.domain.mapper.typehandler.strategy;

import jp.co.stex.domain.model.strategy.value.VBrandGroupName;
import jp.co.stex.domain.model.strategy.value.VTradeStrategyName;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>銘柄グループ名をMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class VBrandGroupNameTypeHandler extends BaseTypeHandler<VBrandGroupName> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, VBrandGroupName brandGroupName, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, brandGroupName.getBrandGroupName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VBrandGroupName getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return new VBrandGroupName(resultSet.getString(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VBrandGroupName getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return new VBrandGroupName(resultSet.getString(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VBrandGroupName getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return new VBrandGroupName(callableStatement.getString(i));
    }
}
