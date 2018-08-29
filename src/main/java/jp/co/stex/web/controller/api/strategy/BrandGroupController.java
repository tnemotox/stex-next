package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.BrandGroup;
import jp.co.stex.domain.model.strategy.BrandGroups;
import jp.co.stex.domain.model.strategy.value.VGid;
import jp.co.stex.domain.service.base.UserService;
import jp.co.stex.domain.service.strategy.IBrandGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p>銘柄グループを操作するコントローラです。</p>
 *
 * @author t.nemoto.x
 */
@RestController
@RequestMapping("/api/brand-group")
@RequiredArgsConstructor
@Validated
public class BrandGroupController {

    /**
     * 銘柄グループサービス
     */
    private final IBrandGroupService brandGroupService;

    /**
     * ユーザサービス
     */
    private final UserService userService;

    /**
     * <p>銘柄グループ一覧を取得する。</p>
     *
     * @return 銘柄グループリスト
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<BrandGroups> fetch() {
        return ResponseEntity.ok(brandGroupService.findAll(userService.findUserId()));
    }

    /**
     * <p>銘柄グループを作成する。</p>
     *
     * @param brandGroup 取引戦略フォーム
     * @param bd バインド結果
     * @param uriBuilder URIビルダー
     * @return なし
     * @throws BindException バインド例外
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody @Validated BrandGroup brandGroup, BindingResult bd, UriComponentsBuilder uriBuilder) throws BindException {
        if (bd.hasErrors()) {
            throw new BindException(bd);
        }
        VGid gid = brandGroupService.createOne(userService.findUserId(), brandGroup);
        return ResponseEntity.created(uriBuilder.path("/api/brand-group/{gid}").buildAndExpand(gid.getGid()).toUri()).build();
    }

    /**
     * <p>銘柄グループを更新する。</p>
     *
     * @param gid 銘柄グループID
     * @param brandGroup 銘柄グループフォーム
     * @param bd バインド結果
     * @return なし
     * @throws BindException バインド例外
     */
    @RequestMapping(path = {"/", "/{gid}"}, method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable(value = "gid", required = false) @NotNull VGid gid, @RequestBody @Validated BrandGroup brandGroup, BindingResult bd) throws BindException {
        if (bd.hasErrors()) {
            throw new BindException(bd);
        }
        brandGroupService.updateOne(userService.findUserId(), new BrandGroup(brandGroup, gid));
        return ResponseEntity.noContent().build();
    }

    /**
     * <p>銘柄グループを削除する。</p>
     *
     * @param gid 銘柄グループID
     * @return なし
     */
    @RequestMapping(path = {"/", "/{gid}"}, method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(value = "gid", required = false) @NotNull VGid gid) {
        brandGroupService.deleteOne(userService.findUserId(), gid);
        return ResponseEntity.noContent().build();
    }
}
