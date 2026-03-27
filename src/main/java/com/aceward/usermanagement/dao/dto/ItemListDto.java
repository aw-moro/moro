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
 * 品目一覧DTO。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Data
public class ItemListDto implements Serializable {

    /**
     * 品目ID。<br>
     * MyBatis経由だとUUID型への変換に対応してないようなので、String型で受け取る。
     */
    private String itemId;

    /** 品名 */
    private String item;

    /** 品目コード */
    private String itemCode;

    /** 単価 */
    private String unitPrice;

    /** 数量 */
    private String quantity;

    /** 単位 */
    private String unit;

    /** 品目詳細 */
    private String itemDetail;

    /** 消費税 */
    private String salesTax;

    /** 源泉徴収 */
    private Integer withholdingTax;

    /** 削除フラグ */
    private String deleteFlg;

    // /**
    //  * 都道府県名を取得する。
    //  * 
    //  * @return 都道府県名
    //  */
    // public String getPrefName() {
    //     return PrefCdEnum.codeOf(prefCd).getName();
    // }

    // /**
    //  * 表示用の性別を取得する。
    //  * 
    //  * @return 表示用の性別
    //  */
    // public String getGenderName() {
    //     if (gender == null) {
    //         return "";
    //     }
    //     return GenderEnum.codeOf(String.valueOf(gender)).getName();
    // }

    // /**
    //  * 付加情報を「、」で連結した文字列を取得する。
    //  * 
    //  * @return 付加情報
    //  */
    // public String getAddition() {
    //     if (StringUtils.isEmpty(additions)) {
    //         return "";
    //     }
    //     String[] arrays = additions.split(",");

    //     return Arrays.stream(arrays).map(e -> AdditionEnum.codeOf(e).getName())
    //             .collect(Collectors.joining("、"));
    // }
}
