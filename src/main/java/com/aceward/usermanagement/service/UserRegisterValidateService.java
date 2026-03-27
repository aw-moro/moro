package com.aceward.usermanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.aceward.usermanagement.enums.AdditionEnum;
import com.aceward.usermanagement.enums.GenderEnum;
import com.aceward.usermanagement.enums.PrefCdEnum;
import com.aceward.usermanagement.form.UserRegisterForm;
import com.aceward.usermanagement.util.ValidateUtils;

/**
 * ユーザ登録バリデーションサービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class UserRegisterValidateService {

    /** MessageSource */
    @Autowired
    private MessageSource messageSource;

    /**
     * ユーザ登録処理を実行する。<br>
     * 入力チェック後、入力値が正常な場合はユーザ登録処理を行う。<br>
     * 入力エラーの場合は処理を中断し、エラーメッセージリストを返却する。
     * 
     * @param form ユーザ登録フォーム
     * @return エラーメッセージリスト（正常時は空のリスト）
     */
    public List<String> execute(UserRegisterForm form) {
        List<String> messageList = new ArrayList<>();

        messageList.addAll(checkUserName(form.getUserName()));
        messageList.addAll(checkUserNameKana(form.getUserNameKana()));
        messageList.addAll(checkMailaddress(form.getMailaddress()));
        messageList.addAll(checkZipCd(form.getZipCd()));
        messageList.addAll(checkPrefCd(form.getPrefCd()));
        messageList.addAll(checkAddress(form.getAddress()));
        messageList.addAll(checkTel(form.getTel()));
        messageList.addAll(checkBirthday(form.getBirthday()));
        messageList.addAll(checkGender(form.getGender()));
        messageList.addAll(checkAdditions(form.getAdditions()));

        return messageList;
    }

    /**
     * ユーザ名の入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value ユーザ名
     * @return エラーメッセージリスト
     */
    private List<String> checkUserName(String value) {
        final String name = "ユーザ名";

        List<String> messageList = new ArrayList<>();

        if (StringUtils.isEmpty(value)) {
            messageList.add(getMessage("required.error", new String[] {name}));
            return messageList;
        }

        if (ValidateUtils.isLengthOver(value, 50)) {
            messageList.add(getMessage("length.over.error", new String[] {name, "50"}));
        }
        return messageList;
    }

    /**
     * ユーザ名カナの入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value ユーザ名カナ
     * @return エラーメッセージリスト
     */
    private List<String> checkUserNameKana(String value) {
        final String name = "ユーザ名カナ";

        List<String> messageList = new ArrayList<>();

        if (StringUtils.isEmpty(value)) {
            return messageList;
        }

        if (ValidateUtils.isLengthOver(value, 100)) {
            messageList.add(getMessage("length.over.error", new String[] {name, "100"}));
        }
        return messageList;
    }

    /**
     * メールアドレスの入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value メールアドレス
     * @return エラーメッセージリスト
     */
    private List<String> checkMailaddress(String value) {
        final String name = "メールアドレス";

        List<String> messageList = new ArrayList<>();

        if (StringUtils.isEmpty(value)) {
            messageList.add(getMessage("required.error", new String[] {name}));
            return messageList;
        }

        if (ValidateUtils.isLengthOver(value, 254)) {
            messageList.add(getMessage("length.over.error", new String[] {name, "254"}));
        }

        if (!ValidateUtils.isMailaddress(value)) {
            messageList.add(getMessage("illegal.mailaddress.error", null));
        }
        return messageList;
    }

    /**
     * 郵便番号の入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 郵便番号
     * @return エラーメッセージリスト
     */
    private List<String> checkZipCd(String value) {
        final String name = "郵便番号";

        List<String> messageList = new ArrayList<>();

        if (StringUtils.isEmpty(value)) {
            messageList.add(getMessage("required.error", new String[] {name}));
            return messageList;
        }

        if (!ValidateUtils.isLengthEquals(value, 7)) {
            messageList.add(getMessage("length.equals.error", new String[] {name, "7"}));
        }

        if (!ValidateUtils.isNumber(value)) {
            messageList.add(getMessage("number.format.error", new String[] {name}));
        }
        return messageList;
    }

    /**
     * 都道府県コードの入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 都道府県コード
     * @return エラーメッセージリスト
     */
    private List<String> checkPrefCd(String value) {
        final String name = "都道府県";

        List<String> messageList = new ArrayList<>();

        if (StringUtils.isEmpty(value)) {
            messageList.add(getMessage("required.error", new String[] {name}));
            return messageList;
        }

        try {
            PrefCdEnum.codeOf(value);
        } catch (IllegalArgumentException e) {
            messageList.add(getMessage("illegal.error", new String[] {name}));
        }
        return messageList;
    }

    /**
     * 住所の入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 住所
     * @return エラーメッセージリスト
     */
    private List<String> checkAddress(String value) {
        final String name = "住所";

        List<String> messageList = new ArrayList<>();

        if (StringUtils.isEmpty(value)) {
            messageList.add(getMessage("required.error", new String[] {name}));
            return messageList;
        }

        if (ValidateUtils.isLengthOver(value, 256)) {
            messageList.add(getMessage("length.over.error", new String[] {name, "256"}));
        }
        return messageList;
    }

    /**
     * 電話番号の入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 電話番号
     * @return エラーメッセージリスト
     */
    private List<String> checkTel(String value) {
        final String name = "電話番号";

        List<String> messageList = new ArrayList<>();

        if (StringUtils.isEmpty(value)) {
            messageList.add(getMessage("required.error", new String[] {name}));
            return messageList;
        }

        if (!ValidateUtils.isTel(value)) {
            messageList.add(getMessage("illegal.tel.error", null));
        }
        return messageList;
    }

    /**
     * 生年月日の入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 生年月日
     * @return エラーメッセージリスト
     */
    private List<String> checkBirthday(String value) {
        final String name = "生年月日";

        List<String> messageList = new ArrayList<>();

        if (StringUtils.isEmpty(value)) {
            messageList.add(getMessage("required.error", new String[] {name}));
            return messageList;
        }

        if (!ValidateUtils.isDate(value)) {
            messageList.add(getMessage("illegal.error", new String[] {name}));
        }
        return messageList;
    }

    /**
     * 性別コードの入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 性別コード
     * @return エラーメッセージリスト
     */
    private List<String> checkGender(String value) {
        final String name = "性別";

        List<String> messageList = new ArrayList<>();

        if (StringUtils.isEmpty(value)) {
            return messageList;
        }

        try {
            GenderEnum.codeOf(value);
        } catch (IllegalArgumentException e) {
            messageList.add(getMessage("illegal.error", new String[] {name}));
        }
        return messageList;
    }

    /**
     * 付加情報の入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 付加情報リスト
     * @return エラーメッセージリスト
     */
    private List<String> checkAdditions(List<String> values) {
        final String name = "付加情報";

        List<String> messageList = new ArrayList<>();

        if (CollectionUtils.isEmpty(values)) {
            return messageList;
        }

        try {
            // streamでやるとIllegalArgumentExceptionを握り潰されてしまうため、この形式でやる
            for (String value : values) {
                AdditionEnum.codeOf(value);
            }
        } catch (IllegalArgumentException e) {
            messageList.add(getMessage("illegal.error", new String[] {name}));
        }
        return messageList;
    }

    /**
     * エラーメッセージを取得する。
     * 
     * @param messageKey メッセージキー
     * @param params パラメータ
     * @return エラーメッセージ
     */
    private String getMessage(String messageKey, String[] params) {
        return messageSource.getMessage(messageKey, params, Locale.JAPAN);
    }
}
