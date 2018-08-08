package jp.co.stex.domain.service.base;

import jp.co.stex.base.MessageCode;
import jp.co.stex.domain.model.base.ResponseMessage;
import jp.co.stex.domain.model.base.code.ErrorLevel;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;

/**
 * <p>TODO 概要を記述してください</p>
 *
 * @author Kuneo
 */
public interface MessageService {

    ResponseMessage makeResponesMessage(ObjectError error, ErrorLevel level);

    ResponseMessage makeResponesMessage(FieldError error, ErrorLevel level);

    ResponseMessage makeResponesMessage(ConstraintViolation<?> violation, ErrorLevel level);

    ResponseMessage makeResponesMessage(MessageCode code, String... args);
}
