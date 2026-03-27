package com.aceward.usermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import com.aceward.usermanagement.enums.GenderEnum;
import com.aceward.usermanagement.form.UserRegisterForm;

import lombok.val;
import lombok.var;

/**
 * {@link UserRegisterValidateService} のテストクラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class UserRegisterValidateServiceTest {

	/** テスト対象サービス */
	@InjectMocks
	private UserRegisterValidateService userRegisterValidateService;

	/** Mock対象サービス */
	@Mock
	private MessageSource messageSource;

	@Test
	public void testExecute_エラーなし() {
		var form = new UserRegisterForm();
		form.setUserName("ユーザ名");
		form.setUserNameKana("ユーザメイ");
		form.setMailaddress("test@example.com");
		form.setZipCd("1600022");
		form.setPrefCd("13");
		form.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		form.setTel("0369103204");
		form.setBirthday("1980/04/01");
		form.setGender(GenderEnum.MALE.getCode());
		form.setAdditions(List.of("01", "02"));
		form.setNote("備考");

		// テスト対象の実行
		val actual = userRegisterValidateService.execute(form);

		assertTrue(actual.isEmpty(), "エラーメッセージがないこと");
	}

	@Test
	public void testExecute_必須入力エラー() {
		var form = new UserRegisterForm();

		List<String> expected = new ArrayList<>();
		expected.add(getMessage("required.error", new String[] {"ユーザ名"}, "ユーザ名の必須エラー"));
		expected.add(getMessage("required.error", new String[] {"メールアドレス"}, "メールアドレスの必須エラー"));
		expected.add(getMessage("required.error", new String[] {"郵便番号"}, "郵便番号の必須エラー"));
		expected.add(getMessage("required.error", new String[] {"都道府県"}, "都道府県の必須エラー"));
		expected.add(getMessage("required.error", new String[] {"住所"}, "住所の必須エラー"));
		expected.add(getMessage("required.error", new String[] {"電話番号"}, "電話番号の必須エラー"));
		expected.add(getMessage("required.error", new String[] {"生年月日"}, "生年月日の必須エラー"));

		// テスト対象の実行
		val actual = userRegisterValidateService.execute(form);

		assertEquals(7, actual.size(), "エラーメッセージ件数が一致すること");
		assertIterableEquals(expected, actual, "メッセージ内容が一致すること");
	}

	@Test
	public void testExecute_桁数超過エラー() {
		var form = new UserRegisterForm();
		form.setUserName(RandomStringUtils.randomAlphabetic(51));
		form.setUserNameKana(RandomStringUtils.randomAlphabetic(101));
		form.setMailaddress(RandomStringUtils.randomAlphabetic(254) + "@example.com");
		form.setZipCd("16000221");
		form.setPrefCd("13");
		form.setAddress(RandomStringUtils.randomAlphabetic(257));
		form.setTel("0369103204");
		form.setBirthday("1980/04/01");
		form.setNote(RandomStringUtils.randomAlphabetic(1001));

		List<String> expected = new ArrayList<>();
		expected.add(getMessage("length.over.error", new String[] {"ユーザ名", "50"}, "ユーザ名の桁数超過エラー"));
		expected.add(getMessage("length.over.error", new String[] {"ユーザ名カナ", "100"}, "ユーザ名カナの桁数超過エラー"));
		expected.add(getMessage("length.over.error", new String[] {"メールアドレス", "254"}, "メールアドレスの桁数超過エラー"));
		expected.add(getMessage("length.equals.error", new String[] {"郵便番号", "7"}, "郵便番号の桁数超過エラー"));
		expected.add(getMessage("length.over.error", new String[] {"住所", "256"}, "住所の桁数超過エラー"));

		// テスト対象の実行
		val actual = userRegisterValidateService.execute(form);

		assertEquals(5, actual.size(), "エラーメッセージ件数が一致すること");
		assertIterableEquals(expected, actual, "メッセージ内容が一致すること");
	}

	@Test
	public void testExecute_最小桁数エラー() {
		var form = new UserRegisterForm();
		form.setUserName("ユーザ名");
		form.setUserNameKana("ユーザメイ");
		form.setMailaddress("test@example.com");
		form.setZipCd("0000");
		form.setPrefCd("13");
		form.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		form.setTel("0369103204");
		form.setBirthday("1980/04/01");
		form.setGender(GenderEnum.MALE.getCode());
		form.setAdditions(List.of("01", "02"));
		form.setNote("備考");

		List<String> expected = new ArrayList<>();
		expected.add(getMessage("length.equals.error", new String[] {"郵便番号", "7"}, "郵便番号の桁数超過エラー"));

		// テスト対象の実行
		val actual = userRegisterValidateService.execute(form);

		assertEquals(1, actual.size(), "エラーメッセージ件数が一致すること");
		assertIterableEquals(expected, actual, "メッセージ内容が一致すること");
	}

	@Test
	public void testExecute_形式エラー() {
		var form = new UserRegisterForm();
		form.setUserName("ユーザ名");
		form.setUserNameKana("ユーザメイ");
		form.setMailaddress("test");
		form.setZipCd("aaa0022");
		form.setPrefCd("999");
		form.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		form.setTel("03-6910-3204");
		form.setBirthday("1980/04/31");
		form.setGender("99");
		form.setAdditions(List.of("aa"));
		form.setNote("備考");

		List<String> expected = new ArrayList<>();
		expected.add(getMessage("illegal.mailaddress.error", null, "メールアドレスの形式エラー"));
		expected.add(getMessage("number.format.error", new String[] {"郵便番号"}, "郵便番号の形式エラー"));
		expected.add(getMessage("illegal.error", new String[] {"都道府県"}, "都道府県の形式エラー"));
		expected.add(getMessage("illegal.tel.error", null, "電話番号の形式エラー"));
		expected.add(getMessage("illegal.error", new String[] {"生年月日"}, "生年月日の形式エラー"));
		expected.add(getMessage("illegal.error", new String[] {"性別"}, "性別の形式エラー"));
		expected.add(getMessage("illegal.error", new String[] {"付加情報"}, "付加情報の形式エラー"));

		// テスト対象の実行
		val actual = userRegisterValidateService.execute(form);

		assertEquals(7, actual.size(), "エラーメッセージ件数が一致すること");
		assertIterableEquals(expected, actual, "メッセージ内容が一致すること");
	}

	/**
	 * モック用のエラーメッセージ取得処理を実行する。
	 * 
	 * @param messageKey エラーメッセージキー
	 * @param params パラメータ
	 * @param message エラーメッセージ
	 * @return エラーメッセージ
	 */
	private String getMessage(String messageKey, String[] params, String message) {
		doReturn(message).when(messageSource).getMessage(eq(messageKey), eq(params),
				eq(Locale.JAPAN));
		return message;
	}
}
