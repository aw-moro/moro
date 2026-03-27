package com.aceward.usermanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aceward.usermanagement.dao.dto.ItemListDto;
import com.aceward.usermanagement.dao.dto.ItemSearchDto;
import com.aceward.usermanagement.form.ItemSearchForm;
import com.aceward.usermanagement.service.db.ItemDetailService;

/**
 * 品目一覧画面検索サービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class ItemLisrSearchService {

    /** 品目詳細サービス */
    @Autowired
    private ItemDetailService itemDetailService;

    /**
     * 品目一覧画面の検索処理を実行する。
     * 
     * @param form 品目検索フォーム
     * @param pageable Pageable
     * @return 検索結果を含めたページ情報
     */
    public Page<ItemListDto> execute(ItemSearchForm form, Pageable pageable) {
        // formからdtoへ詰め替え
        ItemSearchDto dto = new ItemSearchDto();
        dto.setItemCode(form.getItemCode());
        dto.setItem(form.getItem());

        dto.setPage(form.getPage());
        dto.setSize(form.getSize());

        // 件数取得
        int count = itemDetailService.countByItemSearchDto(dto);

        if (count == 0) {
            return new PageImpl<>(new ArrayList<>(), pageable, count);
        }
        // 検索処理
        List<ItemListDto> itemList = itemDetailService.findByItemSearchDto(dto);

        return new PageImpl<>(itemList, pageable, count);
    }
}
