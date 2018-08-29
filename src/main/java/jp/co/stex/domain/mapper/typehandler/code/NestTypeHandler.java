package jp.co.stex.domain.mapper.typehandler.code;

import jp.co.stex.domain.model.strategy.code.NestType;
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
public class NestTypeHandler extends BaseTypeHandler<NestType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, NestType nestType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setBoolean(i, nestType.isFlag());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NestType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return NestType.findByFlag(resultSet.getBoolean(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NestType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return NestType.findByFlag(resultSet.getBoolean(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NestType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return NestType.findByFlag(callableStatement.getBoolean(i));
    }
}
