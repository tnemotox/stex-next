package jp.co.stex.domain.model.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jp.co.stex.domain.model.base.code.ErrorLevel;

import java.io.Serializable;

/**
 * 画面メッセージ情報を格納するモデルです。
 *
 * @author t.nemoto.x
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseMessage implements Serializable {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -8317084664074592982L;

    /**
     * メッセージコード
     */
    private String code;

    /**
     * 引数
     */
    private String[] args;

    /**
     * エラーレベル
     */
    private ErrorLevel level;

    /**
     * メッセージ
     */
    private String message;
}
