package com.aceward.usermanagement.controller;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aceward.usermanagement.dao.mapper.ItemDetailMapper;
import com.aceward.usermanagement.form.ItemDetailForm;
import com.aceward.usermanagement.service.ItemDeleteService;
import com.aceward.usermanagement.service.ItemDetailViewService;

/**
 * 品目詳細画面コントローラ。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Controller
public class ItemDeleteController {

    /** 品目詳細画面初期表示サービス */
    @Autowired
    private ItemDetailViewService itemDetailViewService;
    /** 品目削除サービス */
    @Autowired
    private ItemDeleteService itemDeleteService;

    /**
     * 初期表示処理。
     * 
     * @param itemID 品目ID
     * @param model  Model
     * @return 遷移先URL
     */
    @GetMapping("/item/delete/{id}")
    public String delete(@PathVariable("id") String itemID, RedirectAttributes redirectAttributes) {

        try {
            // 削除実行（ここですでに消えていれば Service が例外を投げる）
            itemDeleteService.execute(UUID.fromString(itemID));

            // 一覧画面に表示する「成功メッセージ」をセット
            redirectAttributes.addFlashAttribute("successMessage", "品目を削除しました。");

        } catch (NotFoundException e) {
            // データがなかった場合などの「エラーメッセージ」をセット
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/item/list";
    }
}
