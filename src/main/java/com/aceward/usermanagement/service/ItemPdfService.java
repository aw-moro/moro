package com.aceward.usermanagement.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.aceward.usermanagement.dao.domain.Item;
import com.aceward.usermanagement.service.db.ItemService;

/**
 * 品目詳細PDF生成サービス。
 */
@Service
public class ItemPdfService {

    @Autowired
    private ItemService itemService;

    /**
     * 指定された品目IDの情報をPDFとして出力します。
     * 
     * @param itemId 品目ID
     * @return PDFのバイト配列
     * @throws Exception
     */
    public byte[] execute(UUID itemId) throws Exception {
        // DBからデータを取得
        Optional<Item> optionalItem = itemService.findById(itemId);

        if (optionalItem.isEmpty()) {
            throw new RuntimeException("対象の品目情報が存在しません。");
        }
        Item item = optionalItem.get();

        // PDF生成処理
        try (PDDocument document = new PDDocument();
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            document.addPage(page);

            // フォントの読み込み
            InputStream fontStream = new ClassPathResource("templates/font/MPLUS1p-Regular.ttf").getInputStream();
            PDType0Font font = PDType0Font.load(document, fontStream);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // レイアウトの基準座標
                float startX = 70;
                float startY = 700;

                // タイトルエリア
                contentStream.beginText();
                contentStream.setFont(font, 22);
                contentStream.newLineAtOffset(startX + 110, startY);
                contentStream.showText("品目詳細情報");
                contentStream.endText();

                // 基本情報エリア（品目・コード）
                contentStream.beginText();
                contentStream.setFont(font, 13);
                contentStream.newLineAtOffset(startX + 50, startY - 60);
                contentStream.showText("品目      ：  " + (item.getItem() != null ? item.getItem() : ""));

                contentStream.newLineAtOffset(0, -30);
                contentStream.showText("品目コード：  " + (item.getItemCode() != null ? item.getItemCode() : ""));
                contentStream.endText();

                contentStream.setLineWidth(1.0f);
                contentStream.moveTo(startX, startY - 70);
                contentStream.lineTo(startX + 450, startY - 70);
                contentStream.stroke();

                contentStream.setLineWidth(1.0f);
                contentStream.moveTo(startX, startY - 100);
                contentStream.lineTo(startX + 450, startY - 100);
                contentStream.stroke();

                // 表エリア
                float tableTop = startY - 130;
                float rowHeight = 30;
                float tableWidth = 450;
                float tableHeight = rowHeight * 2;

                contentStream.setLineWidth(1.0f);
                // 外枠
                contentStream.addRect(startX, tableTop - tableHeight, tableWidth, tableHeight);
                // 真ん中の横線
                contentStream.moveTo(startX, tableTop - rowHeight);
                contentStream.lineTo(startX + tableWidth, tableTop - rowHeight);

                // 縦線
                float[] cols = { 90, 160, 230, 320 }; // 列の区切り
                for (float colX : cols) {
                    contentStream.moveTo(startX + colX, tableTop);
                    contentStream.lineTo(startX + colX, tableTop - tableHeight);
                }
                contentStream.stroke();

                // 【文字：見出し】
                contentStream.beginText();
                contentStream.setFont(font, 11);
                contentStream.newLineAtOffset(startX + 10, tableTop - 20);
                contentStream.showText("単価");
                contentStream.newLineAtOffset(90, 0);
                contentStream.showText("数量");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("単位");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("消費税");
                contentStream.newLineAtOffset(90, 0);
                contentStream.showText("源泉徴収税");
                contentStream.endText();

                // 【文字：データ】
                contentStream.beginText();
                contentStream.newLineAtOffset(startX + 10, tableTop - 50);
                contentStream.showText(item.getUnitPrice() != null ? item.getUnitPrice().toString() : "");
                contentStream.newLineAtOffset(90, 0);
                contentStream.showText(item.getQuantity() != null ? item.getQuantity().toString() : "");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText(item.getUnit() != null ? item.getUnit() : "");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText(item.getSalesTax() != null ? item.getSalesTax().toString() : "10%");
                contentStream.newLineAtOffset(90, 0);
                contentStream.showText(item.getWithholdingTax() == null ? "なし" : "あり");
                contentStream.endText();

                // 品目詳細エリア
                float detailTop = tableTop - tableHeight - 30;
                contentStream.addRect(startX, detailTop - 120, tableWidth, 120);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.newLineAtOffset(startX + 10, detailTop - 25);
                contentStream.showText("品目詳細");
                contentStream.endText();

                contentStream.moveTo(startX, detailTop - 30);
                contentStream.lineTo(startX + 450, detailTop - 30);
                contentStream.stroke();

                // 詳細の内容を少し下げて表示
                contentStream.beginText();
                contentStream.newLineAtOffset(startX + 15, detailTop - 55);
                contentStream.showText(item.getItemDetail() != null ? item.getItemDetail() : "");
                contentStream.endText();
            }

            document.save(baos);
            return baos.toByteArray();
        }
    }
}