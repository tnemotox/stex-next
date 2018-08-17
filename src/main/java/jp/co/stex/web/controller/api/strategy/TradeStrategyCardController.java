package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.TradeStrategyCardEntity;
import jp.co.stex.domain.service.base.UserService;
import jp.co.stex.domain.service.strategy.ITradeStrategyCardService;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>取引戦略カードの操作を行うコントローラです。</p>
 *
 * @author t.nemoto.x
 */
@RestController
@RequestMapping("/api/trade-strategy-card")
@RequiredArgsConstructor
@Validated
public class TradeStrategyCardController {

    /**
     * dozerマッパー
     */
    private final Mapper dozerMapper;

    /**
     * 取引戦略サービス
     */
    private final ITradeStrategyCardService tradeStrategyCardService;

    /**
     * ユーザサービス
     */
    private final UserService userService;

    /**
     * 取引戦略カードバリデータ
     */
    private final TradeStrategyCardFormValidator tradeStrategyCardFormValidator;

    /**
     * <p>バリデーターを追加する。</p>
     *
     * @param webDataBinder バインダ
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(tradeStrategyCardFormValidator);
    }

    @RequestMapping(path = "/{sid}", method = RequestMethod.GET)
    public ResponseEntity<List<TradeStrategyCardEntity>> fetch(@PathVariable(value = "sid", required = false) @NotNull Integer sid) {
        return ResponseEntity.ok(tradeStrategyCardService.findAllTradeStrategyCard(findUserId(), sid));
    }

    /**
     * <p>取引戦略カードを作成する。</p>
     *
     * @param form 取引戦略カードフォーム
     * @param bd バインド結果
     * @param uriBuilder URIビルダー
     * @return なし
     * @throws BindException バインド例外
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody @Validated TradeStrategyCardForm form, BindingResult bd, UriComponentsBuilder uriBuilder) throws BindException {
        if (bd.hasErrors()) {
            throw new BindException(bd);
        }
        int cid = tradeStrategyCardService.createOneTradeStrategyCard(dozerMapper.map(form, TradeStrategyCardEntity.class).setUid(findUserId()));
        return ResponseEntity.created(uriBuilder.path("/api/trade-strategy-card/{cid}").buildAndExpand(cid).toUri()).build();
    }

    /**
     * <p>取引戦略カードを更新する。</p>
     *
     * @param cid 取引戦略カードID
     * @param form 取引戦略カードフォーム
     * @param bd バインド結果
     * @return なし
     * @throws BindException バインド例外
     */
    @RequestMapping(path = {"/", "/{cid}"}, method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable(value = "cid", required = false) @NotNull Integer cid, @RequestBody @Validated TradeStrategyCardForm form, BindingResult bd) throws BindException {
        if (bd.hasErrors()) {
            throw new BindException(bd);
        }
        tradeStrategyCardService.updateOneTradeStrategyCard(dozerMapper.map(form, TradeStrategyCardEntity.class).setUid(findUserId()).setCid(cid));
        return ResponseEntity.noContent().build();
    }

    /**
     * <p>取引戦略カードを削除する。</p>
     *
     * @param cid 取引戦略カードID
     * @return なし
     */
    @RequestMapping(path = {"/", "/{cid}"}, method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(value = "cid", required = false) @NotNull Integer cid) {
        tradeStrategyCardService.deleteOneTradeStrategyCard(findUserId(), cid);
        return ResponseEntity.noContent().build();
    }

    /**
     * <p>認証に用いたユーザ名を取得する。</p>
     *
     * @return ユーザ名
     */
    private String findUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }

    /**
     * <p>認証に用いたユーザIDを取得する。</p>
     *
     * @return ユーザID
     */
    private int findUserId() {
        return userService.findUserId(findUserName());
    }
}
