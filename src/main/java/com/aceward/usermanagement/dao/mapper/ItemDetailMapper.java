package com.aceward.usermanagement.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aceward.usermanagement.dao.dto.ItemListDto;
import com.aceward.usermanagement.dao.dto.ItemSearchDto;

/**
 * item_details テーブルの Mapper クラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Mapper
public interface ItemDetailMapper {

    /**
     * 削除済み品目も含めた品目一覧件数を取得する。
     * 
     * @param dto 品目検索条件DTO
     * @return 品目一覧件数
     */
    int countByItemSearchDto(ItemSearchDto dto);

    /**
     * 削除済み品目も含めた品目一覧を取得する。
     * 
     * @param dto 品目検索条件DTO
     * @return 品目一覧
     */
    List<ItemListDto> findByItemSearchDto(ItemSearchDto dto);

}
