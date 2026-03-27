package com.aceward.usermanagement.dao.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * user テーブルの domain クラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    /** ID */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /** ユーザコード */
    @ColumnTransformer(
            write = "coalesce(?, lpad(nextval('user_cd_seq')::character varying, 10, '0'))",
            read = "user_cd")
    @Column(name = "user_cd")
    private String userCd;

    /** 登録日時 */
    @Column(name = "created_at")
    private Timestamp createdAt;

    /** 登録者スタッフID */
    @Column(name = "created_staff_id")
    private UUID createdStaffId;

    /** 更新日時 */
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    /** 更新者スタッフID */
    @Column(name = "updated_staff_id")
    private UUID updatedStaffId;

    /** 削除日時 */
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    /** 削除者スタッフID */
    @Column(name = "deleted_staff_id")
    private UUID deletedStaffId;
}
