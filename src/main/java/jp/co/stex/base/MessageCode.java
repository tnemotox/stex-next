package jp.co.stex.base;

/**
 * <p>メッセージコードを表す列挙型です。</p>
 *
 * @author t.nemoto.x
 */
public enum MessageCode {

    TEST_E("e.stex.ts.0001"),

    TEST_W("w.stex.ts.0001"),

    TEST_I("i.stex.ts.0001"),

    TEST_F("f.stex.ts.0001"),

    W0001("w.stex.sy.0001"),

    W0002("w.stex.sy.0002"),

    E0001("e.stex.sy.0001"),

    E0002("e.stex.sy.0002"),

    E0003("e.stex.sy.0003"),

    E0004("e.stex.sy.0004"),

    E0005("e.stex.sy.0005"),

    E0006("e.stex.sy.0006"),

    E0007("e.stex.sy.0007"),

    E0008("e.stex.sy.0008"),

    WCM0001("w.stex.cm.0001"),

    WCM0002("w.stex.cm.0002"),

    WST0001("w.stex.st.0001"),

    WST0002("w.stex.st.0002"),

    WST0003("w.stex.st.0003"),

    WSC0001("w.stex.sc.0001"),

    WSC0002("w.stex.sc.0002"),

    WAN0001("w.stex.an.0001");

    /**
     * <p>コンストラクタ。</p>
     *
     * @param code メッセージコード
     */
    MessageCode(String code) {
        this.code = code;
    }

    /**
     * メッセージコード
     */
    private String code;

    /**
     * <p>メッセージコードを取得する。</p>
     *
     * @return メッセージコード
     */
    public String getCode() {
        return code;
    }

    /**
     * <p>コード値から列挙型を逆引きする。</p>
     *
     * @param code メッセージコード
     * @return ENUM型のメッセージコード
     */
    public static MessageCode toEnum(String code) {
        for (MessageCode value : MessageCode.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
