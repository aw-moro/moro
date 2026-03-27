package com.aceward.usermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.aceward.usermanagement.dao.dto.UserListDto;
import com.aceward.usermanagement.dao.dto.UserSearchDto;
import com.aceward.usermanagement.enums.GenderEnum;
import com.aceward.usermanagement.form.UserSearchForm;
import com.aceward.usermanagement.service.db.UserDetailService;

import lombok.val;
import lombok.var;

/**
 * {@link UserLisrSearchService} のテストクラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class UserLisrSearchServiceTest {

	/** テスト対象サービス */
	@InjectMocks
	private UserLisrSearchService userLisrSearchService;

	/** Mock対象サービス */
	@Mock
	private UserDetailService userDetailService;

	@Test
	public void testExecute_検索結果0件の場合() throws Exception {
		var form = new UserSearchForm();
		form.setUserName("山田太郎");
		form.setMailaddress("test@example.com");
		form.setPrefCd("13");
		form.setGender(GenderEnum.MALE.getCode());
		form.setAdditions(List.of("01", "02"));
		form.setPage(1L);
		form.setSize(5L);

		var pageable = Pageable.unpaged();

		// ユーザ一覧件数取得
		var dto = new UserSearchDto();
		dto.setUserName(form.getUserName());
		dto.setMailaddress(form.getMailaddress());
		dto.setPrefCd(form.getPrefCd());
		dto.setGender(form.getGender());
		dto.setAdditions(form.getAdditions());
		dto.setPage(form.getPage());
		dto.setSize(form.getSize());
		doReturn(0).when(userDetailService).countByUserSearchDto(eq(dto));

		// テスト対象の実行
		val actual = userLisrSearchService.execute(form, pageable);

		val expected = new PageImpl<>(new ArrayList<>(), pageable, 0);
		assertEquals(expected, actual, "戻り値が一致すること");
	}

	@Test
	public void testExecute_検索結果1件以上ある場合() throws Exception {
		var form = new UserSearchForm();
		form.setUserName("山田太郎");
		form.setMailaddress("test@example.com");
		form.setPrefCd("13");
		form.setGender(GenderEnum.MALE.getCode());
		form.setAdditions(List.of("01", "02"));
		form.setPage(1L);
		form.setSize(5L);

		var pageable = Pageable.unpaged();

		// ユーザ一覧件数取得
		var dto = new UserSearchDto();
		dto.setUserName(form.getUserName());
		dto.setMailaddress(form.getMailaddress());
		dto.setPrefCd(form.getPrefCd());
		dto.setGender(form.getGender());
		dto.setAdditions(form.getAdditions());
		dto.setPage(form.getPage());
		dto.setSize(form.getSize());
		doReturn(1).when(userDetailService).countByUserSearchDto(eq(dto));

		var userListDto = new UserListDto();
		userListDto.setUserId(UUID.randomUUID().toString());
		userListDto.setUserCd("0000000001");
		userListDto.setUserName("山田太郎");
		userListDto.setMailaddress("test@example.com");
		userListDto.setPrefCd("13");
		userListDto.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		userListDto.setAdditions("01,02");
		userListDto.setNote("備考");

		var userList = List.of(userListDto);
		doReturn(userList).when(userDetailService).findByUserSearchDto(eq(dto));

		// テスト対象の実行
		val actual = userLisrSearchService.execute(form, pageable);

		val expected = new PageImpl<>(userList, pageable, 1);
		assertEquals(expected, actual, "戻り値が一致すること");
	}
}
