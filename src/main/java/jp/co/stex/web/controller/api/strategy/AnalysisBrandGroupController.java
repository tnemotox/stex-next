package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
import jp.co.stex.domain.service.base.UserService;
import jp.co.stex.domain.service.strategy.IAnalysisBrandGroupService;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>分析対象銘柄を操作するコントローラです。</p>
 *
 * @author t.nemoto.x
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
    private final IAnalysisBrandGroupService analysisBrandGroupService;

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
        return ResponseEntity.ok(analysisBrandGroupService.findAllAnalysisBrandGroup(findUserId()));
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
        int gid = analysisBrandGroupService.createOneAnalysisBrandGroup(dozerMapper.map(form, AnalysisBrandGroupEntity.class).setUid(findUserId()));
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
    @RequestMapping(path = {"/", "/{gid}"}, method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable(value = "gid", required = false) @NotNull Integer gid, @RequestBody @Validated AnalysisBrandGroupForm form, BindingResult bd) throws BindException {
        if (bd.hasErrors()) {
            throw new BindException(bd);
        }
        analysisBrandGroupService.updateOneAnalysisBrandGroup(dozerMapper.map(form, AnalysisBrandGroupEntity.class).setUid(findUserId()).setGid(gid));
        return ResponseEntity.noContent().build();
    }

    /**
     * <p>分析対象銘柄を削除する。</p>
     *
     * @param gid 分析対象銘柄ID
     * @return なし
     */
    @RequestMapping(path = {"/", "/{gid}"}, method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(value = "gid", required = false) @NotNull Integer gid) {
        analysisBrandGroupService.deleteOneAnalysisBrandGroup(findUserId(), gid);
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
