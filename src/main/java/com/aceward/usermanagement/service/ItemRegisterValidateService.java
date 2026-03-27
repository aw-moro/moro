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
import com.aceward.usermanagement.form.ItemRegisterForm;
import com.aceward.usermanagement.util.ValidateUtils;

/**
 * 品目登録バリデーションサービス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Service
public class ItemRegisterValidateService {

    /** MessageSource */
    @Autowired
    private MessageSource messageSource;

    /**
     * 品目登録処理を実行する。<br>
     * 入力チェック後、入力値が正常な場合は品目登録処理を行う。<br>
     * 入力エラーの場合は処理を中断し、エラーメッセージリストを返却する。
     * 
     * @param form 品目登録フォーム
     * @return エラーメッセージリスト（正常時は空のリスト）
     */
    public List<String> execute(ItemRegisterForm form) {
        List<String> messageList = new ArrayList<>();

        messageList.addAll(checkItem(form.getItem()));
        messageList.addAll(checkItemCode(form.getItemCode()));
        messageList.addAll(checkUnitPrice(form.getUnitPrice()));
        messageList.addAll(checkQuantity(form.getQuantity()));
        messageList.addAll(checkUnit(form.getUnit()));
        messageList.addAll(checkItemDetail(form.getItemDetail()));

        return messageList;
    }

    /**
     * 品目の入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 品目
     * @return エラーメッセージリスト
     */
    private List<String> checkItem(String value) {
        final String name = "品目";

        List<String> messageList = new ArrayList<>();

        if (StringUtils.isEmpty(value)) {
            messageList.add(getMessage("required.error", new String[] { name }));
            return messageList;
        }

        if (ValidateUtils.isLengthOver(value, 900)) {
            messageList.add(getMessage("length.over.error", new String[] { name, "900" }));
        }
        return messageList;
    }

    /**
     * 品目コードの入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 品目コード
     * @return エラーメッセージリスト
     */
    private List<String> checkItemCode(String value) {
        final String name = "品目コード";

        List<String> messageList = new ArrayList<>();

        if (ValidateUtils.isLengthOver(value, 60)) {
            messageList.add(getMessage("length.over.error", new String[] { name, "60" }));
        }
        return messageList;
    }

    /**
     * 単価の入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 単価
     * @return エラーメッセージリスト
     */
    private List<String> checkUnitPrice(String value) {
        final String name = "単価";

        List<String> messageList = new ArrayList<>();

        if (ValidateUtils.isNumCheck(value, 11)) {
            messageList.add(getMessage("length.over.error", new String[] { name, "11" }));
        }
        return messageList;
    }

    /**
     * 数量の入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 数量
     * @return エラーメッセージリスト
     */
    private List<String> checkQuantity(String value) {
        final String name = "数量";

        List<String> messageList = new ArrayList<>();

        if (ValidateUtils.isNumCheck(value, 11)) {
            messageList.add(getMessage("length.over.error", new String[] { name, "11" }));
        }
        return messageList;
    }

    /**
     * 単位の入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 単位
     * @return エラーメッセージリスト
     */
    private List<String> checkUnit(String value) {
        final String name = "単位";

        List<String> messageList = new ArrayList<>();

        if (ValidateUtils.isLengthOver(value, 40)) {
            messageList.add(getMessage("length.over.error", new String[] { name, "40" }));
        }
        return messageList;
    }

    /**
     * 品目詳細の入力チェックを行う。<br>
     * エラーなしの場合は空のリストを返却する。
     * 
     * @param value 品目詳細
     * @return エラーメッセージリスト
     */
    private List<String> checkItemDetail(String value) {
        final String name = "品目詳細";

        List<String> messageList = new ArrayList<>();

        if (ValidateUtils.isLengthOver(value, 400)) {
            messageList.add(getMessage("length.over.error", new String[] { name, "400" }));
        }
        return messageList;
    }

    /**
     * エラーメッセージを取得する。
     * 
     * @param messageKey メッセージキー
     * @param params     パラメータ
     * @return エラーメッセージ
     */
    private String getMessage(String messageKey, String[] params) {
        return messageSource.getMessage(messageKey, params, Locale.JAPAN);
    }
}
