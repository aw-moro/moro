package com.aceward.usermanagement.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aceward.usermanagement.form.ItemRegisterForm;
import com.aceward.usermanagement.service.ItemRegisterService;
import com.aceward.usermanagement.service.ItemRegisterValidateService;

@RestController // @Controllerから変更
@RequestMapping("/api") // URLの共通プレフィックス
@CrossOrigin(origins = "http://localhost:5173") // Reactのポートを許可
public class ApiItemRegisterController {

    @Autowired
    private ItemRegisterService itemRegisterService;
    @Autowired
    private ItemRegisterValidateService itemRegisterValidateService;

    @PostMapping("/item/register")
    public ResponseEntity<?> register(@RequestBody ItemRegisterForm form) {
        // 既存のバリデーションを流用
        List<String> errors = itemRegisterValidateService.execute(form);
        if (CollectionUtils.isNotEmpty(errors)) {
            return ResponseEntity.badRequest().body(errors);
        }

        // 既存の登録処理を流用
        String itemCode = itemRegisterService.execute(form);

        return ResponseEntity.ok(itemCode);
    }
}
