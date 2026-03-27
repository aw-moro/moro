package com.aceward.usermanagement.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import lombok.val;

/**
 * {@link ValidateUtils} のテストクラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
class ValidateUtilsTest {

	@Test
	public void testiIsLengthOver_桁数チェック_未入力() {
		// テスト対象の実行
		val actual = ValidateUtils.isLengthOver("", 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testiIsLengthOver_桁数チェック_桁数以内() {
		// テスト対象の実行
		val actual = ValidateUtils.isLengthOver("1234", 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testiIsLengthOver_桁数チェック_桁数超過() {
		// テスト対象の実行
		val actual = ValidateUtils.isLengthOver("12345", 4);

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthEquals_桁数一致チェック_未入力() {
		// テスト対象の実行
		val actual = ValidateUtils.isLengthEquals("", 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthEquals_桁数一致チェック_一致() {
		// テスト対象の実行
		val actual = ValidateUtils.isLengthEquals("1234", 4);

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthEquals_桁数一致チェック_不一致() {
		// テスト対象の実行
		val actual = ValidateUtils.isLengthEquals("12345", 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthRange_桁数範囲チェック_未入力() {
		// テスト対象の実行
		val actual = ValidateUtils.isLengthRange("", 1, 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthRange_桁数範囲チェック_範囲内() {
		// テスト対象の実行
		val actual = ValidateUtils.isLengthRange("1234", 1, 4);

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthRange_桁数範囲チェック_範囲外() {
		// テスト対象の実行
		val actual = ValidateUtils.isLengthRange("12345", 1, 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthRange_桁数範囲チェック_min() {
		// テスト対象の実行
		val actual = ValidateUtils.isLengthRange("12", 3, 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsNumber_数値形式チェック_未入力() {
		// テスト対象の実行
		val actual = ValidateUtils.isNumber("");

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsNumber_数値形式チェック_数値() {
		// テスト対象の実行
		val actual = ValidateUtils.isNumber("1234");

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testIsNumber_数値形式チェック_数値以外() {
		// テスト対象の実行
		val actual = ValidateUtils.isNumber("aaa");

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsMailaddress_メールアドレス形式チェック_未入力() {
		// テスト対象の実行
		val actual = ValidateUtils.isMailaddress("");

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsMailaddress_メールアドレス形式チェック_メールアドレス() {
		// テスト対象の実行
		val actual = ValidateUtils.isMailaddress("test@example.com");

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testIsMailaddress_メールアドレス形式チェック_メールアドレス以外() {
		// テスト対象の実行
		val actual = ValidateUtils.isMailaddress("aaa");

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsTel_電話番号形式チェック_未入力() {
		// テスト対象の実行
		val actual = ValidateUtils.isTel("");

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsTel_電話番号形式チェック_メールアドレス() {
		// テスト対象の実行
		val actual = ValidateUtils.isTel("0312345678");

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testIsTel_電話番号形式チェック_メールアドレス以外() {
		// テスト対象の実行
		val actual = ValidateUtils.isTel("aaa");

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsDate_日付形式チェック_未入力() {
		// テスト対象の実行
		val actual = ValidateUtils.isDate("");

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsDate_日付形式チェック_メールアドレス() {
		// テスト対象の実行
		val actual = ValidateUtils.isDate("2023/01/01");

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testIsDate_日付形式チェック_メールアドレス以外() {
		// テスト対象の実行
		val actual = ValidateUtils.isDate("2022/12/32");

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testisNumCheck_桁数チェックInteger用_範囲以上() {

		val actual = ValidateUtils.isNumCheck("string value", 10);

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testisNumCheck_桁数チェックInteger用_範囲以下() {

		val actual = ValidateUtils.isNumCheck("012345", 10);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testisNumCheck_桁数チェックInteger用_未入力() {

		val actual = ValidateUtils.isNumCheck("", 10);

		assertFalse(actual, "期待値が一致すること");
	}
}
