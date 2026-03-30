package com.aceward.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aceward.usermanagement.dao.domain.Item;
import com.aceward.usermanagement.dao.mapper.ItemDeleteMapper;
// import com.aceward.usermanagement.dao.domain.ItemAddition;
// import com.aceward.usermanagement.dao.domain.ItemDetail;
import com.aceward.usermanagement.enums.AdditionEnum;
import com.aceward.usermanagement.form.ItemDetailForm;
// import com.aceward.usermanagement.service.db.ItemAdditionService;
import com.aceward.usermanagement.service.db.ItemDetailService;
import com.aceward.usermanagement.service.db.ItemService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 品目詳細画面初期表示サービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class ItemDeleteService {

    /** 品目削除サービス */
    @Autowired
    private ItemDeleteMapper itemDeleteMapper;

    @Autowired
    private ItemService itemService;

    @Transactional
    public void execute(UUID itemID) throws NotFoundException {
        Optional<Item> optionalItem = itemService.findById(itemID);

        // 「存在しない」または「すでに削除フラグが1」なら例外を投げる
        if (optionalItem.isEmpty() || optionalItem.get().getDeleteFlg() == 1) {
            throw new NotFoundException("削除対象の品目情報は既に削除されているか、存在しません。");
        }

        // チェックを通過したら削除実行
        itemDeleteMapper.deleteById(itemID.toString());
    }
}
