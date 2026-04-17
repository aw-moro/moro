package com.aceward.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aceward.usermanagement.dao.dto.ItemListDto;
import com.aceward.usermanagement.form.ItemSearchForm;
import com.aceward.usermanagement.service.ItemLisrSearchService;

@RestController // @Controllerから変更
@RequestMapping("/api") // URLの共通プレフィックス
@CrossOrigin(origins = "http://localhost:5173") // Reactのポートを許可
public class ApiItemListController {

    @Autowired
    private ItemLisrSearchService itemLisrSearchService;

    private static final int DEFAULT_PAGEABLE_SIZE = 5;

    // ReactからはこのURLを叩く
    @GetMapping("/item/list")
    public Page<ItemListDto> index(@PageableDefault(size = DEFAULT_PAGEABLE_SIZE, page = 0) Pageable pageable) {
        ItemSearchForm form = new ItemSearchForm();
        form.setPage(0L);
        form.setSize((long) DEFAULT_PAGEABLE_SIZE);

        // JSONとしてデータを返却
        return itemLisrSearchService.execute(form, pageable);
    }

    // 検索処理も同様にJSONを返す
    @GetMapping("/item/list/search")
    public Page<ItemListDto> search(ItemSearchForm form,
            @PageableDefault(size = DEFAULT_PAGEABLE_SIZE, page = 0) Pageable pageable) {
        return itemLisrSearchService.execute(form, pageable);
    }

}
