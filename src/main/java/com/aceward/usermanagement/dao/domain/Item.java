package com.aceward.usermanagement.dao.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * item_mst テーブルの domain クラス。
 * 
 * @author s-moro
 * @version 1.0.0
 */
@Data
@Entity
@Table(name = "item_mst")
public class Item implements Serializable {

    /** ID */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "item_id", updatable = false, nullable = false)
    private UUID itemId;

    /** 品目 */
    @Column(name = "item")
    private String item;

    /** 品目コード */
    @Column(name = "item_code")
    private String itemCode;

    /** 単価 */
    @Column(name = "unit_price")
    private Integer unitPrice;

    /** 数量 */
    @Column(name = "quantity")
    private Integer quantity;

    /** 単位 */
    @Column(name = "unit")
    private String unit;

    /** 品目詳細 */
    @Column(name = "item_detail")
    private String itemDetail;

    /** 消費税コード */
    @Column(name = "sales_tax", columnDefinition = "bpchar")
    private String salesTax;

    /** 源泉徴収 */
    @Column(name = "withholding_tax")
    private Integer withholdingTax;

    /** 更新日時 */
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    /** 更新者スタッフID */
    @Column(name = "updated_staff_id")
    private UUID updatedStaffId;

    /** 削除フラグ */
    @Column(name = "delete_flg", columnDefinition = "bpchar")
    private Integer deleteFlg = 0;
}
