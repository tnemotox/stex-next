package jp.co.stex.domain.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jp.co.stex.base.MessageCode;
import jp.co.stex.base.exception.StexSystemException;

import static jp.co.stex.base.MessageCode.W0001;

/**
 * <p>文字列を操作するユーティリティクラスです。</p>
 *
 * @author t.nemoto.x
 */
public class StringUtils {

    public static String toString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String str;
        try {
            str =  mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new StexSystemException(W0001);
        }
        return str;
    }
}
