package com.aceward.usermanagement.service.db;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aceward.usermanagement.dao.domain.Item;
import com.aceward.usermanagement.dao.repository.ItemRepository;

/**
 * item_mst テーブルのDB操作を行うためのサービス。<br>
 * トランザクションは呼び出し元で管理すること。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class ItemService {

    /** EntityManager */
    @Autowired private EntityManager entityManager;
    /** 品目リポジトリ */
    @Autowired private ItemRepository repository;

    /**
     * PKに紐づく一意な Item を取得する。
     * 
     * @param id ID
     * @return Item
     */
    public Optional<Item> findById(UUID id) {
        return repository.findById(id);
    }

    /**
     * item_mst テーブルに対して登録/更新を行う。
     * 
     * @param item Itemのdomain
     * @return 登録/更新後のItem
     */
    public Optional<Item> saveAndFlush(Item item) {
        Item result = repository.saveAndFlush(item);
        // 管理状態のEntityを、DBと同期し、最新化する。
        entityManager.refresh(result);
        return Optional.of(result);
    }

    /**
     * 品目コードをもとに品目情報を取得する。
     * 
     * @param itemCode 品目コード
     * @return 品目情報
     */
    public Optional<Item> findByItemCode(String itemCode) {
        return Optional.ofNullable(repository.findByItemCode(itemCode));
    }
}