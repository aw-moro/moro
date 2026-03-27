package com.aceward.usermanagement.dao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aceward.usermanagement.dao.domain.UserDetail;

/**
 * user_details テーブルの Repository クラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, UUID> {

    /**
     * ユーザIDをもとにユーザ詳細を取得する。
     * 
     * @param userId ユーザID
     * @return ユーザ詳細
     */
    UserDetail findByUserId(UUID userId);
}
