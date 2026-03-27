package com.aceward.usermanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aceward.usermanagement.dao.dto.UserListDto;
import com.aceward.usermanagement.dao.dto.UserSearchDto;
import com.aceward.usermanagement.form.UserSearchForm;
import com.aceward.usermanagement.service.db.UserDetailService;

/**
 * ユーザ一覧画面検索サービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class UserLisrSearchService {

    /** ユーザ詳細サービス */
    @Autowired
    private UserDetailService userDetailService;

    /**
     * ユーザ一覧画面の検索処理を実行する。
     * 
     * @param form ユーザ検索フォーム
     * @param pageable Pageable
     * @return 検索結果を含めたページ情報
     */
    public Page<UserListDto> execute(UserSearchForm form, Pageable pageable) {
        // formからdtoへ詰め替え
        UserSearchDto dto = new UserSearchDto();
        dto.setUserName(form.getUserName());
        dto.setMailaddress(form.getMailaddress());
        dto.setPrefCd(form.getPrefCd());
        dto.setGender(form.getGender());
        dto.setAdditions(form.getAdditions());
        dto.setPage(form.getPage());
        dto.setSize(form.getSize());

        // 件数取得
        int count = userDetailService.countByUserSearchDto(dto);

        if (count == 0) {
            return new PageImpl<>(new ArrayList<>(), pageable, count);
        }
        // 検索処理
        List<UserListDto> userList = userDetailService.findByUserSearchDto(dto);

        return new PageImpl<>(userList, pageable, count);
    }
}
