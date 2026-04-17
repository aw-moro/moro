package com.aceward.usermanagement.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import lombok.val;

/**
 * {@link ItemValidateUtils} のテストクラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
class ItemValidateUtilsTest {

	@Test
	public void testiIsLengthOver_桁数チェック_未入力() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isLengthOver("", 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testiIsLengthOver_桁数チェック_桁数以内() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isLengthOver("1234", 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testiIsLengthOver_桁数チェック_桁数超過() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isLengthOver("12345", 4);

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthEquals_桁数一致チェック_未入力() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isLengthEquals("", 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthEquals_桁数一致チェック_一致() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isLengthEquals("1234", 4);

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthEquals_桁数一致チェック_不一致() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isLengthEquals("12345", 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthRange_桁数範囲チェック_未入力() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isLengthRange("", 1, 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthRange_桁数範囲チェック_範囲内() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isLengthRange("1234", 1, 4);

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthRange_桁数範囲チェック_範囲外() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isLengthRange("12345", 1, 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsLengthRange_桁数範囲チェック_min() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isLengthRange("12", 3, 4);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsNumber_数値形式チェック_未入力() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isNumber("");

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testIsNumber_数値形式チェック_数値() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isNumber("1234");

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testIsNumber_数値形式チェック_数値以外() {
		// テスト対象の実行
		val actual = ItemValidateUtils.isNumber("aaa");

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testisNumCheck_桁数チェックInteger用_範囲以上() {

		val actual = ItemValidateUtils.isNumCheck("string value", 10);

		assertTrue(actual, "期待値が一致すること");
	}

	@Test
	public void testisNumCheck_桁数チェックInteger用_範囲以下() {

		val actual = ItemValidateUtils.isNumCheck("012345", 10);

		assertFalse(actual, "期待値が一致すること");
	}

	@Test
	public void testisNumCheck_桁数チェックInteger用_未入力() {

		val actual = ItemValidateUtils.isNumCheck("", 10);

		assertFalse(actual, "期待値が一致すること");
	}

	// 品目 (最大900文字)
	// 品目コード (最大60文字)
	@Test
	public void testisLengthOver_品目_901文字でエラーになること() {

		// 900文字制限に対して
		val actual = ItemValidateUtils.isLengthOver("12345", 4);

		assertTrue(actual, "超過判定(true)になること");
	}

	// 単価・数量 (半角数字 最大11桁)
	@Test
	public void testisNumber_数字以外はエラーになること() {

		val actual = ItemValidateUtils.isNumber("abc");

		assertFalse(actual, "数字以外はエラーになること");
	}

	@Test
	public void testisNumCheck_12桁でエラーになること() {

		// 11桁制限に対して
		val actual = ItemValidateUtils.isNumCheck("123456789012", 11);

		assertTrue(actual, "11桁を超えたらエラー");
	}

	@Test
	public void testisNumCheck_11桁以内は正常であること() {

		val actual = ItemValidateUtils.isNumCheck("12345678901", 11);

		assertFalse(actual, "11桁以内はfalse(正常)");
	}

	// 単位 (最大40文字)
	@Test
	public void testisLengthOver_41文字でエラーになること() {

		val actual = ItemValidateUtils.isLengthOver("12345", 4);

		assertTrue(actual, "制限超過でtrue");
	}

	@Test
	public void testItemValidateUtilsConstructor() {
		// コンストラクタを呼び出すだけのテスト
		new ItemValidateUtils();
	}

}
