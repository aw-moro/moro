package com.aceward.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aceward.usermanagement.dao.domain.Item;
// import com.aceward.usermanagement.dao.domain.ItemAddition;
// import com.aceward.usermanagement.dao.domain.ItemDetail;
import com.aceward.usermanagement.enums.AdditionEnum;
import com.aceward.usermanagement.form.ItemDetailForm;
// import com.aceward.usermanagement.service.db.ItemAdditionService;
import com.aceward.usermanagement.service.db.ItemDetailService;
import com.aceward.usermanagement.service.db.ItemService;

/**
 * 品目詳細画面初期表示サービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class ItemDetailViewService {

    /** 品目サービス */
    @Autowired
    private ItemService itemService;
    /** 品目詳細サービス */
    @Autowired
    private ItemDetailService itemDetailService;
    /** 品目付加情報サービス */
    // @Autowired
    // private ItemAdditionService itemAdditionService;

    /**
     * 品目詳細画面の初期表示情報を取得する。
     * 
     * @param itemID 品目ID
     * @return 品目詳細フォーム
     * @throws NotFoundException データが存在しない場合
     */
    public ItemDetailForm execute(UUID itemID) throws NotFoundException {
        // 品目
        Optional<Item> optionalItem = itemService.findById(itemID);

        if (optionalItem.isEmpty()) {
            throw new NotFoundException("品目情報が登録されていません。itemID=" + itemID);
        }
        Item item = optionalItem.get();

        // 品目詳細
        Optional<Item> optionalItemDetail = itemDetailService.findByItemId(item.getItemId());

        if (optionalItemDetail.isEmpty()) {
            throw new NotFoundException("品目詳細が登録されていません。itemID=" + itemID);
        }
        Item itemDetail = optionalItemDetail.get();

        // // 品目付加情報
        // List<ItemAddition> itemAdditions =
        // itemAdditionService.findByItemId(item.getId());

        // // 付加情報の項目名を「、」で連結したもの
        // final String addition =
        // itemAdditions.stream().map(e ->
        // AdditionEnum.codeOf(e.getAddition()).getName())
        // .collect(Collectors.joining("、"));

        ItemDetailForm form = new ItemDetailForm();
        form.setItemId(item.getItemId());
        form.setItem(item.getItem());
        form.setItemCode(itemDetail.getItemCode());
        form.setUnitPrice(itemDetail.getUnitPrice() == null ? "" : String.valueOf(itemDetail.getUnitPrice()));
        form.setQuantity(itemDetail.getQuantity() == null ? "" : String.valueOf(itemDetail.getQuantity()));
        form.setUnit(itemDetail.getUnit());
        form.setItemDetail(itemDetail.getItemDetail());
        form.setSalesTax(itemDetail.getSalesTax());
        form.setWithholdingTax(item.getWithholdingTax() == null ? 0 : item.getWithholdingTax());
        form.setWithholdingTax(itemDetail.getWithholdingTax());

        return form;
    }
}
