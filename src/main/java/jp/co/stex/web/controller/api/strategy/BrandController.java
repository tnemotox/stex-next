package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.AnalysisBrandGroupEntity;
import jp.co.stex.domain.model.strategy.BrandEntity;
import jp.co.stex.domain.service.base.UserService;
import jp.co.stex.domain.service.strategy.StrategyService;
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
@RequestMapping("/api/brand")
@RequiredArgsConstructor
@Validated
public class BrandController {

    /**
     * 取引戦略サービス
     */
    private final StrategyService strategyService;

    /**
     * <p>分析対象銘柄の一覧を取得する。</p>
     *
     * @return 分析対象銘柄リスト
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<BrandEntity>> fetch() {
        return ResponseEntity.ok(strategyService.findAllBrands());
    }
}
