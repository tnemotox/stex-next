package jp.co.stex.web.controller;

import jp.co.stex.base.MessageCode;
import jp.co.stex.base.exception.StexBusinessException;
import jp.co.stex.base.exception.StexSystemException;
import jp.co.stex.domain.model.base.ResponseMessage;
import jp.co.stex.domain.model.base.code.ErrorLevel;
import jp.co.stex.domain.service.base.MessageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static jp.co.stex.base.MessageCode.*;

/**
 * <p>例外処理を提供する共通のハンドラです。</p>
 *
 * @author t.nemoto.x
 */
@ControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
public class GlobalRestExceptionHandler {

    /**
     * メッセージサービス
     */
    private final MessageService messageService;

    /**
     * ロガー
     */
    private final Logger LOG = LogManager.getLogger(getClass().getName());

    /**
     * <p>入力チェックエラーを処理する例外ハンドラです。</p>
     * @param e バインド例外
     * @return 画面メッセージ
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<Map<String, ResponseMessage>> handleException(BindException e) {
        return ResponseEntity.badRequest().body(
            e.getFieldErrors().stream()
                .map(error -> messageService.makeResponesMessage(error, ErrorLevel.WARNING))
                .collect(toMap(ResponseMessage::getCode, m -> m, (m1, m2) -> m1))
        );
    }

    /**
     * <p>ビジネス例外を処理する例外ハンドラです。</p>
     *
     * @param e ビジネス例外
     * @return 画面メッセージ
     */
    @ExceptionHandler(value = StexBusinessException.class)
    public ResponseEntity<Map<String, ResponseMessage>> handleException(StexBusinessException e) {
        ResponseMessage message = messageService.makeResponesMessage(MessageCode.toEnum(e.getCode()), e.getArgs());
        LOG.warn(message, e);
        Map<String, ResponseMessage> messageMap = new HashMap<>();
        messageMap.put(message.getCode(), message);
        return ResponseEntity.badRequest().body(messageMap);
    }

    /**
     * <p>システム例外を処理する例外ハンドラです。</p>
     *
     * @param e システム例外
     * @return 画面メッセージ
     */
    @ExceptionHandler(value = StexSystemException.class)
    public ResponseEntity<Map<String, ResponseMessage>> handleException(StexSystemException e) {
        ResponseMessage message = messageService.makeResponesMessage(MessageCode.toEnum(e.getCode()), e.getArgs());
        LOG.error(message, e);
        Map<String, ResponseMessage> messageMap = new HashMap<>();
        messageMap.put(message.getCode(), message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageMap);
    }

    /**
     * <p>予期せぬ例外を処理する例外ハンドラです。</p>
     *
     * @param e システム例外
     * @return 画面メッセージ
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, ResponseMessage>> handleException(Exception e) {
        ResponseMessage message = messageService.makeResponesMessage(E0006);
        LOG.error(message, e);
        Map<String, ResponseMessage> messageMap = new HashMap<>();
        messageMap.put(message.getCode(), message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageMap);
    }

    /**
     * <p>引数の異常による例外を処理する例外ハンドラです。</p>
     *
     * @param e 例外
     * @return 画面メッセージ
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, ResponseMessage>> handleException(MethodArgumentNotValidException e) {
        Map<String, ResponseMessage> messageMap =e.getBindingResult().getAllErrors().stream()
            .map(error -> messageService.makeResponesMessage(error, ErrorLevel.WARNING))
            .collect(toMap(ResponseMessage::getCode, m -> m, (m1, m2) -> m1));
        LOG.error(messageMap);
        return ResponseEntity.badRequest().body(messageMap);
    }

    /**
     * <p>リクエストの形式の異常による例外、日付形式などコンバートの失敗による例外を処理する例外ハンドラです。</p>
     *
     * @param e 例外
     * @return 画面メッセージ
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, ConversionFailedException.class})
    public ResponseEntity<Map<String, ResponseMessage>> handleException(HttpMessageNotReadableException e) {
        ResponseMessage message = messageService.makeResponesMessage(WCM0001);
        LOG.error(message, e);
        Map<String, ResponseMessage> messageMap = new HashMap<>();
        messageMap.put(message.getCode(), message);
        return ResponseEntity.badRequest().body(messageMap);
    }

    /**
     * <p>データベースとの接続に関する例外を処理する例外ハンドラです。</p>
     *
     * @param e 例外
     * @return 画面メッセージ
     */
    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<Map<String, ResponseMessage>> handleException(SQLException e) {
        ResponseMessage message = messageService.makeResponesMessage(E0001, "データベース");
        LOG.error(message, e);
        Map<String, ResponseMessage> messageMap = new HashMap<>();
        messageMap.put(message.getCode(), message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageMap);
    }
}
