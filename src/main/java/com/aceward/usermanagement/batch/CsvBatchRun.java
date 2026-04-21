package com.aceward.usermanagement.batch;

import com.aceward.usermanagement.UserManagementApplication;
import com.aceward.usermanagement.service.CsvItemService;
import com.aceward.usermanagement.form.ItemRegisterForm;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

public class CsvBatchRun {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(UserManagementApplication.class, args);
        CsvItemService csvService = context.getBean(CsvItemService.class);

        String path = "D:/item_list.csv";
        List<String> lines = Files.readAllLines(Paths.get(path));

        System.out.println("処理を開始します。件数: " + lines.size());

        // 1行目を飛ばすためのフラグを作る
        boolean isFirstLine = true;

        for (String line : lines) {
            if (line == null || line.trim().isEmpty())
                continue;

            // 1行目（項目名）なら処理を飛ばす
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }

            String[] parts = line.split(",");
            ItemRegisterForm form = new ItemRegisterForm();

            try {
                // 0: ID (BOMとトリム処理)
                if (parts.length > 0 && parts[0] != null && !parts[0].trim().isEmpty()) {
                    String uuidStr = parts[0].replace("\uFEFF", "").replace("{", "").replace("}", "").trim();
                    if (uuidStr.length() >= 32) {
                        form.setItemId(UUID.fromString(uuidStr));
                    }
                }

                // 1〜9: 各カラムを順番にセット
                if (parts.length > 1)
                    form.setItem(parts[1]); // 品名
                if (parts.length > 2)
                    form.setItemCode(parts[2]); // コード
                if (parts.length > 3)
                    form.setUnitPrice(parts[3]); // 単価
                if (parts.length > 4)
                    form.setQuantity(parts[4]); // 数量
                if (parts.length > 5)
                    form.setUnit(parts[5]); // 単位
                if (parts.length > 6)
                    form.setItemDetail(parts[6]); // 詳細
                if (parts.length > 7)
                    form.setSalesTax(parts[7]); // 消費税

                // 8: 源泉徴収 (数値変換)
                if (parts.length > 8 && parts[8] != null && !parts[8].trim().isEmpty()) {
                    form.setWithholdingTax(Integer.parseInt(parts[8].trim()));
                }

                // 9: 削除フラグ
                if (parts.length > 9)
                    form.setDeleteFlg(parts[9]);

                // すべてセットした状態でサービス実行
                csvService.execute(form);

            } catch (Exception e) {
                System.out.println("エラー行: " + line);
                e.printStackTrace();
            }
        }

        System.out.println("完了。");
        System.exit(0);
    }
}