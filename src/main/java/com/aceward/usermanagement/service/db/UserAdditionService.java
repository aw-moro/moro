package com.aceward.usermanagement.service.db;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aceward.usermanagement.dao.domain.UserAddition;
import com.aceward.usermanagement.dao.repository.UserAdditionRepository;

/**
 * user_additions テーブルのDB操作を行うためのサービス。<br>
 * トランザクションは呼び出し元で管理すること。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class UserAdditionService {

    /** EntityManager */
    @Autowired private EntityManager entityManager;
    /** ユーザ付加情報リポジトリ */
    @Autowired private UserAdditionRepository repository;

    /**
     * PKに紐づく一意な UserAddition を取得する。
     * 
     * @param id ID
     * @return UserAddition
     */
    public Optional<UserAddition> findById(UUID id) {
        return repository.findById(id);
    }

    /**
     * UserAddition テーブルに対して登録/更新を行う。
     * 
     * @param userAddition UserAdditionのdomain
     * @return 登録/更新後のUserAddition
     */
    public Optional<UserAddition> saveAndFlush(UserAddition userAddition) {
        UserAddition result = repository.saveAndFlush(userAddition);
        // 管理状態のEntityを、DBと同期し、最新化する。
        entityManager.refresh(result);
        return Optional.of(result);
    }

    /**
     * ユーザIDをもとに付加情報リストを取得する。
     * 
     * @param userId ユーザID
     * @return 付加情報リスト
     */
    public List<UserAddition> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    /**
     * ユーザIDに紐づく付加情報レコードすべて物理削除する。
     * 
     * @param userId ユーザID
     * @return 削除件数
     */
    public int deleteByUserId(UUID userId) {
        int count = repository.deleteByUserId(userId);
        // 削除処理を反映させるため、強制flushを実行する
        entityManager.flush();
        return count;
    }
}