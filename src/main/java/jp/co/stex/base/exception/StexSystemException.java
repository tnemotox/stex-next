package jp.co.stex.base.exception;

import jp.co.stex.base.MessageCode;
import lombok.Getter;

/**
 * <p>システム例外を表すクラスです。</p>
 *
 * @author t.nemoto.x
 */
@Getter
public class StexSystemException extends RuntimeException {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 6720036428488307491L;

    /**
     * メッセージコード
     */
    private String code;

    /**
     * 引数
     */
    private String[] args;

    /**
     * <p>[概 要] コンストラクタ。</p>
     * <p>[詳 細] </p>
     * <p>[備 考] </p>
     */
    public StexSystemException() {
        super();
    }

    /**
     * <p>[概 要] コンストラクタ。</p>
     * <p>[詳 細] </p>
     * <p>[備 考] </p>
     *
     * @param code メッセージコード
     */
    public StexSystemException(MessageCode code) {
        super();
        this.code = code.getCode();
    }

    /**
     * <p>[概 要] コンストラクタ。</p>
     * <p>[詳 細] </p>
     * <p>[備 考] </p>
     *
     * @param code メッセージコード
     * @param args 引数
     */
    public StexSystemException(MessageCode code, String... args) {
        super();
        this.code = code.getCode();
        this.args = args;
    }

    /**
     * <p>[概 要] コンストラクタ。</p>
     * <p>[詳 細] </p>
     * <p>[備 考] </p>
     *
     * @param cause 原因
     * @param code  メッセージコード
     */
    public StexSystemException(Throwable cause, MessageCode code) {
        super(cause);
        this.code = code.getCode();
    }

    /**
     * <p>[概 要] コンストラクタ。</p>
     * <p>[詳 細] </p>
     * <p>[備 考] </p>
     *
     * @param cause 原因
     * @param code  メッセージコード
     * @param args  引数
     */
    public StexSystemException(Throwable cause, MessageCode code, String... args) {
        super(cause);
        this.code = code.getCode();
        this.args = args;
    }
}
