package com.aceward.usermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
 * {@link UserRegisterService} のテストクラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class UserRegisterServiceTest {

	/** テスト対象サービス */
	@InjectMocks
	private UserRegisterService userRegisterService;

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
	public void testExecute_正常系_付加情報ありの場合() {
		var logger = (Logger) LoggerFactory.getLogger(UserRegisterService.class);
		logger.addAppender(mockAppender);

		val userId = UUID.randomUUID();
		val userCd = "0000000001";
		val nowInstant = Instant.parse("2022-01-01T00:00:00Z");
		val now = new Timestamp(nowInstant.toEpochMilli());

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

		// 現在日時
		doReturn(Clock.fixed(nowInstant, ZoneId.of("Asia/Tokyo"))).when(clockConfig).clock();

		// ユーザ登録
		var user = new User();
		user.setCreatedAt(now);
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(now);
		user.setUpdatedStaffId(Constants.STAFF_ID);

		var resultUser = new User();
		BeanUtils.copyProperties(user, resultUser);
		resultUser.setId(userId);
		resultUser.setUserCd(userCd);

		doReturn(Optional.of(resultUser)).when(userService).saveAndFlush(eq(user));

		// ユーザ詳細登録
		var userDetail = new UserDetail();
		userDetail.setUserId(userId);
		userDetail.setUserName(form.getUserName());
		userDetail.setUserNameKana(form.getUserNameKana());
		userDetail.setMailaddress(form.getMailaddress());
		userDetail.setZipCd(form.getZipCd());
		userDetail.setPrefCd(form.getPrefCd());
		userDetail.setAddress(form.getAddress());
		userDetail.setTel(form.getTel());
		userDetail.setBirthday(form.getBirthday());
		userDetail.setGender(GenderEnum.MALE.getCodeAsInt());
		userDetail.setNote(form.getNote());

		var reseultUserDetail = new UserDetail();
		BeanUtils.copyProperties(userDetail, reseultUserDetail);
		reseultUserDetail.setUserId(userId);

		doReturn(Optional.of(reseultUserDetail)).when(userDetailService)
				.saveAndFlush(eq(userDetail));

		// ユーザ付加情報登録
		doReturn(Optional.of(new UserAddition())).when(userAdditionService).saveAndFlush(any());

		// テスト対象の実行
		val actual = userRegisterService.execute(form);

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

		val expectedMessage = "ユーザ登録が完了しました。 userId={}, userCd={}, createdAt={}";
		assertEquals(expectedMessage, actualEvent.getMessage(), "ログメッセージが一致すること");
	}

	@Test
	public void testExecute_正常系_付加情報なしの場合() {
		var logger = (Logger) LoggerFactory.getLogger(UserRegisterService.class);
		logger.addAppender(mockAppender);

		val userId = UUID.randomUUID();
		val userCd = "0000000001";
		val nowInstant = Instant.parse("2022-01-01T00:00:00Z");
		val now = new Timestamp(nowInstant.toEpochMilli());

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
		form.setAdditions(List.of());
		form.setNote("備考");

		// 現在日時
		doReturn(Clock.fixed(nowInstant, ZoneId.of("Asia/Tokyo"))).when(clockConfig).clock();

		// ユーザ登録
		var user = new User();
		user.setCreatedAt(now);
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(now);
		user.setUpdatedStaffId(Constants.STAFF_ID);

		var resultUser = new User();
		BeanUtils.copyProperties(user, resultUser);
		resultUser.setId(userId);
		resultUser.setUserCd(userCd);

		doReturn(Optional.of(resultUser)).when(userService).saveAndFlush(eq(user));

		// ユーザ詳細登録
		var userDetail = new UserDetail();
		userDetail.setUserId(userId);
		userDetail.setUserName(form.getUserName());
		userDetail.setUserNameKana(form.getUserNameKana());
		userDetail.setMailaddress(form.getMailaddress());
		userDetail.setZipCd(form.getZipCd());
		userDetail.setPrefCd(form.getPrefCd());
		userDetail.setAddress(form.getAddress());
		userDetail.setTel(form.getTel());
		userDetail.setBirthday(form.getBirthday());
		userDetail.setGender(GenderEnum.MALE.getCodeAsInt());
		userDetail.setNote(form.getNote());

		var reseultUserDetail = new UserDetail();
		BeanUtils.copyProperties(userDetail, reseultUserDetail);
		reseultUserDetail.setUserId(userId);

		doReturn(Optional.of(reseultUserDetail)).when(userDetailService)
				.saveAndFlush(eq(userDetail));

		// テスト対象の実行
		val actual = userRegisterService.execute(form);

		assertEquals(userCd, actual, "戻り値が一致すること");

		// 付加情報登録が実行されないこと
		verify(userAdditionService, never()).saveAndFlush(any());

		// ログの挙動確認
		verify(mockAppender, atLeastOnce()).doAppend(captorLoggingEvent.capture());

		val actualValues = captorLoggingEvent.getAllValues();
		assertEquals(1, actualValues.size(), "ログ出力の件数が一致すること");

		val actualEvent = actualValues.get(0);
		assertEquals(Level.INFO, actualEvent.getLevel(), "ログレベルが一致すること");

		val expectedMessage = "ユーザ登録が完了しました。 userId={}, userCd={}, createdAt={}";
		assertEquals(expectedMessage, actualEvent.getMessage(), "ログメッセージが一致すること");
	}

	@Test
	public void testExecute_正常系_性別が未入力の場合() {
		var logger = (Logger) LoggerFactory.getLogger(UserRegisterService.class);
		logger.addAppender(mockAppender);

		val userId = UUID.randomUUID();
		val userCd = "0000000001";
		val nowInstant = Instant.parse("2022-01-01T00:00:00Z");
		val now = new Timestamp(nowInstant.toEpochMilli());

		var form = new UserRegisterForm();
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

		// 現在日時
		doReturn(Clock.fixed(nowInstant, ZoneId.of("Asia/Tokyo"))).when(clockConfig).clock();

		// ユーザ登録
		var user = new User();
		user.setCreatedAt(now);
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(now);
		user.setUpdatedStaffId(Constants.STAFF_ID);

		var resultUser = new User();
		BeanUtils.copyProperties(user, resultUser);
		resultUser.setId(userId);
		resultUser.setUserCd(userCd);

		doReturn(Optional.of(resultUser)).when(userService).saveAndFlush(eq(user));

		// ユーザ詳細登録
		var userDetail = new UserDetail();
		userDetail.setUserId(userId);
		userDetail.setUserName(form.getUserName());
		userDetail.setUserNameKana(form.getUserNameKana());
		userDetail.setMailaddress(form.getMailaddress());
		userDetail.setZipCd(form.getZipCd());
		userDetail.setPrefCd(form.getPrefCd());
		userDetail.setAddress(form.getAddress());
		userDetail.setTel(form.getTel());
		userDetail.setBirthday(form.getBirthday());
		userDetail.setGender(null);
		userDetail.setNote(form.getNote());

		var reseultUserDetail = new UserDetail();
		BeanUtils.copyProperties(userDetail, reseultUserDetail);
		reseultUserDetail.setUserId(userId);

		doReturn(Optional.of(reseultUserDetail)).when(userDetailService)
				.saveAndFlush(eq(userDetail));

		// テスト対象の実行
		val actual = userRegisterService.execute(form);

		assertEquals(userCd, actual, "戻り値が一致すること");

		// 付加情報登録が実行されないこと
		verify(userAdditionService, never()).saveAndFlush(any());

		// ログの挙動確認
		verify(mockAppender, atLeastOnce()).doAppend(captorLoggingEvent.capture());

		val actualValues = captorLoggingEvent.getAllValues();
		assertEquals(1, actualValues.size(), "ログ出力の件数が一致すること");

		val actualEvent = actualValues.get(0);
		assertEquals(Level.INFO, actualEvent.getLevel(), "ログレベルが一致すること");

		val expectedMessage = "ユーザ登録が完了しました。 userId={}, userCd={}, createdAt={}";
		assertEquals(expectedMessage, actualEvent.getMessage(), "ログメッセージが一致すること");
	}
}
