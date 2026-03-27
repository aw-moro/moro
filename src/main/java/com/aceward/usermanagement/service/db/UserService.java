package com.aceward.usermanagement.service.db;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aceward.usermanagement.dao.domain.User;
import com.aceward.usermanagement.dao.repository.UserRepository;

/**
 * users テーブルのDB操作を行うためのサービス。<br>
 * トランザクションは呼び出し元で管理すること。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class UserService {

    /** EntityManager */
    @Autowired private EntityManager entityManager;
    /** ユーザリポジトリ */
    @Autowired private UserRepository repository;

    /**
     * PKに紐づく一意な User を取得する。
     * 
     * @param id ID
     * @return User
     */
    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    /**
     * users テーブルに対して登録/更新を行う。
     * 
     * @param user Userのdomain
     * @return 登録/更新後のUser
     */
    public Optional<User> saveAndFlush(User user) {
        User result = repository.saveAndFlush(user);
        // 管理状態のEntityを、DBと同期し、最新化する。
        entityManager.refresh(result);
        return Optional.of(result);
    }

    /**
     * ユーザコードをもとにユーザ情報を取得する。
     * 
     * @param userCd ユーザコード
     * @return ユーザ情報
     */
    public Optional<User> findByUserCd(String userCd) {
        return Optional.ofNullable(repository.findByUserCd(userCd));
    }
}