package com.aceward.usermanagement.dao.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.aceward.usermanagement.enums.AdditionEnum;
import com.aceward.usermanagement.enums.GenderEnum;
import com.aceward.usermanagement.enums.PrefCdEnum;

import lombok.Data;

/**
 * ユーザ一覧DTO。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Data
public class UserListDto implements Serializable {

    /**
     * ユーザID。<br>
     * MyBatis経由だとUUID型への変換に対応してないようなので、String型で受け取る。
     */
    private String userId;

    /** ユーザコード */
    private String userCd;

    /** ユーザ名 */
    private String userName;

    /** メールアドレス */
    private String mailaddress;

    /** 都道府県コード */
    private String prefCd;

    /** 住所 */
    private String address;

    /** 性別 */
    private Integer gender;

    /** 備考 */
    private String note;

    /** 付加情報 */
    private String additions;

    /**
     * 都道府県名を取得する。
     * 
     * @return 都道府県名
     */
    public String getPrefName() {
        return PrefCdEnum.codeOf(prefCd).getName();
    }

    /**
     * 表示用の性別を取得する。
     * 
     * @return 表示用の性別
     */
    public String getGenderName() {
        if (gender == null) {
            return "";
        }
        return GenderEnum.codeOf(String.valueOf(gender)).getName();
    }

    /**
     * 付加情報を「、」で連結した文字列を取得する。
     * 
     * @return 付加情報
     */
    public String getAddition() {
        if (StringUtils.isEmpty(additions)) {
            return "";
        }
        String[] arrays = additions.split(",");

        return Arrays.stream(arrays).map(e -> AdditionEnum.codeOf(e).getName())
                .collect(Collectors.joining("、"));
    }
}
