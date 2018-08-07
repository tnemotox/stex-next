package jp.co.stex.domain.model.base.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * エラーレベルを定義する列挙子です。
 *
 * @author t.nemoto.x
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ErrorLevel {

    INFO("通知"), WARNING("警告"), DANGER("エラー");

    /**
     * エラーレベル
     */
    private final String level;
}
