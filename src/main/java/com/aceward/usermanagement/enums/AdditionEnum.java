package com.aceward.usermanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 付加情報のコード定義。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public enum AdditionEnum {

    CODE_01("01", "お得意様"),
    CODE_02("02", "クレーマー"),
    CODE_03("03", "外国人"),
    CODE_04("04", "離島在住"),
    CODE_05("05", "海外在住"),
    CODE_06("06", "関係者"),
    CODE_07("07", "休眠顧客"),
    CODE_08("08", "要注意"),
    CODE_09("09", "その他");

    /** コード値 */
    private final String code;
    /** 名称 */
    private final String name;

    /**
     * コードをもとに付加情報を取得する。<br>
     * 存在しない場合は、IllegalArgumentExceptionをスローする。
     * 
     * @param code コード
     * @return 付加情報
     */
    public static AdditionEnum codeOf(String code) {
        for (AdditionEnum mst : AdditionEnum.values()) {
            if (mst.getCode().equals(code)) {
                return mst;
            }
        }
        throw new IllegalArgumentException("付加情報のコード値が不正です。");
    }
}
