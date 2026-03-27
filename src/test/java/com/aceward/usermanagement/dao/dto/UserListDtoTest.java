package com.aceward.usermanagement.dao.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import com.aceward.usermanagement.enums.GenderEnum;
import com.aceward.usermanagement.enums.PrefCdEnum;
import lombok.var;

/**
 * {@link UserListDto} のテストクラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
class UserListDtoTest {

	@Test
	public void testGetPrefName_都道府県名が取得できること() {
		var dto = new UserListDto();
		dto.setPrefCd(PrefCdEnum.PREF_CD_01.getCode());

		assertEquals(PrefCdEnum.PREF_CD_01.getName(), dto.getPrefName(), "戻り値が一致すること");
	}

	@Test
	public void testGetGenderName_性別が取得できること() {
		var dto = new UserListDto();
		dto.setGender(GenderEnum.MALE.getCodeAsInt());

		assertEquals(GenderEnum.MALE.getName(), dto.getGenderName(), "戻り値が一致すること");
	}

	@Test
	public void testGetGenderName_性別が未設定の場合はブランクが取得できること() {
		var dto = new UserListDto();

		assertEquals("", dto.getGenderName(), "戻り値が一致すること");
	}

	@Test
	public void testGetAddition_付加情報が取得できること() {
		var dto = new UserListDto();
		dto.setAdditions("01,02");

		assertEquals("お得意様、クレーマー", dto.getAddition(), "戻り値が一致すること");
	}

	@Test
	public void testGetAddition_付加情報が未設定の場合はブランクが取得できること() {
		var dto = new UserListDto();

		assertEquals("", dto.getAddition(), "戻り値が一致すること");
	}
}
