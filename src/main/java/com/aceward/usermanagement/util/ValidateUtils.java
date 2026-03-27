package com.aceward.usermanagement.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * バリデーション共通ユーティリティー。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
public class ValidateUtils {

    /** メールアドレスの正規表現 */
    private static final Pattern REGEX_MAIL_ADDRESS = Pattern
            .compile("^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\._-]+)+$");

    /** 電話番号の正規表現 */
    private static final Pattern REGEX_TEL = Pattern.compile("^0[0-9]{9,12}");

    /**
     * 桁数超過チェック。<br>
     * 検証値がnull、またはブランクの場合はfalseを返す。
     * 
     * @param value 検証値
     * @param max   最大桁数
     * @return true：桁数超過、false：桁数以内
     */
    public static boolean isLengthOver(String value, int max) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return value.length() > max;
    }

    /**
     * 桁数一致チェック。<br>
     * 検証値がnull、またはブランクの場合はfalseを返す。
     * 
     * @param value 検証値
     * @param max   最大桁数
     * @return true：桁数と一致、false：その他
     */
    public static boolean isLengthEquals(String value, int size) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return value.length() == size;
    }

    /**
     * 桁数範囲チェック。<br>
     * 検証値がnull、またはブランクの場合はfalseを返す。
     * 
     * @param value 検証値
     * @param max   最大桁数
     * @return true：範囲内、false：範囲外
     */
    public static boolean isLengthRange(String value, int min, int max) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return min <= value.length() && value.length() <= max;
    }

    /**
     * 数値形式チェック。
     * 
     * @param value 検証値
     * @return true：数値形式、false：数値形式以外
     */
    public static boolean isNumber(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * メールアドレス形式チェックを行う。
     * 
     * @param value 検証値
     * @return true：メールアドレス形式、false：メールアドレス形式以外
     */
    public static boolean isMailaddress(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return REGEX_MAIL_ADDRESS.matcher(value).matches();
    }

    /**
     * 固定・携帯電話番号形式チェックを行う。
     * 
     * @param value 検証値
     * @return true：電話番号形式、false：電話番号形式以外
     */
    public static boolean isTel(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return REGEX_TEL.matcher(value).matches();
    }

    /**
     * 日付チェックを行う。
     * 
     * @param value 検証値
     * @return true：存在する日付、false：存在しない日付
     */
    public static boolean isDate(String value) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            df.setLenient(false);
            df.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 桁数超過チェック（Integer用）。<br>
     * 検証値がnullの場合はfalseを返す。
     *
     * @param value 検証値
     * @param max   最大桁数
     * @return true：桁数超過、false：桁数以内
     */
    public static boolean isNumCheck(String value, int max) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return value.length() > max;
    }

    /**
     * ユーザ名桁数チェック
     * 
     * @param value 入力値
     * @return true：桁数以内、false：桁数超過
     */
    public static boolean isuserNamecheck(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return value.length() <= 51;
    }

        /**
     * ユーザ名桁数チェック
     * 
     * @param value 入力値
     * @return true：桁数以内、false：桁数超過
     */
    public static String isuserNamecheckequal(String value) {

        String Name = "";
        if (StringUtils.isEmpty(value)) {
            return Name;
        }
        if ("A".equals(value)) {
            Name = "UserA";

        }else {
            Name = "UserB";
        }
        return Name;
    }
}
