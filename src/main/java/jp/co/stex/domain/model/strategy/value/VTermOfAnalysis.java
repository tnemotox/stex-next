package jp.co.stex.domain.model.strategy.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.stex.domain.utility.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * <p>分析期間を表す値オブジェクトです。</p>
 *
 * @author t.nemoto.x
 */
@EqualsAndHashCode
@Getter
@Builder
public class VTermOfAnalysis {

    /**
     * MyBatisから利用するコンストラクタ。
     */
    public VTermOfAnalysis(
        @Param("analysisStartDate") LocalDate analysisStartDate,
        @Param("analysisEndDate") LocalDate analysisEndDate
    ) {
        this.analysisStartDate = analysisStartDate;
        this.analysisEndDate = analysisEndDate;
    }

    /**
     * Jacksonから利用するコンストラクタ。
     */
    @JsonCreator
    public VTermOfAnalysis(List<LocalDate> termOfAnalysis) {
        this.analysisStartDate = termOfAnalysis.get(0);
        this.analysisEndDate = termOfAnalysis.get(1);
    }

    /**
     * 分析開始日
     */
    private LocalDate analysisStartDate;

    /**
     * 分析終了日
     */
    private LocalDate analysisEndDate;

    /**
     * Jacksonで返却する値。
     *
     * @return 日付文字列の配列
     */
    @JsonValue
    public List<String> value() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.JAPAN);
        return Arrays.asList(
            analysisStartDate.format(formatter),
            analysisEndDate.format(formatter)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
