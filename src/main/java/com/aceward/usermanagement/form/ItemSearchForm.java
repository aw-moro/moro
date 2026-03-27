package com.aceward.usermanagement.form;

import java.util.List;

import lombok.Data;

/**
 * ユーザ検索条件フォーム。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Data
public class ItemSearchForm {
    
    /** 品目コード */
    private String itemCode;
    /** 品名 */
    private String item;

    /** ページ番号 */
    private Long page;
    /** 件数 */
    private Long size;
}
