package com.aceward.usermanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 都道府県のコード定義。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public enum PrefCdEnum {

    PREF_CD_01("01", "北海道"),
    PREF_CD_02("02", "青森県"),
    PREF_CD_03("03", "岩手県"),
    PREF_CD_04("04", "宮城県"),
    PREF_CD_05("05", "秋田県"),
    PREF_CD_06("06", "山形県"),
    PREF_CD_07("07", "福島県"),
    PREF_CD_08("08", "茨城県"),
    PREF_CD_09("09", "栃木県"),
    PREF_CD_10("10", "群馬県"),
    PREF_CD_11("11", "埼玉県"),
    PREF_CD_12("12", "千葉県"),
    PREF_CD_13("13", "東京都"),
    PREF_CD_14("14", "神奈川県"),
    PREF_CD_15("15", "新潟県"),
    PREF_CD_16("16", "富山県"),
    PREF_CD_17("17", "石川県"),
    PREF_CD_18("18", "福井県"),
    PREF_CD_19("19", "山梨県"),
    PREF_CD_20("20", "長野県"),
    PREF_CD_21("21", "岐阜県"),
    PREF_CD_22("22", "静岡県"),
    PREF_CD_23("23", "愛知県"),
    PREF_CD_24("24", "三重県"),
    PREF_CD_25("25", "滋賀県"),
    PREF_CD_26("26", "京都府"),
    PREF_CD_27("27", "大阪府"),
    PREF_CD_28("28", "兵庫県"),
    PREF_CD_29("29", "奈良県"),
    PREF_CD_30("30", "和歌山県"),
    PREF_CD_31("31", "鳥取県"),
    PREF_CD_32("32", "島根県"),
    PREF_CD_33("33", "岡山県"),
    PREF_CD_34("34", "広島県"),
    PREF_CD_35("35", "山口県"),
    PREF_CD_36("36", "徳島県"),
    PREF_CD_37("37", "香川県"),
    PREF_CD_38("38", "愛媛県"),
    PREF_CD_39("39", "高知県"),
    PREF_CD_40("40", "福岡県"),
    PREF_CD_41("41", "佐賀県"),
    PREF_CD_42("42", "長崎県"),
    PREF_CD_43("43", "熊本県"),
    PREF_CD_44("44", "大分県"),
    PREF_CD_45("45", "宮崎県"),
    PREF_CD_46("46", "鹿児島県"),
    PREF_CD_47("47", "沖縄県");

    /** コード値 */
    private final String code;
    /** 名称 */
    private final String name;

    /**
     * コードをもとに都道府県を取得する。<br>
     * 存在しない場合は、IllegalArgumentExceptionをスローする。
     * 
     * @param code コード
     * @return 都道府県
     */
    public static PrefCdEnum codeOf(String code) {
        for (PrefCdEnum mst : PrefCdEnum.values()) {
            if (mst.getCode().equals(code)) {
                return mst;
            }
        }
        throw new IllegalArgumentException("都道府県のコード値が不正です。");
    }
}
