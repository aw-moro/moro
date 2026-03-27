package com.aceward.usermanagement.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aceward.usermanagement.form.UserRegisterForm;
import com.aceward.usermanagement.service.UserRegisterService;
import com.aceward.usermanagement.service.UserRegisterValidateService;

/**
 * ユーザ登録画面コントローラ。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Controller
public class UserRegisterController {

    /** ユーザ登録バリデーションサービス */
    @Autowired private UserRegisterValidateService userRegisterValidateService;
    /** ユーザ登録サービス */
    @Autowired private UserRegisterService userRegisterService;

    /**
     * 初期表示処理。
     * 
     * @param form ユーザ登録フォーム
     * @param model Model
     * @return 遷移先URL
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String index(UserRegisterForm form, Model model) {
        model.addAttribute("form", form);

        // 初期表示は何もしない
        return "user/userRegister";
    }

    /**
     * 登録処理。
     * 
     * @param form ユーザ登録フォーム
     * @param model Model
     * @return 遷移先URL
     */
    @RequestMapping(value = "/user/registerComplete", method = RequestMethod.POST)
    public String register(UserRegisterForm form, Model model) {
        model.addAttribute("form", form);

        // 入力チェック
        List<String> errorMessageList = userRegisterValidateService.execute(form);

        if (CollectionUtils.isNotEmpty(errorMessageList)) {
            // 入力エラーがある場合
            model.addAttribute("errorMessages", errorMessageList);

            return "user/userRegister";
        }

        // ユーザ登録を実行し、自動採番したユーザコードを取得する
        final String userCd = userRegisterService.execute(form);

        // ユーザ登録完了画面に表示するためのユーザコードを設定する
        model.addAttribute("userCd", userCd);

        return "user/userRegisterComple";
    }
}
