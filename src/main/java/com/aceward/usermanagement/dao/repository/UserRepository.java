package com.aceward.usermanagement.dao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aceward.usermanagement.dao.domain.User;

/**
 * users テーブルの Repository クラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * ユーザコードをもとにユーザ情報を取得する。
     * 
     * @param userCd ユーザコード
     * @return ユーザ情報
     */
    User findByUserCd(String userCd);
}
