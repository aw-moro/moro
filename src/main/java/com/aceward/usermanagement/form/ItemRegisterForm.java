package com.aceward.usermanagement.form;

import java.util.List;
import java.util.UUID;

import lombok.Data;

/**
 * 品目登録フォーム。<br>
 * 品目更新画面と兼用する。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Data
public class ItemRegisterForm {

    /** 品目ID **/
    private UUID itemId;

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

    /** 楽観ロック用更新日時（ミリ秒） */
    private String updatedAtMillisecond;

}
