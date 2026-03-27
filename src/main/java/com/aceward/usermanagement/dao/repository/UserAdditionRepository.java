package com.aceward.usermanagement.dao.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aceward.usermanagement.dao.domain.UserAddition;

/**
 * user_additions テーブルの Repository クラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Repository
public interface UserAdditionRepository extends JpaRepository<UserAddition, UUID> {

    /**
     * ユーザIDをもとに付加情報リストを取得する。
     * 
     * @param userId ユーザID
     * @return 付加情報リスト
     */
    List<UserAddition> findByUserId(UUID userId);

    /**
     * ユーザIDに紐づく付加情報レコードすべて物理削除する。
     * 
     * @param userId ユーザID
     * @return 削除件数
     */
    int deleteByUserId(UUID userId);
}
