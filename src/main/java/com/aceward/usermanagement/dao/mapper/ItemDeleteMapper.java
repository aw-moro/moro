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
public interface ItemDeleteMapper {

    /**
     * 指定したIDの品目の削除フラグを1にする。
     * 
     * @param itemId 品目ID
     */
    void deleteById(String itemId);

}
