package com.aceward.usermanagement.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aceward.usermanagement.bean.ClockConfig;
import com.aceward.usermanagement.constant.Constants;
import com.aceward.usermanagement.dao.domain.User;
import com.aceward.usermanagement.dao.domain.UserAddition;
import com.aceward.usermanagement.dao.domain.UserDetail;
import com.aceward.usermanagement.enums.GenderEnum;
import com.aceward.usermanagement.exception.OptimisticErrorException;
import com.aceward.usermanagement.form.UserRegisterForm;
import com.aceward.usermanagement.service.db.UserAdditionService;
import com.aceward.usermanagement.service.db.UserDetailService;
import com.aceward.usermanagement.service.db.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * ユーザ更新サービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Slf4j
@Service
@Transactional
public class UserUpdateService {

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
     * ユーザ更新処理を実行する。
     * 
     * @param form ユーザ登録フォーム
     * @return ユーザコード
     * @throws NotFoundException データが存在しない場合
     * @throws OptimisticErrorException 楽観的ロックエラーの場合
     */
    public String execute(UserRegisterForm form)
            throws NotFoundException, OptimisticErrorException {
        // 楽観ロック用更新日時
        final Timestamp updatedAt = new Timestamp(Long.parseLong(form.getUpdatedAtMillisecond()));

        // システム日時
        final Timestamp now = new Timestamp(clockConfig.clock().instant().toEpochMilli());

        // ユーザ更新
        Optional<User> optionalUser = userService.findByUserCd(form.getUserCd());

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("ユーザ情報が登録されていません。userCd=" + form.getUserCd());
        }
        User user = optionalUser.get();

        // 楽観ロックチェック
        if (!user.getUpdatedAt().equals(updatedAt)) {
            throw new OptimisticErrorException("ユーザ情報が既に更新されています。");
        }

        user.setUpdatedAt(now);
        user.setUpdatedStaffId(Constants.STAFF_ID);
        userService.saveAndFlush(user);

        // ユーザ詳細更新
        Optional<UserDetail> optionalUserDetail = userDetailService.findByUserId(user.getId());

        if (optionalUserDetail.isEmpty()) {
            throw new NotFoundException("ユーザ詳細が登録されていません。userCd=" + form.getUserCd());
        }
        UserDetail userDetail = optionalUserDetail.get();
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
        } else {
            userDetail.setGender(null);
        }
        userDetail.setNote(form.getNote());
        userDetailService.saveAndFlush(userDetail);

        // ユーザ付加情報は選択有無に関わらず、登録済みのレコードを全削除しておく
        userAdditionService.deleteByUserId(user.getId());

        // ユーザ付加情報更新（再登録）
        if (CollectionUtils.isNotEmpty(form.getAdditions())) {
            // 付加情報が選択されている場合
            for (String addition : form.getAdditions()) {
                UserAddition userAddition = new UserAddition();
                userAddition.setUserId(user.getId());
                userAddition.setAddition(addition);
                userAdditionService.saveAndFlush(userAddition);
            }
        }

        // 更新完了のログ出力
        log.info("ユーザ更新が完了しました。 userId={}, userCd={}, updatedAt={}", user.getId(), user.getUserCd(),
                now);

        return user.getUserCd();
    }
}
