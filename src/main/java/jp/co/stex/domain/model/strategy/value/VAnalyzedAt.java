package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

/**
 * <p>最終分析分析日時を表す値オブジェクトです。</p>
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VAnalyzedAt {

    /**
     * コンストラクタ。
     *
     * @param analyzedAt 最終分析日
     */
    @JsonCreator
    public VAnalyzedAt(LocalDate analyzedAt) {
        this.analyzedAt = analyzedAt;
    }

    /**
     * 分析開始日
     */
    private LocalDate analyzedAt;

    /**
     * Jacksonで返却する値。
     *
     * @return 日付文字列の配列
     */
    @JsonValue
    public String value() {
        return analyzedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.JAPAN));
    }

    /**
     * 最終分析日を生成する。
     *
     * @return 最終分析日
     */
    public VAnalyzedAt generateAnalyzedAt() {
        return new VAnalyzedAt(LocalDate.now());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
