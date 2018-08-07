package jp.co.stex.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>画面遷移を行うコントローラです。</p>
 *
 * @author t.nemoto.x
 */
@Controller
public class PageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "stex";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
}
