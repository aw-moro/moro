package com.aceward.usermanagement.dao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aceward.usermanagement.dao.domain.Item;

/**
 * item_mst テーブルの Repository クラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    /**
     * 品目IDをもとに品目詳細を取得する。
     * 
     * @param itemId 品目ID
     * @return 品目詳細
     */
    Item findByItemId(UUID itemId);

    /**
     * 品目コードをもとに品目情報を取得する。
     * 
     * @param itemCode 品目コード
     * @return 品目情報
     */
    Item findByItemCode(String itemCode);
}
