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
import com.aceward.usermanagement.form.UserRegisterForm;
import com.aceward.usermanagement.service.db.UserAdditionService;
import com.aceward.usermanagement.service.db.UserDetailService;
import com.aceward.usermanagement.service.db.UserService;

/**
 * ユーザ更新画面初期表示サービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class UserUpdateViewService {

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
     * ユーザ更新画面の初期表示情報を取得する。
     * 
     * @param userCd ユーザコード
     * @return ユーザ登録フォーム
     * @throws NotFoundException データが存在しない場合
     */
    public UserRegisterForm execute(String userCd) throws NotFoundException {
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
        List<String> additions = userAdditionService.findByUserId(user.getId()).stream()
                .map(UserAddition::getAddition).collect(Collectors.toList());

        UserRegisterForm form = new UserRegisterForm();
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
        form.setNote(userDetail.getNote());
        form.setAdditions(additions);
        form.setUpdatedAtMillisecond(String.valueOf(user.getUpdatedAt().getTime()));

        return form;
    }
}
