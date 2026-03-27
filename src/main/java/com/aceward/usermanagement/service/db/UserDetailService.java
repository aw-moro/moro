package com.aceward.usermanagement.service.db;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aceward.usermanagement.dao.domain.UserDetail;
import com.aceward.usermanagement.dao.dto.UserListDto;
import com.aceward.usermanagement.dao.dto.UserSearchDto;
import com.aceward.usermanagement.dao.mapper.UserDetailMapper;
import com.aceward.usermanagement.dao.repository.UserDetailRepository;

/**
 * user_details テーブルのDB操作を行うためのサービス。<br>
 * トランザクションは呼び出し元で管理すること。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class UserDetailService {

    /** EntityManager */
    @Autowired private EntityManager entityManager;
    /** ユーザ詳細リポジトリ */
    @Autowired private UserDetailRepository repository;
    /** ユーザ詳細マッパー */
    @Autowired private UserDetailMapper mapper;

    /**
     * PKに紐づく一意な UserDetail を取得する。
     * 
     * @param id ID
     * @return UserDetail
     */
    public Optional<UserDetail> findById(UUID id) {
        return repository.findById(id);
    }

    /**
     * UserDetail テーブルに対して登録/更新を行う。
     * 
     * @param userDetail UserDetailのdomain
     * @return 登録/更新後のUserDetail
     */
    public Optional<UserDetail> saveAndFlush(UserDetail userDetail) {
        UserDetail result = repository.saveAndFlush(userDetail);
        // 管理状態のEntityを、DBと同期し、最新化する。
        entityManager.refresh(result);
        return Optional.of(result);
    }

    /**
     * ユーザIDをもとにユーザ詳細を取得する。
     * 
     * @param userId ユーザID
     * @return ユーザ詳細
     */
    public Optional<UserDetail> findByUserId(UUID userId) {
        return Optional.ofNullable(repository.findByUserId(userId));
    }

    /**
     * 削除済みユーザも含めたユーザ一覧件数を取得する。
     * 
     * @param dto ユーザ検索条件DTO
     * @return ユーザ一覧件数
     */
    public int countByUserSearchDto(UserSearchDto dto) {
        return mapper.countByUserSearchDto(dto);
    }

    /**
     * 削除済みユーザも含めたユーザ一覧を取得する。
     * 
     * @param dto ユーザ検索条件DTO
     * @return ユーザ一覧
     */
    public List<UserListDto> findByUserSearchDto(UserSearchDto dto) {
        return mapper.findByUserSearchDto(dto);
    }
}