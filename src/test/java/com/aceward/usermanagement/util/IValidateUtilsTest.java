package com.aceward.usermanagement.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import lombok.val;

/**
 * {@link IValidateUtils} のテストクラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
class IValidateUtilsTest {

	@Test
	public void testIsLengthEquals_桁数一致チェック_未入力() {
		// テスト対象の実行
		val actual = IValidateUtils.isLengthEquals("", 4);

		assertFalse(actual, "期待値が一致すること");
	}

	// @Test
	// public void testIsLengthEquals_桁数一致チェック_一致() {
	// 	// テスト対象の実行
	// 	val actual = IValidateUtils.isLengthEquals("1234", 4);

	// 	assertTrue(actual, "期待値が一致すること");
	// }

	// @Test
	// public void testIsLengthEquals_桁数一致チェック_不一致() {
	// 	// テスト対象の実行
	// 	val actual = IValidateUtils.isLengthEquals("12345", 4);

	// 	assertFalse(actual, "期待値が一致すること");
	// }

}
