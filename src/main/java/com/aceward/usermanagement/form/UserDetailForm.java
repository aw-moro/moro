package com.aceward.usermanagement.form;

import lombok.Data;

/**
 * ユーザ詳細フォーム。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Data
public class UserDetailForm {
    /** ユーザコード */
    private String userCd;
    /** ユーザ名 */
    private String userName;
    /** ユーザ名カナ */
    private String userNameKana;
    /** メールアドレス */
    private String mailaddress;
    /** 郵便番号 */
    private String zipCd;
    /** 都道府県コード */
    private String prefCd;
    /** 住所 */
    private String address;
    /** 連絡先 */
    private String tel;
    /** 生年月日 */
    private String birthday;
    /** 性別 */
    private String gender;
    /** 付加情報（項目名を読点で連結したもの） */
    private String addition;
    /** 備考 */
    private String note;
}
