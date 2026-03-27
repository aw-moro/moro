package com.aceward.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aceward.usermanagement.dao.common.PagenationHelper;
import com.aceward.usermanagement.dao.dto.ItemListDto;
import com.aceward.usermanagement.form.ItemSearchForm;
import com.aceward.usermanagement.service.ItemLisrSearchService;

/**
 * 品目一覧画面コントローラ。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Controller
public class ItemListController {

    /** 品目一覧画面検索サービス */
    @Autowired
    private ItemLisrSearchService itemLisrSearchService;

    /** １ページ当たりの最大表示件数 */
    private static final int DEFAULT_PAGEABLE_SIZE = 5;

    /**
     * 初期表示処理。
     * 
     * @param form  品目登録フォーム
     * @param model Model
     * @return 遷移先URL
     */
    @RequestMapping(value = "/item/list", method = RequestMethod.GET)
    public String index(@PageableDefault(size = DEFAULT_PAGEABLE_SIZE, page = 0) Pageable pageable, Model model) {
        // ページング参考サイト
        // https://ksby.hatenablog.com/entry/2015/01/24/174819
        ItemSearchForm form = new ItemSearchForm();
        form.setPage(0L);
        form.setSize((long) DEFAULT_PAGEABLE_SIZE);

        // 全件検索を行う。
        Page<ItemListDto> page = itemLisrSearchService.execute(form, pageable);

        PagenationHelper ph = new PagenationHelper(page.getNumber(), page.getSize(), page.getTotalPages());

        model.addAttribute("form", form);
        model.addAttribute("page", page);
        model.addAttribute("ph", ph);

        return "item/itemList";
    }

    /**
     * 検索処理。
     * 
     * @param form  品目検索条件フォーム
     * @param model Model
     * @return 遷移先URL
     */
    @RequestMapping(value = "/item/list/search", method = RequestMethod.GET)
    public String search(ItemSearchForm form,
            @PageableDefault(size = DEFAULT_PAGEABLE_SIZE, page = 0) Pageable pageable, Model model) {
        // 検索条件をもとに検索を行う
        Page<ItemListDto> page = itemLisrSearchService.execute(form, pageable);

        PagenationHelper ph = new PagenationHelper(page.getNumber(), page.getSize(), page.getTotalPages());

        model.addAttribute("form", form);
        model.addAttribute("page", page);
        model.addAttribute("ph", ph);

        return "item/itemList";
    }
}
