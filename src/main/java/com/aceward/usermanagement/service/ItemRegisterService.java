package com.aceward.usermanagement.service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aceward.usermanagement.bean.ClockConfig;
import com.aceward.usermanagement.constant.Constants;
import com.aceward.usermanagement.dao.domain.Item;
// import com.aceward.usermanagement.dao.domain.ItemAddition;
// import com.aceward.usermanagement.dao.domain.ItemDetail;
import com.aceward.usermanagement.enums.GenderEnum;
import com.aceward.usermanagement.form.ItemRegisterForm;
// import com.aceward.usermanagement.service.db.ItemAdditionService;
// import com.aceward.usermanagement.service.db.ItemDetailService;
import com.aceward.usermanagement.service.db.ItemService;

import lombok.extern.slf4j.Slf4j;

/**
 * 品目登録サービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Slf4j
@Service
@Transactional
public class ItemRegisterService {

    /** ClockConfig */
    @Autowired
    private ClockConfig clockConfig;
    /** 品目サービス */
    @Autowired
    private ItemService itemService;
    // /** 品目詳細サービス */
    // @Autowired
    // private ItemService itemService;
    // /** 品目付加情報サービス */
    // @Autowired
    // private ItemAdditionService itemAdditionService;

    /**
     * 品目登録処理を実行する。
     * 
     * @param form  品目登録フォーム
     * @param 品目コード
     */
    public String execute(ItemRegisterForm form) {
        // システム日時
        final Timestamp now = new Timestamp(clockConfig.clock().instant().toEpochMilli());

        // // 品目登録
        // Item item = new Item();
        // // item.setCreatedAt(now);
        // // item.setCreatedStaffId(Constants.STAFF_ID);
        // item.setUpdatedAt(now);
        // item.setUpdatedStaffId(Constants.STAFF_ID);
        // Optional<Item> optionalItem = itemService.saveAndFlush(item);
        // Item resultItem = optionalItem.get();

        // UUID itemId = resultItem.getItemId();
        String itemCode = form.getItemCode();

        // 品目詳細登録
        Item item = new Item();

        item.setItem(form.getItem());
        item.setItemCode(form.getItemCode());
        if (StringUtils.isNotEmpty(form.getUnitPrice())) {
            item.setUnitPrice(Integer.parseInt(form.getUnitPrice()));
        }

        if (StringUtils.isNotEmpty(form.getQuantity())) {
            item.setQuantity(Integer.parseInt(form.getQuantity()));
        }

        item.setUnit(form.getUnit());
        item.setItemDetail(form.getItemDetail());
        item.setSalesTax(form.getSalesTax());
        item.setWithholdingTax(form.getWithholdingTax());

        item.setUpdatedAt(now);
        item.setUpdatedStaffId(Constants.STAFF_ID);

        item.setDeleteFlg(0);
        itemService.saveAndFlush(item);

        form.setItemId(item.getItemId());

        // // 品目付加情報登録
        // if (CollectionUtils.isNotEmpty(form.getAdditions())) {
        // for (String addition : form.getAdditions()) {
        // ItemAddition itemAddition = new ItemAddition();
        // itemAddition.setItemId(itemId);
        // itemAddition.setAddition(addition);
        // itemAdditionService.saveAndFlush(itemAddition);
        // }
        // }

        // 登録完了のログ出力
        log.info("品目登録が完了しました。 itemCode={}, createdAt={}", itemCode, now);

        return itemCode;
    }
}
