package com.aceward.usermanagement.util;

// import java.text.DateFormat;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 品目バリデーション共通ユーティリティー。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
public class ItemValidateUtils {

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

}
