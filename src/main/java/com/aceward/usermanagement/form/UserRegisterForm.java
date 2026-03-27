package com.aceward.usermanagement.form;

import java.util.List;

import lombok.Data;

/**
 * ユーザ登録フォーム。<br>
 * ユーザ更新画面と兼用する。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Data
public class UserRegisterForm {
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
    /** 付加情報 */
    private List<String> additions;
    /** 備考 */
    private String note;
    /** 楽観ロック用更新日時（ミリ秒） */
    private String updatedAtMillisecond;
}
