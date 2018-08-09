package jp.co.stex.domain.service.base;

import jp.co.stex.base.MessageCode;
import jp.co.stex.base.exception.StexSystemException;
import jp.co.stex.domain.model.base.ResponseMessage;
import jp.co.stex.domain.model.base.code.ErrorLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.thymeleaf.util.ArrayUtils;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * メッセージを抽出するサービスです。
 *
 * @author t.nemoto.x
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    /**
     * メッセージソース
     */
    private final MessageSource messageSource;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseMessage makeResponesMessage(ObjectError error, ErrorLevel level) {
        List<String> argsList = new ArrayList<>();
        if (!ArrayUtils.isEmpty(error.getArguments())) {
            for (Object obj : error.getArguments()) {
                if (obj instanceof DefaultMessageSourceResolvable) {
                    DefaultMessageSourceResolvable dmsr = (DefaultMessageSourceResolvable) obj;
                    argsList.add(dmsr.getCode());
                } else {
                    argsList.add(obj.toString());
                }
            }
            argsList.toArray(new String[argsList.size()]);
        }

        String message = messageSource.getMessage(error.getCode(), argsList.toArray(), Locale.JAPAN);
        return new ResponseMessage(error.getCode(), argsList.toArray(new String[argsList.size()]), level, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseMessage makeResponesMessage(FieldError error, ErrorLevel level) {
        String message = messageSource.getMessage(error.getCodes()[0], new Object[] {}, Locale.JAPAN);
        return new ResponseMessage(error.getCodes()[0], new String[] {}, level, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseMessage makeResponesMessage(ConstraintViolation<?> violation, ErrorLevel level) {
        String code = String.join("",
            violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
            ".",
            violation.getRootBeanClass().getSimpleName(),
            ".",
            violation.getPropertyPath().toString()
        );
        return new ResponseMessage(code, new String[]{}, level, messageSource.getMessage(code, new Object[]{}, Locale.getDefault()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseMessage makeResponesMessage(MessageCode code, String... args) throws StexSystemException {
        ErrorLevel level;
        switch (code.getCode().split("\\.")[0]) {
            case "w":
                level = ErrorLevel.WARNING;
                break;
            case "i":
                level = ErrorLevel.INFO;
                break;
            case "e":
                level = ErrorLevel.DANGER;
                break;
            default:
                throw new StexSystemException(MessageCode.E0002);
        }
        String message = messageSource.getMessage(code.getCode(), args, Locale.JAPAN);
        return new ResponseMessage(code.getCode(), args, level, message);
    }
}
