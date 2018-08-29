package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.TradeStrategies;
import jp.co.stex.domain.model.strategy.TradeStrategy;
import jp.co.stex.domain.model.strategy.value.VSid;
import jp.co.stex.domain.service.base.UserService;
import jp.co.stex.domain.service.strategy.ITradeStrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p>取引戦略の操作を行うコントローラです。</p>
 *
 * @author t.nemoto.x
 */
@RestController
@RequestMapping("/api/trade-strategy")
@RequiredArgsConstructor
@Validated
public class TradeStrategyController {

    /**
     * 取引戦略サービス
     */
    private final ITradeStrategyService tradeStrategyService;

    /**
     * ユーザサービス
     */
    private final UserService userService;

    /**
     * <p>取引戦略の一覧を取得する。</p>
     *
     * @return 取引戦略リスト
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<TradeStrategies> fetch() {
        return ResponseEntity.ok(tradeStrategyService.findAll(userService.findUserId()));
    }

    /**
     * <p>取引戦略を作成する。</p>
     *
     * @param tradeStrategy 取引戦略フォーム
     * @param bd バインド結果
     * @param uriBuilder URIビルダー
     * @return なし
     * @throws BindException バインド例外
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody @Validated TradeStrategy tradeStrategy, BindingResult bd, UriComponentsBuilder uriBuilder) throws BindException {
        if (bd.hasErrors()) {
            throw new BindException(bd);
        }
        VSid sid = tradeStrategyService.createOne(userService.findUserId(), tradeStrategy);
        return ResponseEntity.created(uriBuilder.path("/api/strategy/{sid}").buildAndExpand(sid.getSid()).toUri()).build();
    }

    /**
     * <p>取引戦略を更新する。</p>
     *
     * @param sid 取引戦略ID
     * @param tradeStrategy 取引戦略
     * @param bd バインド結果
     * @return なし
     * @throws BindException バインド例外
     */
    @RequestMapping(path = {"/", "/{sid}"}, method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable(value = "sid", required = false) @NotNull VSid sid, @RequestBody @Validated TradeStrategy tradeStrategy, BindingResult bd) throws BindException {
        if (bd.hasErrors()) {
            throw new BindException(bd);
        }
        tradeStrategyService.updateOne(userService.findUserId(), new TradeStrategy(tradeStrategy, sid));
        return ResponseEntity.noContent().build();
    }

    /**
     * <p>取引戦略を削除する。</p>
     *
     * @param sid 取引戦略ID
     * @return なし
     */
    @RequestMapping(path = {"/", "/{sid}"}, method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(value = "sid", required = false) @NotNull VSid sid) {
        tradeStrategyService.deleteOne(userService.findUserId(), sid);
        return ResponseEntity.noContent().build();
    }
}
