package com.aceward.usermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import com.aceward.usermanagement.bean.ClockConfig;
import com.aceward.usermanagement.constant.Constants;
import com.aceward.usermanagement.dao.domain.User;
import com.aceward.usermanagement.dao.domain.UserAddition;
import com.aceward.usermanagement.dao.domain.UserDetail;
import com.aceward.usermanagement.enums.GenderEnum;
import com.aceward.usermanagement.exception.OptimisticErrorException;
import com.aceward.usermanagement.form.UserRegisterForm;
import com.aceward.usermanagement.service.db.UserAdditionService;
import com.aceward.usermanagement.service.db.UserDetailService;
import com.aceward.usermanagement.service.db.UserService;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import lombok.val;
import lombok.var;

/**
 * {@link UserUpdateService} のテストクラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class UserUpdateServiceTest {

	/** テスト対象サービス */
	@InjectMocks
	private UserUpdateService userUpdateService;

	/** Mock対象サービス */
	@Mock
	private ClockConfig clockConfig;
	@Mock
	private UserService userService;
	@Mock
	private UserDetailService userDetailService;
	@Mock
	private UserAdditionService userAdditionService;
	@Mock
	private Appender<ILoggingEvent> mockAppender;

	/** ログ検証用 */
	@Captor
	private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

	/** パラメータ検証用 */
	@Captor
	private ArgumentCaptor<UserAddition> userAdditionCallbackCaptor;

	@Test
	public void testExecute_正常系_付加情報ありの場合() throws Exception {
		var logger = (Logger) LoggerFactory.getLogger(UserUpdateService.class);
		logger.addAppender(mockAppender);

		val userId = UUID.randomUUID();
		val userCd = "0000000001";
		val updatedAtInstant = Instant.parse("2022-01-01T00:00:00Z");
		val updatedAtMillisecond = String.valueOf(updatedAtInstant.toEpochMilli());
		val nowInstant = Instant.parse("2022-01-01T01:00:00Z");

		var form = new UserRegisterForm();
		form.setUserCd(userCd);
		form.setUserName("山田太郎");
		form.setUserNameKana("ヤマダタロウ");
		form.setMailaddress("test2@example.com");
		form.setZipCd("1600022");
		form.setPrefCd("13");
		form.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		form.setTel("0369103204");
		form.setBirthday("1980/04/01");
		form.setGender(GenderEnum.MALE.getCode());
		form.setAdditions(List.of("01", "02"));
		form.setNote("備考");
		form.setUpdatedAtMillisecond(updatedAtMillisecond);

		// 現在日時
		doReturn(Clock.fixed(nowInstant, ZoneId.of("Asia/Tokyo"))).when(clockConfig).clock();

		// ユーザ取得
		var user = new User();
		user.setId(userId);
		user.setUserCd(userCd);
		user.setCreatedAt(new Timestamp(0));
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(new Timestamp(updatedAtInstant.toEpochMilli()));
		user.setUpdatedStaffId(Constants.STAFF_ID);
		doReturn(Optional.of(user)).when(userService).findByUserCd(eq(userCd));

		// ユーザ更新
		var updateUser = new User();
		BeanUtils.copyProperties(user, updateUser);
		updateUser.setUpdatedAt(new Timestamp(nowInstant.toEpochMilli()));
		updateUser.setUpdatedStaffId(Constants.STAFF_ID);
		doReturn(Optional.of(updateUser)).when(userService).saveAndFlush(eq(updateUser));

		// ユーザ詳細取得
		var userDetail = new UserDetail();
		userDetail.setUserId(userId);
		userDetail.setUserName("ユーザ名");
		userDetail.setUserNameKana("ユーザメイ");
		userDetail.setMailaddress("test1@example.com");
		userDetail.setZipCd("2120013");
		userDetail.setPrefCd("14");
		userDetail.setAddress("川崎市川崎区駅前本町２６－１");
		userDetail.setTel("0312345678");
		userDetail.setBirthday("19810401");
		userDetail.setGender(GenderEnum.OTHER.getCodeAsInt());
		userDetail.setNote("備考\n");
		doReturn(Optional.of(userDetail)).when(userDetailService).findByUserId(eq(userId));

		// ユーザ詳細更新
		var updateUserDetail = new UserDetail();
		updateUserDetail.setUserId(userId);
		updateUserDetail.setUserName(form.getUserName());
		updateUserDetail.setUserNameKana(form.getUserNameKana());
		updateUserDetail.setMailaddress(form.getMailaddress());
		updateUserDetail.setZipCd(form.getZipCd());
		updateUserDetail.setPrefCd(form.getPrefCd());
		updateUserDetail.setAddress(form.getAddress());
		updateUserDetail.setTel(form.getTel());
		updateUserDetail.setBirthday(form.getBirthday());
		updateUserDetail.setGender(GenderEnum.MALE.getCodeAsInt());
		updateUserDetail.setNote(form.getNote());
		doReturn(Optional.of(updateUserDetail)).when(userDetailService)
				.saveAndFlush(eq(updateUserDetail));

		// ユーザ付加情報削除
		doReturn(1).when(userAdditionService).deleteByUserId(eq(userId));

		// ユーザ付加情報登録
		doReturn(Optional.of(new UserAddition())).when(userAdditionService).saveAndFlush(any());

		// テスト対象の実行
		val actual = userUpdateService.execute(form);

		assertEquals(userCd, actual, "戻り値が一致すること");

		// パラメータ検証
		verify(userAdditionService, times(2)).saveAndFlush(userAdditionCallbackCaptor.capture());
		val userAdditions = userAdditionCallbackCaptor.getAllValues();

		val userAddition1 = userAdditions.get(0);
		assertEquals(userId, userAddition1.getUserId(), "1件目：ユーザID");
		assertEquals("01", userAddition1.getAddition(), "1件目：付加情報");

		val userAddition2 = userAdditions.get(1);
		assertEquals(userId, userAddition2.getUserId(), "2件目：ユーザID");
		assertEquals("02", userAddition2.getAddition(), "2件目：付加情報");

		// ログの挙動確認
		verify(mockAppender, atLeastOnce()).doAppend(captorLoggingEvent.capture());

		val actualValues = captorLoggingEvent.getAllValues();
		assertEquals(1, actualValues.size(), "ログ出力の件数が一致すること");

		val actualEvent = actualValues.get(0);
		assertEquals(Level.INFO, actualEvent.getLevel(), "ログレベルが一致すること");

		val expectedMessage = "ユーザ更新が完了しました。 userId={}, userCd={}, updatedAt={}";
		assertEquals(expectedMessage, actualEvent.getMessage(), "ログメッセージが一致すること");
	}

	@Test
	public void testExecute_正常系_付加情報なしの場合() throws Exception {
		var logger = (Logger) LoggerFactory.getLogger(UserUpdateService.class);
		logger.addAppender(mockAppender);

		val userId = UUID.randomUUID();
		val userCd = "0000000001";
		val updatedAtInstant = Instant.parse("2022-01-01T00:00:00Z");
		val updatedAtMillisecond = String.valueOf(updatedAtInstant.toEpochMilli());
		val nowInstant = Instant.parse("2022-01-01T01:00:00Z");

		var form = new UserRegisterForm();
		form.setUserCd(userCd);
		form.setUserName("山田太郎");
		form.setUserNameKana("ヤマダタロウ");
		form.setMailaddress("test2@example.com");
		form.setZipCd("1600022");
		form.setPrefCd("13");
		form.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		form.setTel("0369103204");
		form.setBirthday("1980/04/01");
		form.setGender(GenderEnum.MALE.getCode());
		form.setAdditions(List.of());
		form.setNote("備考");
		form.setUpdatedAtMillisecond(updatedAtMillisecond);

		// 現在日時
		doReturn(Clock.fixed(nowInstant, ZoneId.of("Asia/Tokyo"))).when(clockConfig).clock();

		// ユーザ取得
		var user = new User();
		user.setId(userId);
		user.setUserCd(userCd);
		user.setCreatedAt(new Timestamp(0));
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(new Timestamp(updatedAtInstant.toEpochMilli()));
		user.setUpdatedStaffId(Constants.STAFF_ID);
		doReturn(Optional.of(user)).when(userService).findByUserCd(eq(userCd));

		// ユーザ更新
		var updateUser = new User();
		BeanUtils.copyProperties(user, updateUser);
		updateUser.setUpdatedAt(new Timestamp(nowInstant.toEpochMilli()));
		updateUser.setUpdatedStaffId(Constants.STAFF_ID);
		doReturn(Optional.of(updateUser)).when(userService).saveAndFlush(eq(updateUser));

		// ユーザ詳細取得
		var userDetail = new UserDetail();
		userDetail.setUserId(userId);
		userDetail.setUserName("ユーザ名");
		userDetail.setUserNameKana("ユーザメイ");
		userDetail.setMailaddress("test1@example.com");
		userDetail.setZipCd("2120013");
		userDetail.setPrefCd("14");
		userDetail.setAddress("川崎市川崎区駅前本町２６－１");
		userDetail.setTel("0312345678");
		userDetail.setBirthday("19810401");
		userDetail.setGender(GenderEnum.OTHER.getCodeAsInt());
		userDetail.setNote("備考\n");
		doReturn(Optional.of(userDetail)).when(userDetailService).findByUserId(eq(userId));

		// ユーザ詳細更新
		var updateUserDetail = new UserDetail();
		updateUserDetail.setUserId(userId);
		updateUserDetail.setUserName(form.getUserName());
		updateUserDetail.setUserNameKana(form.getUserNameKana());
		updateUserDetail.setMailaddress(form.getMailaddress());
		updateUserDetail.setZipCd(form.getZipCd());
		updateUserDetail.setPrefCd(form.getPrefCd());
		updateUserDetail.setAddress(form.getAddress());
		updateUserDetail.setTel(form.getTel());
		updateUserDetail.setBirthday(form.getBirthday());
		updateUserDetail.setGender(GenderEnum.MALE.getCodeAsInt());
		updateUserDetail.setNote(form.getNote());
		doReturn(Optional.of(updateUserDetail)).when(userDetailService)
				.saveAndFlush(eq(updateUserDetail));

		// ユーザ付加情報削除
		doReturn(1).when(userAdditionService).deleteByUserId(eq(userId));

		// テスト対象の実行
		val actual = userUpdateService.execute(form);

		assertEquals(userCd, actual, "戻り値が一致すること");

		// 付加情報登録が実行されないこと
		verify(userAdditionService, never()).saveAndFlush(any());

		// ログの挙動確認
		verify(mockAppender, atLeastOnce()).doAppend(captorLoggingEvent.capture());

		val actualValues = captorLoggingEvent.getAllValues();
		assertEquals(1, actualValues.size(), "ログ出力の件数が一致すること");

		val actualEvent = actualValues.get(0);
		assertEquals(Level.INFO, actualEvent.getLevel(), "ログレベルが一致すること");

		val expectedMessage = "ユーザ更新が完了しました。 userId={}, userCd={}, updatedAt={}";
		assertEquals(expectedMessage, actualEvent.getMessage(), "ログメッセージが一致すること");
	}

	@Test
	public void testExecute_正常系_性別が未入力の場合() throws Exception {
		var logger = (Logger) LoggerFactory.getLogger(UserUpdateService.class);
		logger.addAppender(mockAppender);

		val userId = UUID.randomUUID();
		val userCd = "0000000001";
		val updatedAtInstant = Instant.parse("2022-01-01T00:00:00Z");
		val updatedAtMillisecond = String.valueOf(updatedAtInstant.toEpochMilli());
		val nowInstant = Instant.parse("2022-01-01T01:00:00Z");

		var form = new UserRegisterForm();
		form.setUserCd(userCd);
		form.setUserName("山田太郎");
		form.setUserNameKana("ヤマダタロウ");
		form.setMailaddress("test2@example.com");
		form.setZipCd("1600022");
		form.setPrefCd("13");
		form.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		form.setTel("0369103204");
		form.setBirthday("1980/04/01");
		form.setGender(null);
		form.setAdditions(List.of());
		form.setNote("備考");
		form.setUpdatedAtMillisecond(updatedAtMillisecond);

		// 現在日時
		doReturn(Clock.fixed(nowInstant, ZoneId.of("Asia/Tokyo"))).when(clockConfig).clock();

		// ユーザ取得
		var user = new User();
		user.setId(userId);
		user.setUserCd(userCd);
		user.setCreatedAt(new Timestamp(0));
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(new Timestamp(updatedAtInstant.toEpochMilli()));
		user.setUpdatedStaffId(Constants.STAFF_ID);
		doReturn(Optional.of(user)).when(userService).findByUserCd(eq(userCd));

		// ユーザ更新
		var updateUser = new User();
		BeanUtils.copyProperties(user, updateUser);
		updateUser.setUpdatedAt(new Timestamp(nowInstant.toEpochMilli()));
		updateUser.setUpdatedStaffId(Constants.STAFF_ID);
		doReturn(Optional.of(updateUser)).when(userService).saveAndFlush(eq(updateUser));

		// ユーザ詳細取得
		var userDetail = new UserDetail();
		userDetail.setUserId(userId);
		userDetail.setUserName("ユーザ名");
		userDetail.setUserNameKana("ユーザメイ");
		userDetail.setMailaddress("test1@example.com");
		userDetail.setZipCd("2120013");
		userDetail.setPrefCd("14");
		userDetail.setAddress("川崎市川崎区駅前本町２６－１");
		userDetail.setTel("0312345678");
		userDetail.setBirthday("19810401");
		userDetail.setGender(GenderEnum.OTHER.getCodeAsInt());
		userDetail.setNote("備考\n");
		doReturn(Optional.of(userDetail)).when(userDetailService).findByUserId(eq(userId));

		// ユーザ詳細更新
		var updateUserDetail = new UserDetail();
		updateUserDetail.setUserId(userId);
		updateUserDetail.setUserName(form.getUserName());
		updateUserDetail.setUserNameKana(form.getUserNameKana());
		updateUserDetail.setMailaddress(form.getMailaddress());
		updateUserDetail.setZipCd(form.getZipCd());
		updateUserDetail.setPrefCd(form.getPrefCd());
		updateUserDetail.setAddress(form.getAddress());
		updateUserDetail.setTel(form.getTel());
		updateUserDetail.setBirthday(form.getBirthday());
		updateUserDetail.setGender(null);
		updateUserDetail.setNote(form.getNote());
		doReturn(Optional.of(updateUserDetail)).when(userDetailService)
				.saveAndFlush(eq(updateUserDetail));

		// ユーザ付加情報削除
		doReturn(1).when(userAdditionService).deleteByUserId(eq(userId));

		// テスト対象の実行
		val actual = userUpdateService.execute(form);

		assertEquals(userCd, actual, "戻り値が一致すること");

		// 付加情報登録が実行されないこと
		verify(userAdditionService, never()).saveAndFlush(any());

		// ログの挙動確認
		verify(mockAppender, atLeastOnce()).doAppend(captorLoggingEvent.capture());

		val actualValues = captorLoggingEvent.getAllValues();
		assertEquals(1, actualValues.size(), "ログ出力の件数が一致すること");

		val actualEvent = actualValues.get(0);
		assertEquals(Level.INFO, actualEvent.getLevel(), "ログレベルが一致すること");

		val expectedMessage = "ユーザ更新が完了しました。 userId={}, userCd={}, updatedAt={}";
		assertEquals(expectedMessage, actualEvent.getMessage(), "ログメッセージが一致すること");
	}

	@Test
	public void testExecute_異常系_ユーザ情報が存在しない場合() {
		val userCd = "0000000001";
		val updatedAtInstant = Instant.parse("2022-01-01T00:00:00Z");
		val updatedAtMillisecond = String.valueOf(updatedAtInstant.toEpochMilli());
		val nowInstant = Instant.parse("2022-01-01T01:00:00Z");

		var form = new UserRegisterForm();
		form.setUserCd(userCd);
		form.setUserName("ユーザ名");
		form.setUserNameKana("ユーザメイ");
		form.setMailaddress("test@example.com");
		form.setZipCd("1600022");
		form.setPrefCd("13");
		form.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		form.setTel("0369103204");
		form.setBirthday("1980/04/01");
		form.setGender(null);
		form.setAdditions(List.of());
		form.setNote("備考");
		form.setUpdatedAtMillisecond(updatedAtMillisecond);

		// 現在日時
		doReturn(Clock.fixed(nowInstant, ZoneId.of("Asia/Tokyo"))).when(clockConfig).clock();

		// ユーザ取得
		doReturn(Optional.empty()).when(userService).findByUserCd(eq(userCd));

		// テスト対象の実行兼例外検証
		assertThrows(NotFoundException.class, () -> userUpdateService.execute(form));
	}

	@Test
	public void testExecute_異常系_楽観ロックエラーの場合() {
		val userCd = "0000000001";
		val updatedAtInstant = Instant.parse("2022-01-01T00:00:00Z");
		val updatedAtMillisecond = String.valueOf(updatedAtInstant.toEpochMilli());
		val nowInstant = Instant.parse("2022-01-01T01:00:00Z");

		var form = new UserRegisterForm();
		form.setUserCd(userCd);
		form.setUserName("ユーザ名");
		form.setUserNameKana("ユーザメイ");
		form.setMailaddress("test@example.com");
		form.setZipCd("1600022");
		form.setPrefCd("13");
		form.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		form.setTel("0369103204");
		form.setBirthday("1980/04/01");
		form.setGender(null);
		form.setAdditions(List.of());
		form.setNote("備考");
		form.setUpdatedAtMillisecond(updatedAtMillisecond);

		// 現在日時
		doReturn(Clock.fixed(nowInstant, ZoneId.of("Asia/Tokyo"))).when(clockConfig).clock();

		// ユーザ取得
		var user = new User();
		user.setCreatedAt(new Timestamp(0));
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(new Timestamp(0));
		user.setUpdatedStaffId(Constants.STAFF_ID);
		doReturn(Optional.of(user)).when(userService).findByUserCd(eq(userCd));

		// テスト対象の実行兼例外検証
		assertThrows(OptimisticErrorException.class, () -> userUpdateService.execute(form));
	}

	@Test
	public void testExecute_異常系_ユーザ詳細が存在しない場合() {
		val userId = UUID.randomUUID();
		val userCd = "0000000001";
		val updatedAtInstant = Instant.parse("2022-01-01T00:00:00Z");
		val updatedAtMillisecond = String.valueOf(updatedAtInstant.toEpochMilli());
		val nowInstant = Instant.parse("2022-01-01T01:00:00Z");

		var form = new UserRegisterForm();
		form.setUserCd(userCd);
		form.setUserName("ユーザ名");
		form.setUserNameKana("ユーザメイ");
		form.setMailaddress("test@example.com");
		form.setZipCd("1600022");
		form.setPrefCd("13");
		form.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		form.setTel("0369103204");
		form.setBirthday("1980/04/01");
		form.setGender(null);
		form.setAdditions(List.of());
		form.setNote("備考");
		form.setUpdatedAtMillisecond(updatedAtMillisecond);

		// 現在日時
		doReturn(Clock.fixed(nowInstant, ZoneId.of("Asia/Tokyo"))).when(clockConfig).clock();

		// ユーザ取得
		var user = new User();
		user.setId(userId);
		user.setUserCd(userCd);
		user.setCreatedAt(new Timestamp(0));
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(new Timestamp(updatedAtInstant.toEpochMilli()));
		user.setUpdatedStaffId(Constants.STAFF_ID);
		doReturn(Optional.of(user)).when(userService).findByUserCd(eq(userCd));

		// ユーザ更新
		var updateUser = new User();
		BeanUtils.copyProperties(user, updateUser);
		updateUser.setUpdatedAt(new Timestamp(nowInstant.toEpochMilli()));
		updateUser.setUpdatedStaffId(Constants.STAFF_ID);
		doReturn(Optional.of(updateUser)).when(userService).saveAndFlush(eq(updateUser));

		// ユーザ詳細取得
		doReturn(Optional.empty()).when(userDetailService).findByUserId(eq(userId));

		// テスト対象の実行兼例外検証
		assertThrows(NotFoundException.class, () -> userUpdateService.execute(form));
	}
}
