package jp.co.stex.web.controller.api.strategy;

import lombok.RequiredArgsConstructor;

import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
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
 * 分析対象銘柄を操作するコントローラです。
 */
@RestController
@RequestMapping("/api/analysis-brand-group")
@RequiredArgsConstructor
@Validated
public class AnalysisBrandGroupController {

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
     * <p>分析対象銘柄の一覧を取得する。</p>
     *
     * @return 分析対象銘柄リスト
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<AnalysisBrandGroupEntity>> fetch() {
        return ResponseEntity.ok(strategyService.findAllAnalysisBrandGroup(findUserId()));
    }

    /**
     * <p>分析対象銘柄を作成する。</p>
     *
     * @param form 取引戦略フォーム
     * @param bd バインド結果
     * @param uriBuilder URIビルダー
     * @return なし
     * @throws BindException バインド例外
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody @Validated AnalysisBrandGroupForm form, BindingResult bd, UriComponentsBuilder uriBuilder) throws BindException {
        if (bd.hasErrors()) {
            throw new BindException(bd);
        }
        int gid = strategyService.createOneAnalysisBrandGroup(dozerMapper.map(form, AnalysisBrandGroupEntity.class));
        return ResponseEntity.created(uriBuilder.path("/api/analysis-brand-group/{gid}").buildAndExpand(gid).toUri()).build();
    }

    /**
     * <p>分析対象銘柄を更新する。</p>
     *
     * @param gid 分析対象銘柄ID
     * @param form 分析対象銘柄フォーム
     * @param bd バインド結果
     * @return なし
     * @throws BindException バインド例外
     */
    @RequestMapping(path = "/{gid}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable(value = "gid", required = false) @NotNull Integer gid, @RequestBody @Validated AnalysisBrandGroupForm form, BindingResult bd) throws BindException {
        if (bd.hasErrors()) {
            throw new BindException(bd);
        }
        strategyService.updateOneAnalysisBrandGroup(dozerMapper.map(form, AnalysisBrandGroupEntity.class).setUid(findUserId()).setGid(gid));
        return ResponseEntity.noContent().build();
    }

    /**
     * <p>分析対象銘柄を削除する。</p>
     *
     * @param gid 分析対象銘柄ID
     * @return なし
     * @throws BindException バインド例外
     */
    @RequestMapping(path = "/{gid}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable(value = "gid", required = false) @NotNull Integer gid) throws BindException {
        strategyService.deleteOneAnalysisBrandGroup(findUserId(), gid);
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
