package com.aceward.usermanagement.service.db;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aceward.usermanagement.dao.domain.Item;
import com.aceward.usermanagement.dao.dto.ItemListDto;
import com.aceward.usermanagement.dao.dto.ItemSearchDto;
import com.aceward.usermanagement.dao.mapper.ItemDetailMapper;
import com.aceward.usermanagement.dao.repository.ItemRepository;

/**
 * item_details テーブルのDB操作を行うためのサービス。<br>
 * トランザクションは呼び出し元で管理すること。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class ItemDetailService {

    /** EntityManager */
    @Autowired private EntityManager entityManager;
    /** 品目詳細リポジトリ */
    @Autowired private ItemRepository repository;
    /** 品目詳細マッパー */
    @Autowired private ItemDetailMapper mapper;

    /**
     * PKに紐づく一意な ItemDetail を取得する。
     * 
     * @param id ID
     * @return ItemDetail
     */
    public Optional<Item> findById(UUID itemid) {
        return repository.findById(itemid);
    }

    /**
     * Item テーブルに対して登録/更新を行う。
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
     * 品目IDをもとに品目詳細を取得する。
     * 
     * @param itemId 品目ID
     * @return 品目詳細
     */
    public Optional<Item> findByItemId(UUID itemId) {
        return Optional.ofNullable(repository.findByItemId(itemId));
    }

    /**
     * 削除済み品目も含めた品目一覧件数を取得する。
     * 
     * @param dto 品目検索条件DTO
     * @return 品目一覧件数
     */
    public int countByItemSearchDto(ItemSearchDto dto) {
        return mapper.countByItemSearchDto(dto);
    }

    /**
     * 削除済み品目も含めた品目一覧を取得する。
     * 
     * @param dto 品目検索条件DTO
     * @return 品目一覧
     */
    public List<ItemListDto> findByItemSearchDto(ItemSearchDto dto) {
        return mapper.findByItemSearchDto(dto);
    }
}