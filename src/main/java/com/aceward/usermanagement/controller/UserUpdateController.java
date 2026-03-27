package com.aceward.usermanagement.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.aceward.usermanagement.exception.OptimisticErrorException;
import com.aceward.usermanagement.form.UserRegisterForm;
import com.aceward.usermanagement.service.UserRegisterValidateService;
import com.aceward.usermanagement.service.UserUpdateService;
import com.aceward.usermanagement.service.UserUpdateViewService;

/**
 * ユーザ更新画面コントローラ。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Controller
public class UserUpdateController {

    /** ユーザ登録バリデーションサービス */
    @Autowired private UserRegisterValidateService userRegisterValidateService;
    /** ユーザ更新サービス */
    @Autowired private UserUpdateService userUpdateService;
    /** ユーザ更新画面初期表示サービス */
    @Autowired private UserUpdateViewService userUpdateViewService;

    /**
     * 初期表示処理。
     * 
     * @param userCd ユーザコード
     * @param model Model
     * @return 遷移先URL
     */
    @RequestMapping(value = "/user/update/{userCd}", method = RequestMethod.GET)
    public String index(@PathVariable("userCd") String userCd, Model model) {
        UserRegisterForm form = new UserRegisterForm();

        try {
            form = userUpdateViewService.execute(userCd);
        } catch (NotFoundException e) {
            model.addAttribute("errorMessages", List.of(e.getMessage()));
        }

        model.addAttribute("form", form);

        return "user/userUpdate";
    }

    /**
     * 更新処理。
     * 
     * @param form ユーザ登録フォーム
     * @param model Model
     * @return 遷移先URL
     */
    @RequestMapping(value = "/user/updateComplete", method = RequestMethod.POST)
    public String update(UserRegisterForm form, Model model) {
        model.addAttribute("form", form);

        // 入力チェック
        List<String> errorMessageList = userRegisterValidateService.execute(form);

        if (CollectionUtils.isNotEmpty(errorMessageList)) {
            // 入力エラーがある場合
            model.addAttribute("errorMessages", errorMessageList);

            return "user/userUpdate";
        }

        // ユーザ更新を実行し、ユーザコードをを取得する
        final String userCd;
        try {
            userCd = userUpdateService.execute(form);
        } catch (NotFoundException e) {
            // データ非存在
            model.addAttribute("errorMessages", List.of(e.getMessage()));
            return "user/userUpdate";
        } catch (OptimisticErrorException e) {
            // 楽観ロックエラー
            model.addAttribute("errorMessages", List.of(e.getMessage()));
            return "user/userUpdate";
        }

        // ユーザ更新完了画面に表示するためのユーザコードを設定する
        model.addAttribute("userCd", userCd);

        return "/user/userUpdateComple";
    }
}
