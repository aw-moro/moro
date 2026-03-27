package com.aceward.usermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.aceward.usermanagement.constant.Constants;
import com.aceward.usermanagement.dao.domain.User;
import com.aceward.usermanagement.dao.domain.UserAddition;
import com.aceward.usermanagement.dao.domain.UserDetail;
import com.aceward.usermanagement.enums.GenderEnum;
import com.aceward.usermanagement.form.UserRegisterForm;
import com.aceward.usermanagement.service.db.UserAdditionService;
import com.aceward.usermanagement.service.db.UserDetailService;
import com.aceward.usermanagement.service.db.UserService;

import lombok.val;
import lombok.var;

/**
 * {@link UserUpdateViewService} のテストクラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class UserUpdateViewServiceTest {

	/** テスト対象サービス */
	@InjectMocks
	private UserUpdateViewService userUpdateViewService;

	/** Mock対象サービス */
	@Mock
	private UserService userService;
	@Mock
	private UserDetailService userDetailService;
	@Mock
	private UserAdditionService userAdditionService;

	@Test
	public void testExecute_正常系_付加情報ありの場合() throws Exception {
		val userId = UUID.randomUUID();
		val userCd = "0000000001";
		val updatedAtInstant = Instant.parse("2022-01-01T00:00:00Z");
		val updatedAtMillisecond = String.valueOf(updatedAtInstant.toEpochMilli());

		// ユーザ取得
		var user = new User();
		user.setId(userId);
		user.setUserCd(userCd);
		user.setCreatedAt(new Timestamp(0));
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(new Timestamp(updatedAtInstant.toEpochMilli()));
		user.setUpdatedStaffId(Constants.STAFF_ID);
		doReturn(Optional.of(user)).when(userService).findByUserCd(eq(userCd));

		// ユーザ詳細取得
		var userDetail = new UserDetail();
		userDetail.setUserId(userId);
		userDetail.setUserName("山田太郎");
		userDetail.setUserNameKana("ヤマダタロウ");
		userDetail.setMailaddress("test@example.com");
		userDetail.setZipCd("1600022");
		userDetail.setPrefCd("13");
		userDetail.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		userDetail.setTel("0369103204");
		userDetail.setBirthday("1980/04/01");
		userDetail.setGender(GenderEnum.MALE.getCodeAsInt());
		userDetail.setNote("備考");
		doReturn(Optional.of(userDetail)).when(userDetailService).findByUserId(eq(userId));

		// ユーザ付加情報取得
		List<UserAddition> userAdditions = new ArrayList<>();
		{
			var userAddition = new UserAddition();
			userAddition.setId(UUID.randomUUID());
			userAddition.setUserId(userId);
			userAddition.setAddition("01");
			userAdditions.add(userAddition);
		}
		{
			var userAddition = new UserAddition();
			userAddition.setId(UUID.randomUUID());
			userAddition.setUserId(userId);
			userAddition.setAddition("02");
			userAdditions.add(userAddition);
		}
		doReturn(userAdditions).when(userAdditionService).findByUserId(eq(userId));

		// テスト対象の実行
		val actual = userUpdateViewService.execute(userCd);

		var expected = new UserRegisterForm();
		expected.setUserCd(userCd);
		expected.setUserName("山田太郎");
		expected.setUserNameKana("ヤマダタロウ");
		expected.setMailaddress("test@example.com");
		expected.setZipCd("1600022");
		expected.setPrefCd("13");
		expected.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		expected.setTel("0369103204");
		expected.setBirthday("1980/04/01");
		expected.setGender(GenderEnum.MALE.getCode());
		expected.setAdditions(List.of("01", "02"));
		expected.setNote("備考");
		expected.setUpdatedAtMillisecond(updatedAtMillisecond);

		assertEquals(expected, actual, "戻り値が一致すること");
	}

	@Test
	public void testExecute_正常系_付加情報なしの場合() throws Exception {
		val userId = UUID.randomUUID();
		val userCd = "0000000001";
		val updatedAtInstant = Instant.parse("2022-01-01T00:00:00Z");
		val updatedAtMillisecond = String.valueOf(updatedAtInstant.toEpochMilli());

		// ユーザ取得
		var user = new User();
		user.setId(userId);
		user.setUserCd(userCd);
		user.setCreatedAt(new Timestamp(0));
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(new Timestamp(updatedAtInstant.toEpochMilli()));
		user.setUpdatedStaffId(Constants.STAFF_ID);
		doReturn(Optional.of(user)).when(userService).findByUserCd(eq(userCd));

		// ユーザ詳細取得
		var userDetail = new UserDetail();
		userDetail.setUserId(userId);
		userDetail.setUserName("山田太郎");
		userDetail.setUserNameKana("ヤマダタロウ");
		userDetail.setMailaddress("test@example.com");
		userDetail.setZipCd("1600022");
		userDetail.setPrefCd("13");
		userDetail.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		userDetail.setTel("0369103204");
		userDetail.setBirthday("1980/04/01");
		userDetail.setGender(GenderEnum.MALE.getCodeAsInt());
		userDetail.setNote("備考");
		doReturn(Optional.of(userDetail)).when(userDetailService).findByUserId(eq(userId));

		// ユーザ付加情報取得
		doReturn(List.of()).when(userAdditionService).findByUserId(eq(userId));

		// テスト対象の実行
		val actual = userUpdateViewService.execute(userCd);

		var expected = new UserRegisterForm();
		expected.setUserCd(userCd);
		expected.setUserName("山田太郎");
		expected.setUserNameKana("ヤマダタロウ");
		expected.setMailaddress("test@example.com");
		expected.setZipCd("1600022");
		expected.setPrefCd("13");
		expected.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		expected.setTel("0369103204");
		expected.setBirthday("1980/04/01");
		expected.setGender(GenderEnum.MALE.getCode());
		expected.setAdditions(List.of());
		expected.setNote("備考");
		expected.setUpdatedAtMillisecond(updatedAtMillisecond);

		assertEquals(expected, actual, "戻り値が一致すること");
	}

	@Test
	public void testExecute_異常系_ユーザ情報が存在しない場合() {
		val userCd = "0000000001";

		// ユーザ取得
		doReturn(Optional.empty()).when(userService).findByUserCd(eq(userCd));

		// テスト対象の実行兼例外検証
		assertThrows(NotFoundException.class, () -> userUpdateViewService.execute(userCd));
	}

	@Test
	public void testExecute_異常系_ユーザ詳細が存在しない場合() {
		val userId = UUID.randomUUID();
		val userCd = "0000000001";

		// ユーザ取得
		var user = new User();
		user.setId(userId);
		user.setUserCd(userCd);
		user.setCreatedAt(new Timestamp(0));
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(new Timestamp(0));
		user.setUpdatedStaffId(Constants.STAFF_ID);
		doReturn(Optional.of(user)).when(userService).findByUserCd(eq(userCd));

		// ユーザ詳細取得
		doReturn(Optional.empty()).when(userDetailService).findByUserId(eq(userId));

		// テスト対象の実行兼例外検証
		assertThrows(NotFoundException.class, () -> userUpdateViewService.execute(userCd));
	}
}
