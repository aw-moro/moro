package com.aceward.usermanagement.service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aceward.usermanagement.bean.ClockConfig;
import com.aceward.usermanagement.constant.Constants;
import com.aceward.usermanagement.dao.domain.User;
import com.aceward.usermanagement.dao.domain.UserAddition;
import com.aceward.usermanagement.dao.domain.UserDetail;
import com.aceward.usermanagement.enums.GenderEnum;
import com.aceward.usermanagement.form.UserRegisterForm;
import com.aceward.usermanagement.service.db.UserAdditionService;
import com.aceward.usermanagement.service.db.UserDetailService;
import com.aceward.usermanagement.service.db.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * ユーザ登録サービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Slf4j
@Service
@Transactional
public class UserRegisterService {

    /** ClockConfig */
    @Autowired
    private ClockConfig clockConfig;
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
     * ユーザ登録処理を実行する。
     * 
     * @param form ユーザ登録フォーム
     * @param ユーザコード
     */
    public String execute(UserRegisterForm form) {
        // システム日時
        final Timestamp now = new Timestamp(clockConfig.clock().instant().toEpochMilli());

        // ユーザ登録
        User user = new User();
        user.setCreatedAt(now);
        user.setCreatedStaffId(Constants.STAFF_ID);
        user.setUpdatedAt(now);
        user.setUpdatedStaffId(Constants.STAFF_ID);
        Optional<User> optionalUser = userService.saveAndFlush(user);
        User resultUser = optionalUser.get();

        UUID userId = resultUser.getId();
        String userCd = resultUser.getUserCd();

        // ユーザ詳細登録
        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(userId);
        userDetail.setUserName(form.getUserName());
        userDetail.setUserNameKana(form.getUserNameKana());
        userDetail.setMailaddress(form.getMailaddress());
        userDetail.setZipCd(form.getZipCd());
        userDetail.setPrefCd(form.getPrefCd());
        userDetail.setAddress(form.getAddress());
        userDetail.setTel(form.getTel());
        userDetail.setBirthday(form.getBirthday());
        if (StringUtils.isNotEmpty(form.getGender())) {
            userDetail.setGender(GenderEnum.codeOf(form.getGender()).getCodeAsInt());
        }
        userDetail.setNote(form.getNote());
        userDetailService.saveAndFlush(userDetail);

        // ユーザ付加情報登録
        if (CollectionUtils.isNotEmpty(form.getAdditions())) {
            for (String addition : form.getAdditions()) {
                UserAddition userAddition = new UserAddition();
                userAddition.setUserId(userId);
                userAddition.setAddition(addition);
                userAdditionService.saveAndFlush(userAddition);
            }
        }

        // 登録完了のログ出力
        log.info("ユーザ登録が完了しました。 userId={}, userCd={}, createdAt={}", userId, userCd, now);

        return userCd;
    }
}
