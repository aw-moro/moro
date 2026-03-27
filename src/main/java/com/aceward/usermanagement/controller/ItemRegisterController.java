package com.aceward.usermanagement.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aceward.usermanagement.form.ItemRegisterForm;
import com.aceward.usermanagement.service.ItemRegisterService;
import com.aceward.usermanagement.service.ItemRegisterValidateService;

/**
 * 品目登録画面コントローラ。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Controller
public class ItemRegisterController {

    /** 品目登録バリデーションサービス */
   @Autowired private ItemRegisterValidateService itemRegisterValidateService;
    /** 品目登録サービス */
   @Autowired private ItemRegisterService itemRegisterService;

    /**
     * 初期表示処理。
     * 
     * @param form 品目登録フォーム
     * @param model Model
     * @return 遷移先URL
     */
    @RequestMapping(value = "/item/register", method = RequestMethod.GET)
    public String index(ItemRegisterForm form, Model model) {
        model.addAttribute("form", form);

        // 初期表示は何もしない
        return "item/itemRegister";
    }

    /**
     * 登録処理。
     * 
     * @param form 品目登録フォーム
     * @param model Model
     * @return 遷移先URL
     */
   @RequestMapping(value = "/item/registerComplete", method = RequestMethod.POST)
   public String register(ItemRegisterForm form, Model model) {
       model.addAttribute("form", form);

        //入力チェック
       List<String> errorMessageList = itemRegisterValidateService.execute(form);

       if (CollectionUtils.isNotEmpty(errorMessageList)) {
            //入力エラーがある場合
           model.addAttribute("errorMessages", errorMessageList);

           return "item/itemRegister";
       }

        //品目登録を実行し、品目コードを取得する
       final String itemCode = itemRegisterService.execute(form);

        //品目登録完了画面に表示するための品目コードを設定する
       model.addAttribute("itemCode", itemCode);
       model.addAttribute("itemId", form.getItemId());

       return "item/itemRegisterComple";
   }
}
