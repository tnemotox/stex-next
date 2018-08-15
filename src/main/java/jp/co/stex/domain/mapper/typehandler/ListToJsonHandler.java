package jp.co.stex.domain.mapper.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.stex.base.exception.StexSystemException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
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
public class ListToJsonHandler extends BaseTypeHandler<List<Object>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<Object> list, JdbcType jdbcType) throws SQLException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            preparedStatement.setString(i, mapper.writeValueAsString(list));
        } catch (JsonProcessingException e) {
            throw new StexSystemException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(resultSet.getString(s), new TypeReference<List<Object>>(){});
        } catch (IOException e) {
            throw new StexSystemException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(resultSet.getString(i), new TypeReference<List<Object>>(){});
        } catch (IOException e) {
            throw new StexSystemException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(callableStatement.getString(i), new TypeReference<List<Object>>(){});
        } catch (IOException e) {
            throw new StexSystemException();
        }
    }
}
