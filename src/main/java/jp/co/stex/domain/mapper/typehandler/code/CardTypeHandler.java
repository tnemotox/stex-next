package jp.co.stex.domain.mapper.typehandler.code;

import jp.co.stex.domain.model.strategy.code.CardType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>カード種別をMyBatisでマッピングするハンドラです。</p>
 *
 * @author t.nemoto.x
 */
public class CardTypeHandler extends BaseTypeHandler<CardType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, CardType cardType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, cardType.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return CardType.findById(resultSet.getInt(s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return CardType.findById(resultSet.getInt(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return CardType.findById(callableStatement.getInt(i));
    }
}
