package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.TradeRuleEntity;
import jp.co.stex.domain.model.strategy.code.TradeType;
import jp.co.stex.domain.service.base.UserService;
import jp.co.stex.domain.service.strategy.ITradeRuleService;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>取引ルールの操作を行うコントローラです。</p>
 *
 * @author t.nemoto.x
 */
@RestController
@RequestMapping("/api/trade-rule")
@RequiredArgsConstructor
@Validated
public class TradeRuleController {

    /**
     * dozerマッパー
     */
    private final Mapper dozerMapper;

    /**
     * 取引戦略サービス
     */
    private final ITradeRuleService tradeRuleService;

    /**
     * ユーザサービス
     */
    private final UserService userService;

    /**
     * 取引種別コンバーター
     */
    private final TradeTypeConverter tradeTypeConverter;

    /**
     * <p>初期処理を追加する。</p>
     * <ul>
     *   <li>取引種別を変換する。</li>
     * </ul>
     * @param webDataBinder バインダ
     */
    @InitBinder
    public void initBinder(final WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(TradeType.class, tradeTypeConverter);
    }

    /**
     * <p>取引戦略IDと取引種別に紐づく取引ルールを全て取得する。</p>
     *
     * @param sid 取引戦略ID
     * @param tradeType 取引種別
     * @return 取引戦略リスト
     */
    @RequestMapping(path = "/{sid}/{tradeType}", method = RequestMethod.GET)
    public ResponseEntity<List<TradeRuleEntity>> fetch(@NotNull @PathVariable("sid") Integer sid, @NotNull @PathVariable("tradeType") TradeType tradeType) {
        return ResponseEntity.ok(tradeRuleService.findAllTradeRule(1, sid, tradeType));
    }
}
