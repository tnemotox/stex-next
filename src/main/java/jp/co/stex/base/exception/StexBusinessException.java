package jp.co.stex.base.exception;

import jp.co.stex.base.MessageCode;
import lombok.Getter;

/**
 * <p>ビジネス例外を表すクラスです。</p>
 *
 * @author t.nemoto.x
 */
@Getter
public class StexBusinessException extends Exception {

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
     * <p>引数なしコンストラクタ。</p>
     */
    public StexBusinessException() {
        super();
    }

    /**
     * <p>メッセージコードを引数にとるコンストラクタ。</p>
     *
     * @param code メッセージコード
     */
    public StexBusinessException(MessageCode code) {
        super();
        this.code = code.getCode();
    }

    /**
     * <p>メッセージコードと引数を引数にとるコンストラクタ。</p>
     *
     * @param code メッセージコード
     * @param args 引数
     */
    public StexBusinessException(MessageCode code, String... args) {
        super();
        this.code = code.getCode();
        this.args = args;
    }

    /**
     * <p>メッセージコードとエラー原因を引数にとるコンストラクタ。</p>
     *
     * @param cause 原因
     * @param code  メッセージコード
     */
    public StexBusinessException(Throwable cause, MessageCode code) {
        super(cause);
        this.code = code.getCode();
    }

    /**
     * <p>エラー原因とメッセージコードと引数を引数にとるコンストラクタ。</p>
     *
     * @param code  メッセージコード
     * @param args  引数
     * @param cause 原因
     */
    public StexBusinessException(Throwable cause, MessageCode code, String... args) {
        super(cause);
        this.code = code.getCode();
        this.args = args;
    }
}
