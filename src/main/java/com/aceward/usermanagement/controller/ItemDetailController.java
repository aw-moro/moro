package com.aceward.usermanagement.controller;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aceward.usermanagement.dao.mapper.ItemDetailMapper;
import com.aceward.usermanagement.form.ItemDetailForm;
import com.aceward.usermanagement.service.ItemDetailViewService;

/**
 * 品目詳細画面コントローラ。
 * 
 * @author s-moro
 * @version 1.0.0
 */
@Controller
public class ItemDetailController {

    /** 品目詳細画面初期表示サービス */
    @Autowired
    private ItemDetailViewService itemDetailViewService;
    /** 品目削除 */
    @Autowired
    private ItemDetailMapper itemDetailMapper;

    /**
     * 初期表示処理。
     * 
     * @param itemID 品目ID
     * @param model  Model
     * @return 遷移先URL
     */
    @RequestMapping(value = "/item/detail/{itemID}", method = RequestMethod.GET)
    public String index(@PathVariable("itemID") UUID itemID, Model model) {
        ItemDetailForm form = new ItemDetailForm();

        try {
            // 品目情報を取得する
            form = itemDetailViewService.execute(itemID);
        } catch (NotFoundException e) {
            // 品目情報が登録されていない場合
            model.addAttribute("errorMessages", List.of(e.getMessage()));
        }

        model.addAttribute("form", form);

        return "item/itemDetail";
    }
}
