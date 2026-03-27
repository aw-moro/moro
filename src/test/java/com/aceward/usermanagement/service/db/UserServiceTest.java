package com.aceward.usermanagement.service.db;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.aceward.usermanagement.constant.Constants;
import com.aceward.usermanagement.dao.domain.User;

import lombok.val;
import lombok.var;

/**
 * {@link UserService} のテストクラス。<br>
 * 基本的にJPAのテストケースは不要。<br>
 * 但し、JPAの中で動きが気になるものはテストケースを作成する。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired private UserService userService;

	@Test
	public void testSaveAndFlush_登録結果の取得確認() {
		var user = new User();
		user.setCreatedAt(new Timestamp(0));
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(new Timestamp(0));
		user.setUpdatedStaffId(Constants.STAFF_ID);

		// テスト対象の実行
		val actual = userService.saveAndFlush(user);

		assertTrue(actual.isPresent(), "登録結果が取得できること");

		val actualUser = actual.get();
		assertNotNull(actualUser.getId(), "ユーザIDがnullではないこと");
		assertNotNull(actualUser.getUserCd(), "ユーザコードがnullではないこと");
	}
}
