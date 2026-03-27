package com.aceward.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aceward.usermanagement.dao.domain.User;
import com.aceward.usermanagement.dao.domain.UserAddition;
import com.aceward.usermanagement.dao.domain.UserDetail;
import com.aceward.usermanagement.enums.AdditionEnum;
import com.aceward.usermanagement.form.UserDetailForm;
import com.aceward.usermanagement.service.db.UserAdditionService;
import com.aceward.usermanagement.service.db.UserDetailService;
import com.aceward.usermanagement.service.db.UserService;

/**
 * ユーザ詳細画面初期表示サービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class UserDetailViewService {

    /** ユーザサービス */
    @Autowired
    private UserService userService;
    /** ユーザ詳細サービス */
    @Autowired
    private UserDetailService userDetailService;
    /** ユーザ付加情報サービス */
    @Autowired
    private UserAdditionService userAdditionService;

    /**
     * ユーザ詳細画面の初期表示情報を取得する。
     * 
     * @param userCd ユーザコード
     * @return ユーザ詳細フォーム
     * @throws NotFoundException データが存在しない場合
     */
    public UserDetailForm execute(String userCd) throws NotFoundException {
        // ユーザ
        Optional<User> optionalUser = userService.findByUserCd(userCd);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("ユーザ情報が登録されていません。userCd=" + userCd);
        }
        User user = optionalUser.get();

        // ユーザ詳細
        Optional<UserDetail> optionalUserDetail = userDetailService.findByUserId(user.getId());

        if (optionalUserDetail.isEmpty()) {
            throw new NotFoundException("ユーザ詳細が登録されていません。userCd=" + userCd);
        }
        UserDetail userDetail = optionalUserDetail.get();

        // ユーザ付加情報
        List<UserAddition> userAdditions = userAdditionService.findByUserId(user.getId());

        // 付加情報の項目名を「、」で連結したもの
        final String addition =
                userAdditions.stream().map(e -> AdditionEnum.codeOf(e.getAddition()).getName())
                        .collect(Collectors.joining("、"));

        UserDetailForm form = new UserDetailForm();
        form.setUserCd(user.getUserCd());
        form.setUserName(userDetail.getUserName());
        form.setUserNameKana(userDetail.getUserNameKana());
        form.setMailaddress(userDetail.getMailaddress());
        form.setZipCd(userDetail.getZipCd());
        form.setPrefCd(userDetail.getPrefCd());
        form.setAddress(userDetail.getAddress());
        form.setTel(userDetail.getTel());
        form.setBirthday(userDetail.getBirthday());
        form.setGender(String.valueOf(userDetail.getGender()));
        form.setAddition(addition);
        form.setNote(userDetail.getNote());

        return form;
    }
}
