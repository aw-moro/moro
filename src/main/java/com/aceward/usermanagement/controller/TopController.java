package com.aceward.usermanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * トップ画面コントローラ。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Controller
public class TopController {

    /**
     * 初期表示処理。
     * 
     * @param model Model
     * @return 遷移先URL
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "top/index";
    }
}
