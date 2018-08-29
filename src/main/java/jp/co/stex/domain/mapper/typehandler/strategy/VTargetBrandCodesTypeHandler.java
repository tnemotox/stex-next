package jp.co.stex.domain.mapper.typehandler.strategy;

import jp.co.stex.domain.model.strategy.value.VTargetBrandCodes;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>対象銘柄コード一覧をMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class VTargetBrandCodesTypeHandler extends BaseTypeHandler<VTargetBrandCodes> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, VTargetBrandCodes targetBrandCodes, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, VTargetBrandCodes.serializeToJson(targetBrandCodes));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VTargetBrandCodes getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return VTargetBrandCodes.deserializeFromJson(resultSet.getString(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VTargetBrandCodes getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return VTargetBrandCodes.deserializeFromJson(resultSet.getString(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VTargetBrandCodes getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return VTargetBrandCodes.deserializeFromJson(callableStatement.getString(i));
    }
}
