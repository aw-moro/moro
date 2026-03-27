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
import com.aceward.usermanagement.form.ItemRegisterForm;
// import com.aceward.usermanagement.service.db.ItemAdditionService;
import com.aceward.usermanagement.service.db.ItemDetailService;
import com.aceward.usermanagement.service.db.ItemService;

/**
 * 品目更新画面初期表示サービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class ItemUpdateViewService {

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
     * 品目更新画面の初期表示情報を取得する。
     * 
     * @param itemId 品目ID
     * @return 品目登録フォーム
     * @throws NotFoundException データが存在しない場合
     */
    public ItemRegisterForm execute(UUID itemId) throws NotFoundException {
        // 品目
        Optional<Item> optionalItem = itemService.findById(itemId);

        if (optionalItem.isEmpty()) {
            throw new NotFoundException("品目情報が登録されていません。itemId=" + itemId);
        }
        Item item = optionalItem.get();

        // // 品目詳細
        // Optional<ItemDetail> optionalItemDetail =
        // itemDetailService.findByItemId(item.getId());

        // if (optionalItemDetail.isEmpty()) {
        // throw new NotFoundException("品目詳細が登録されていません。itemCode=" + itemCode);
        // }
        // ItemDetail itemDetail = optionalItemDetail.get();

        // // 品目付加情報
        // List<String> additions =
        // itemAdditionService.findByItemId(item.getId()).stream()
        // .map(ItemAddition::getAddition).collect(Collectors.toList());

        ItemRegisterForm form = new ItemRegisterForm();
        form.setItemId(item.getItemId());
        form.setItem(item.getItem());
        form.setItemCode(item.getItemCode());
        form.setUnitPrice(item.getUnitPrice() == null ? "" : String.valueOf(item.getUnitPrice()));
        form.setQuantity(item.getQuantity() == null ? "" : String.valueOf(item.getQuantity()));
        form.setUnit(item.getUnit());
        form.setItemDetail(item.getItemDetail());
        form.setSalesTax(item.getSalesTax());
        form.setWithholdingTax(item.getWithholdingTax() == null ? 0 : item.getWithholdingTax());
        form.setUpdatedAtMillisecond(String.valueOf(item.getUpdatedAt().getTime()));
        return form;
    }
}
