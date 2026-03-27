package com.aceward.usermanagement.dao.common;

import java.io.InputStream;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * SQLに関する共通処理を取り扱うクラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Slf4j
@Component
public class CommonSQL {

    /**
     * 外部ファイルに定義されているSQLを取得する。
     * 
     * @param path SQLのファイルパス
     * @return SQL文字列
     */
    public String loadQuery(String path) {
        String sql = null;
        java.util.Scanner scanner = null;
        try {
            InputStream is = this.getClass().getResourceAsStream(path);
            scanner = new java.util.Scanner(is).useDelimiter("\\A");
            sql = scanner.hasNext() ? scanner.next() : "";
        } catch (Exception e) {
            log.error("Failed to load query {}: {}", path, e.getMessage(), e);
            return null;
        } finally {
            scanner.close();
        }
        return sql;
    }
}
