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
 * user_additions テーブルの domain クラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Data
@Entity
@Table(name = "user_additions")
public class UserAddition implements Serializable {

    /** ID */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /** ユーザID */
    @Column(name = "user_id")
    private UUID userId;

    /** 付加情報 */
    @Column(name = "addition")
    private String addition;
}
