package com.aceward.usermanagement.dao.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * user_details テーブルの domain クラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Data
@Entity
@Table(name = "user_details")
public class UserDetail implements Serializable {

    /** ID */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /** ユーザID */
    @Column(name = "user_id")
    private UUID userId;

    /** ユーザ名 */
    @Column(name = "user_name")
    private String userName;

    /** ユーザ名カナ */
    @Column(name = "user_name_kana")
    private String userNameKana;

    /** メールアドレス */
    @Column(name = "mailaddress")
    private String mailaddress;

    /** 郵便番号 */
    @Column(name = "zip_cd")
    private String zipCd;

    /** 都道府県コード */
    @Column(name = "pref_cd")
    private String prefCd;

    /** 住所 */
    @Column(name = "address")
    private String address;

    /** 連絡先 */
    @Column(name = "tel")
    private String tel;

    /** 生年月日 */
    @Column(name = "birthday")
    private String birthday;

    /** 性別 */
    @Column(name = "gender")
    private Integer gender;

    /** 備考 */
    @Column(name = "note")
    private String note;
}
