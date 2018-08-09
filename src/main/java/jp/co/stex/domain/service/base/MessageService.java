package jp.co.stex.domain.service.base;

import jp.co.stex.base.MessageCode;
import jp.co.stex.base.exception.StexSystemException;
import jp.co.stex.domain.model.base.ResponseMessage;
import jp.co.stex.domain.model.base.code.ErrorLevel;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;

/**
 * <p>画面メッセージを生成するサービスです。</p>
 *
 * @author t.nemoto.x
 */
public interface MessageService {

    /**
     * <p>Springを契機に発生する例外から画面メッセージ情報を生成する</p>。
     *
     * @param error エラー情報
     * @param level エラーレベル
     * @return 画面メッセージ情報
     * @since 1.0.0
     */
    ResponseMessage makeResponesMessage(ObjectError error, ErrorLevel level);

    /**
     * <p>入力チェックを契機に発生する例外から画面メッセージ情報を生成する。</p>
     *
     * @param error エラー情報
     * @param level エラーレベル
     * @return 画面メッセージ情報
     * @since 1.0.0
     */
    ResponseMessage makeResponesMessage(FieldError error, ErrorLevel level);

    /**
     * <p>入力チェックを契機に発生する例外から画面メッセージ情報を生成する。</p>
     *
     * @param violation エラー情報
     * @param level エラーレベル
     * @return 画面メッセージ情報
     * @since 1.0.0
     */
    ResponseMessage makeResponesMessage(ConstraintViolation<?> violation, ErrorLevel level);

    /**
     * <p>メッセージコードから画面メッセージ情報を生成する</p>。
     *
     * @param code メッセージコード
     * @param args 引数
     * @return メッセージ情報
     * @throws StexSystemException システム例外
     * @since 1.0.0
     */
    ResponseMessage makeResponesMessage(MessageCode code, String... args);
}
