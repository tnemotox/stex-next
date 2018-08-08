package jp.co.stex.web.controller.api;

import lombok.RequiredArgsConstructor;

import jp.co.stex.domain.model.strategy.TradeStrategyEntity;
import jp.co.stex.domain.service.base.UserService;
import jp.co.stex.domain.service.strategy.StrategyService;

import org.dozer.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * <p>取引戦略の操作を行うコントローラです。</p>
 *
 * @author t.nemoto.x
 */
@RestController
@RequestMapping("/api/strategy")
@RequiredArgsConstructor
@Validated
public class StrategyController {

    /**
     * dozerマッパー
     */
    private final Mapper dozerMapper;

    /**
     * 取引戦略サービス
     */
    private final StrategyService strategyService;

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
    public ResponseEntity<List<TradeStrategyEntity>> fetch() {
        return ResponseEntity.ok(strategyService.findAll(findUserId()));
    }

    /**
     * <p>取引戦略を作成する。</p>
     *
     * @param form 取引戦略フォーム
     * @param bd バインド結果
     * @param uriBuilder URIビルダー
     * @return なし
     * @throws BindException バインド例外
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody @Validated TradeStrategyForm form, BindingResult bd, UriComponentsBuilder uriBuilder) throws BindException {
        if (bd.hasErrors()) {
            throw new BindException(bd);
        }
        TradeStrategyEntity entity = dozerMapper.map(form, TradeStrategyEntity.class);
        int id = strategyService.createOne(findUserId(), entity);
        return ResponseEntity.created(uriBuilder.path("/api/strategy/{id}").buildAndExpand(id).toUri()).build();
    }

    /**
     * <p>取引戦略を更新する。</p>
     *
     * @param sid 取引戦略ID
     * @param form 取引戦略フォーム
     * @param bd バインド結果
     * @return なし
     * @throws BindException バインド例外
     */
    @RequestMapping(path = "/{sid}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable("sid") @NotNull Integer sid, @RequestBody @Validated TradeStrategyForm form, BindingResult bd) throws BindException {
        if (bd.hasErrors()) {
            throw new BindException(bd);
        }
        strategyService.updateOne(findUserId(), sid, dozerMapper.map(form, TradeStrategyEntity.class));
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
