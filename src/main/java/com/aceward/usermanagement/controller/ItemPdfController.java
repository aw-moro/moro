package com.aceward.usermanagement.controller;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import com.aceward.usermanagement.dao.domain.Item;
import com.aceward.usermanagement.service.ItemPdfService;
import com.aceward.usermanagement.service.db.ItemService;

/**
 * 品目PDFコントローラ。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Controller
public class ItemPdfController {

    /** 品目PDFサービス */
    @Autowired
    private ItemPdfService itemPdfService;

    @Autowired
    private ItemService itemService;

    /**
     * PDFダウンロード処理。
     * 画面の <input type="hidden" name="itemId" ... /> から値を受け取ります。
     */
   @PostMapping("/item/pdf")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam("itemId") UUID itemId) {
        try {
            // 1. まずは品目情報を取得（名前をファイル名に使うため）
            Optional<Item> optionalItem = itemService.findById(itemId);
            if (optionalItem.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            String itemName = optionalItem.get().getItem();

            // 2. サービスを実行してPDFのバイナリデータを取得
            byte[] pdfBytes = itemPdfService.execute(itemId);

            // 3. ファイル名を準備する
            // ここで itemName を使って「あいうえお.pdf」のような文字列を作る
            String fileName = itemName + ".pdf";
            
            // 4. 日本語ファイル名をエンコードする（ここで赤線の原因だった変数を作ります）
            // これによりブラウザが日本語の名前を正しく認識できるようになります
            String encodedFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);

            // 5. レスポンスヘッダーの設定
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            // 6. ファイル名をセット（ここで「encodedFileName」を使います）
            headers.setContentDispositionFormData("attachment", encodedFileName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}    