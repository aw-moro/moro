package com.aceward.usermanagement.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aceward.usermanagement.dao.dto.UserListDto;
import com.aceward.usermanagement.dao.dto.UserSearchDto;

/**
 * user_details テーブルの Mapper クラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Mapper
public interface UserDetailMapper {

    /**
     * 削除済みユーザも含めたユーザ一覧件数を取得する。
     * 
     * @param dto ユーザ検索条件DTO
     * @return ユーザ一覧件数
     */
    int countByUserSearchDto(UserSearchDto dto);

    /**
     * 削除済みユーザも含めたユーザ一覧を取得する。
     * 
     * @param dto ユーザ検索条件DTO
     * @return ユーザ一覧
     */
    List<UserListDto> findByUserSearchDto(UserSearchDto dto);
}
