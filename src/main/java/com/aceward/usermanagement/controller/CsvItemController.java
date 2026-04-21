package com.aceward.usermanagement.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.aceward.usermanagement.dao.dto.ItemListDto;
import com.aceward.usermanagement.form.ItemSearchForm;
import com.aceward.usermanagement.service.ItemLisrSearchService;

@Controller
public class CsvItemController {

    @Autowired
    private ItemLisrSearchService itemListSearchService;

    /**
     * CSVダウンロード処理（全件出力版）
     */
    @GetMapping("/item/csv/download")
    public void downloadCsv(HttpServletResponse response) throws IOException {

        // 1. レスポンスヘッダーの設定
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"item_list.csv\"");

        // 2. 全件取得用の設定
        // 空の検索フォームを作成することで、条件なし（全件）としてサービスを実行します
        ItemSearchForm form = new ItemSearchForm();
        // 0ページ目から、システム上の最大件数まで取得するように設定
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        // 3. サービスを実行してデータを取得
        Page<ItemListDto> page = itemListSearchService.execute(form, pageable);

        // 4. CSV書き出し
        PrintWriter writer = response.getWriter();

        // Excelの文字化けを防止するBOMコードを先頭に書き込む
        writer.write('\ufeff');

        writer.println("ID,品名,品目コード,単価,数量,単位,品目詳細,消費税,源泉徴収税,削除フラグ");

        for (ItemListDto dto : page.getContent()) {
            // nullデータを空文字やデフォルト値に変換
            String itemId = dto.getItemId() != null ? dto.getItemId().toString() : "";
            String itemNm = dto.getItem() != null ? dto.getItem() : "";
            String itemCode = dto.getItemCode() != null ? dto.getItemCode() : "";
            String unitPrice = dto.getUnitPrice() != null ? dto.getUnitPrice().toString() : "0";
            String quantity = dto.getQuantity() != null ? dto.getQuantity().toString() : "0";
            String unit = dto.getUnit() != null ? dto.getUnit() : "";
            String itemDetail = dto.getItemDetail() != null ? dto.getItemDetail() : "";
            String salesTax = dto.getSalesTax() != null ? dto.getSalesTax() : "10";
            String withholdingTax = dto.getWithholdingTax() != null ? dto.getWithholdingTax().toString() : "0";
            String deleteFlg = dto.getDeleteFlg() != null ? dto.getDeleteFlg() : "0";

            // DBのテーブル定義に合わせた順番でCSV一行分を出力
            writer.println(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                    itemId,
                    itemNm,
                    itemCode,
                    unitPrice,
                    quantity,
                    unit,
                    itemDetail,
                    salesTax,
                    withholdingTax,
                    deleteFlg
                ));
        }
        writer.flush();
    }
}