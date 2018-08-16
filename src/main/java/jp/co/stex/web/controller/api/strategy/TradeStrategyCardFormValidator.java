package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.code.CardType;
import jp.co.stex.domain.model.strategy.code.IndicatorType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <p>取引戦略カードフォームに適用する相関チェックバリデーターです。</p>
 *
 * @author t.nemoto.x
 */
@Component
public class TradeStrategyCardFormValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return TradeStrategyCardForm.class.isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {
        // 単項目チェックエラーが発生している場合、相関チェックは行わない
        if (!errors.hasErrors()) {
            TradeStrategyCardForm form = (TradeStrategyCardForm) target;

            // カード種別が比較または交差の場合
            if (CardType.COMPARE.equals(form.getCardType()) || CardType.CROSS.equals(form.getCardType())) {
                // 左辺指標値が未入力の場合
                if (ObjectUtils.isEmpty(form.getLeftSideIndicatorType())) {
                    errors.reject("TradeStrategyCardController.LeftSideIndicatorNull");
                }
                // 左辺指標値が日付利用、かつ左辺日数がnullの場合
                else if (IndicatorType.resolveDaysUsed(form.getLeftSideIndicatorType()) && ObjectUtils.isEmpty(form.getLeftSideDays())) {
                    errors.reject("TradeStrategyCardController.LeftSideIndicatorDaysNull");
                }

                // 右辺種別が未入力の場合
                if (ObjectUtils.isEmpty(form.getRightSideFixOrFlex())) {
                    errors.reject("TradeStrategyCardController.RightSideFixOrFlexNull");
                }
                // 右辺種別が固定値、かつ右辺値がnullの場合
                else if (form.getRightSideFixOrFlex() && ObjectUtils.isEmpty(form.getRightSideFixValue())) {
                    errors.reject("TradeStrategyCardController.RightSideTypeFixAndValueNull");
                }
                // 右辺種別が指標値、かつ右辺値がnullの場合
                else if (!form.getRightSideFixOrFlex() && ObjectUtils.isEmpty(form.getRightSideIndicatorType())) {
                    errors.reject("TradeStrategyCardController.RightSideTypeFlexAndIndicatorNull");
                }
                // 右辺種別が指標値、かつ右辺指標値が日付利用、かつ右辺日数がnullの場合
                else if (!form.getRightSideFixOrFlex() && IndicatorType.resolveDaysUsed(form.getRightSideIndicatorType()) && ObjectUtils.isEmpty(form.getRightSideDays())) {
                    errors.reject("TradeStrategyCardController.RightSideIndicatorDaysNull");
                }

                // 比較種別が未入力
                if (CardType.COMPARE.equals(form.getCardType()) && ObjectUtils.isEmpty(form.getComparisonType())) {
                    errors.reject("TradeStrategyCardController.ComparisonTypeNull");
                }
                // 交差種別が未入力
                else if (CardType.CROSS.equals(form.getCardType()) && ObjectUtils.isEmpty(form.getCrossType())) {
                    errors.reject("TradeStrategyCardController.CrossTypeNull");
                }
            }

            // カード種別が日数の場合
            else if (CardType.TIME.equals(form.getCardType()) && ObjectUtils.isEmpty(form.getElapsedDay())) {
                errors.reject("TradeStrategyCardController.ElapsedDayNull");
            }
        }
    }
}
