package com.aceward.usermanagement.controller;

import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.aceward.usermanagement.exception.OptimisticErrorException;
import com.aceward.usermanagement.form.ItemRegisterForm;
import com.aceward.usermanagement.service.ItemRegisterValidateService;
import com.aceward.usermanagement.service.ItemUpdateService;
import com.aceward.usermanagement.service.ItemUpdateViewService;

/**
 * 品目更新画面コントローラ。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Controller
public class ItemUpdateController {

    /** 品目登録バリデーションサービス */
    @Autowired private ItemRegisterValidateService itemRegisterValidateService;
    /** 品目更新サービス */
    @Autowired private ItemUpdateService itemUpdateService;
    /** 品目更新画面初期表示サービス */
    @Autowired private ItemUpdateViewService itemUpdateViewService;

    /**
     * 初期表示処理。
     * 
     * @param itemID 品目ID
     * @param model Model
     * @return 遷移先URL
     */
    @RequestMapping(value = "/item/update/{itemId}", method = RequestMethod.GET)
    public String index(@PathVariable("itemId") UUID itemId, Model model) {
        ItemRegisterForm form = new ItemRegisterForm();

        try {
            form = itemUpdateViewService.execute(itemId);
        } catch (NotFoundException e) {
            model.addAttribute("errorMessages", List.of(e.getMessage()));
        }

        model.addAttribute("form", form);

        return "item/itemUpdate";
    }

    /**
     * 更新処理。
     * 
     * @param form 品目登録フォーム
     * @param model Model
     * @return 遷移先URL
     */
    @RequestMapping(value = "/item/updateComplete", method = RequestMethod.POST)
    public String update(ItemRegisterForm form, Model model) {
        model.addAttribute("form", form);

        // 入力チェック
        List<String> errorMessageList = itemRegisterValidateService.execute(form);

        if (CollectionUtils.isNotEmpty(errorMessageList)) {
            // 入力エラーがある場合
            model.addAttribute("errorMessages", errorMessageList);

            return "item/itemUpdate";
        }

        // 品目更新を実行し、品目コードをを取得する
        final String itemCode;
        try {
            itemCode = itemUpdateService.execute(form);
        } catch (NotFoundException e) {
            // データ非存在
            model.addAttribute("errorMessages", List.of(e.getMessage()));
            return "item/itemUpdate";
        } catch (OptimisticErrorException e) {
            // 楽観ロックエラー
            model.addAttribute("errorMessages", List.of(e.getMessage()));
            return "item/itemUpdate";
        }

        // 品目更新完了画面に表示するための品目コードを設定する
        model.addAttribute("itemCode", itemCode);
        model.addAttribute("itemId", form.getItemId());

        return "/item/itemUpdateComple";
    }
}
