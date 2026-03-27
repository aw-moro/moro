package com.aceward.usermanagement.dao.dto;

import java.util.List;

import lombok.Data;

/**
 * ユーザ検索条件DTO。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Data
public class ItemSearchDto {
    /** 品目コード */
    private String itemCode;
    /** 品名 */
    private String item;
    /** ページ番号 */
    private Long page;
    /** 件数 */
    private Long size;
}
