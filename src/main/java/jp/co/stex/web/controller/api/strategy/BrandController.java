package jp.co.stex.web.controller.api.strategy;

import jp.co.stex.domain.model.strategy.BrandEntity;
import jp.co.stex.domain.service.strategy.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>銘柄を操作するコントローラです。</p>
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
    private final IBrandService brandService;

    /**
     * <p>銘柄の一覧を取得する。</p>
     *
     * @return 銘柄リスト
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<BrandEntity>> fetch() {
        return ResponseEntity.ok(brandService.findAllBrands());
    }
}
