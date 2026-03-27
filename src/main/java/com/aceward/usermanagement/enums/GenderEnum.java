package com.aceward.usermanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性別のコード定義。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public enum GenderEnum {

    MALE("1", "男性"),
    FEMALE("2", "女性"),
    OTHER("9", "その他");

    /** コード値 */
    private final String code;
    /** 名称 */
    private final String name;

    /**
     * コードをもとに性別を取得する。<br>
     * 存在しない場合は、IllegalArgumentExceptionをスローする。
     * 
     * @param code コード
     * @return 性別
     */
    public static GenderEnum codeOf(String code) {
        for (GenderEnum mst : GenderEnum.values()) {
            if (mst.getCode().equals(code)) {
                return mst;
            }
        }
        throw new IllegalArgumentException("性別のコード値が不正です。");
    }

    /**
     * コード値をint型として取得する。
     * 
     * @return int型のコード値
     */
    public int getCodeAsInt() {
        return Integer.parseInt(code);
    }
}
