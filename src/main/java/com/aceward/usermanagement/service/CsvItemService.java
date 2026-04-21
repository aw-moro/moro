package com.aceward.usermanagement.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aceward.usermanagement.bean.ClockConfig;
import com.aceward.usermanagement.constant.Constants;
import com.aceward.usermanagement.dao.domain.Item;
import com.aceward.usermanagement.form.ItemRegisterForm;
import com.aceward.usermanagement.service.db.ItemService;

import lombok.extern.slf4j.Slf4j;

/**
 * CSVバッチ品目登録・更新サービス。
 */
@Slf4j
@Service
@Transactional
public class CsvItemService {

    @Autowired
    private ClockConfig clockConfig;

    @Autowired
    private ItemService itemService;

    /**
     * CSVから取得した全項目をDBに反映する（IDがあれば更新、なければ新規）
     */
    public String execute(ItemRegisterForm form) {
        // システム日時
        final Timestamp now = new Timestamp(clockConfig.clock().instant().toEpochMilli());

        Item item = null;

        // 1. IDで既存データを検索
        if (form.getItemId() != null) {
            Optional<Item> optionalItem = itemService.findById(form.getItemId());
            if (optionalItem.isPresent()) {
                item = optionalItem.get();
                log.info("既存データを更新対象として読み込みました。 itemId={}", form.getItemId());
            }
        }

        // 2. 既存データがない場合は、新しいインスタンスを作成
        if (item == null) {
            item = new Item();
            log.info("新規データとして登録します。");
        }

        // 3. 値のセット（CSVの全項目をEntityに反映）
        item.setItem(form.getItem());
        item.setItemCode(form.getItemCode());

        // 数値項目の変換とセット
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

        // 削除フラグ
        if (StringUtils.isNotEmpty(form.getDeleteFlg())) {
            item.setDeleteFlg(Integer.parseInt(form.getDeleteFlg()));
        } else {
            item.setDeleteFlg(0);
        }

        // 共通項目のセット
        item.setUpdatedAt(now);
        item.setUpdatedStaffId(Constants.STAFF_ID);

        // 4. DB保存
        // IDがセットされた状態のEntityを保存すると、JPAが自動でUPDATE文を発行します
        itemService.saveAndFlush(item);

        form.setItemId(item.getItemId());

        log.info("品目の反映が完了しました。 itemCode={}", form.getItemCode());

        return form.getItemCode();
    }
}