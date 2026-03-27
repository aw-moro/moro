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
public class UserSearchForm {
    /** ユーザ名 */
    private String userName;
    /** メールアドレス */
    private String mailaddress;
    /** 都道府県コード */
    private String prefCd;
    /** 性別 */
    private String gender;
    /** 付加情報 */
    private List<String> additions;
    /** ページ番号 */
    private Long page;
    /** 件数 */
    private Long size;
}
