package com.aceward.usermanagement.controller;

import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aceward.usermanagement.form.UserDetailForm;
import com.aceward.usermanagement.service.UserDetailViewService;

/**
 * ユーザ詳細画面コントローラ。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Controller
public class UserDetailController {

    /** ユーザ詳細画面初期表示サービス */
    @Autowired
    private UserDetailViewService userDetailViewService;

    /**
     * 初期表示処理。
     * 
     * @param userCd ユーザコード
     * @param model Model
     * @return 遷移先URL
     */
    @RequestMapping(value = "/user/detail/{userCd}", method = RequestMethod.GET)
    public String index(@PathVariable("userCd") String userCd, Model model) {
        UserDetailForm form = new UserDetailForm();

        try {
            // ユーザ情報を取得する
            form = userDetailViewService.execute(userCd);
        } catch (NotFoundException e) {
            // ユーザ情報が登録されていない場合
            model.addAttribute("errorMessages", List.of(e.getMessage()));
        }

        model.addAttribute("form", form);

        return "user/userDetail";
    }
}
