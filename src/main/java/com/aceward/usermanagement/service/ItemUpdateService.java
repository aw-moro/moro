package com.aceward.usermanagement.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aceward.usermanagement.bean.ClockConfig;
import com.aceward.usermanagement.constant.Constants;
import com.aceward.usermanagement.dao.domain.Item;
// import com.aceward.usermanagement.dao.domain.ItemAddition;
// import com.aceward.usermanagement.dao.domain.ItemDetail;
import com.aceward.usermanagement.enums.GenderEnum;
import com.aceward.usermanagement.exception.OptimisticErrorException;
import com.aceward.usermanagement.form.ItemRegisterForm;
// import com.aceward.usermanagement.service.db.ItemAdditionService;
import com.aceward.usermanagement.service.db.ItemDetailService;
import com.aceward.usermanagement.service.db.ItemService;

import lombok.extern.slf4j.Slf4j;

/**
 * 品目更新サービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Slf4j
@Service
@Transactional
public class ItemUpdateService {

    /** ClockConfig */
    @Autowired
    private ClockConfig clockConfig;
    /** 品目サービス */
    @Autowired
    private ItemService itemService;
    /** 品目詳細サービス */
    // @Autowired
    // private ItemDetailService itemDetailService;
    /** 品目付加情報サービス */
    // @Autowired
    // private ItemAdditionService itemAdditionService;

    /**
     * 品目更新処理を実行する。
     * 
     * @param form 品目登録フォーム
     * @return 品目コード
     * @throws NotFoundException データが存在しない場合
     * @throws OptimisticErrorException 楽観的ロックエラーの場合
     */
    public String execute(ItemRegisterForm form)
            throws NotFoundException, OptimisticErrorException {
        // 楽観ロック用更新日時
        final Timestamp updatedAt = new Timestamp(Long.parseLong(form.getUpdatedAtMillisecond()));

        // システム日時
        final Timestamp now = new Timestamp(clockConfig.clock().instant().toEpochMilli());

        // 品目更新
        Optional<Item> optionalItem = itemService.findById(form.getItemId());

        if (optionalItem.isEmpty()) {
            throw new NotFoundException("品目情報が登録されていません。itemId=" + form.getItemId());
        }
        Item item = optionalItem.get();

        // 楽観ロックチェック
        if (!item.getUpdatedAt().equals(updatedAt)) {
            throw new OptimisticErrorException("品目情報が既に更新されています。");
        }

        item.setUpdatedAt(now);
        item.setUpdatedStaffId(Constants.STAFF_ID);
        // itemService.saveAndFlush(item);

        // // 品目詳細更新
        // Optional<Item> optionalItem = itemDetailService.findByItemId(item.getItemId());

        // if (optionalItem.isEmpty()) {
        //     throw new NotFoundException("品目詳細が登録されていません。itemId=" + form.getItemId());
        // }
        // Item item = optionalItem.get();

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
        item.setDeleteFlg(0);
        itemService.saveAndFlush(item);


        // // 品目付加情報は選択有無に関わらず、登録済みのレコードを全削除しておく
        // itemAdditionService.deleteByItemId(item.getId());

        // // 品目付加情報更新（再登録）
        // if (CollectionUtils.isNotEmpty(form.getAdditions())) {
        //     // 付加情報が選択されている場合
        //     for (String addition : form.getAdditions()) {
        //         ItemAddition itemAddition = new ItemAddition();
        //         itemAddition.setItemId(item.getId());
        //         itemAddition.setAddition(addition);
        //         itemAdditionService.saveAndFlush(itemAddition);
        //     }
        // }

        // 更新完了のログ出力
        log.info("ユーザ更新が完了しました。 itemId={}, itemCd={}, updatedAt={}", item.getItemId(), item.getItemCode(),
                now);

        return item.getItemCode();
    }
}
