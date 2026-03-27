package com.aceward.usermanagement.service.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.List;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.aceward.usermanagement.constant.Constants;
import com.aceward.usermanagement.dao.domain.User;
import com.aceward.usermanagement.dao.domain.UserAddition;
import com.aceward.usermanagement.dao.domain.UserDetail;
import com.aceward.usermanagement.dao.dto.UserListDto;
import com.aceward.usermanagement.dao.dto.UserSearchDto;
import com.aceward.usermanagement.enums.AdditionEnum;
import com.aceward.usermanagement.enums.GenderEnum;
import lombok.val;
import lombok.var;

/**
 * {@link UserDetailService} のMapper専用テストクラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserDetailServiceForMapperTest {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private UserAdditionService userAdditionService;

	@Test
	public void testCountByUserSearchDto_検索条件が未設定_検索結果あり() {
		// テストデータ登録
		registerUser1();
		registerUser2();
		registerUser3();

		var dto = new UserSearchDto();
		dto.setPage(0L);
		dto.setSize(1L);

		// テスト対象の実行
		val actual = userDetailService.countByUserSearchDto(dto);

		assertEquals(3, actual, "戻り値が一致すること");
	}

	@Test
	public void testCountByUserSearchDto_検索条件が全入力_検索結果あり() {
		// テストデータ登録
		registerUser1();
		registerUser2();
		registerUser3();

		var dto = new UserSearchDto();
		dto.setUserName("山田");
		dto.setMailaddress("test");
		dto.setPrefCd("13");
		dto.setGender(GenderEnum.MALE.getCode());
		dto.setAdditions(List.of("01"));
		dto.setPage(0L);
		dto.setSize(1L);

		// テスト対象の実行
		val actual = userDetailService.countByUserSearchDto(dto);

		assertEquals(1, actual, "戻り値が一致すること");
	}

	@Test
	public void testCountByUserSearchDto_検索条件が全入力_検索結果なし() {
		// テストデータ登録
		registerUser1();
		registerUser2();
		registerUser3();

		var dto = new UserSearchDto();
		dto.setUserName("山田");
		dto.setMailaddress("test");
		dto.setPrefCd("01");
		dto.setGender(GenderEnum.MALE.getCode());
		dto.setAdditions(List.of("01"));
		dto.setPage(0L);
		dto.setSize(1L);

		// テスト対象の実行
		val actual = userDetailService.countByUserSearchDto(dto);

		assertEquals(0, actual, "戻り値が一致すること");
	}

	@Test
	public void testFindByUserSearchDto_検索条件が未設定_ページャ1件縛り_1ページ目の検索結果あり() {
		// テストデータ登録
		val expected1 = registerUser1();
		registerUser2();
		registerUser3();

		var dto = new UserSearchDto();
		// ページ番号は0始まり
		dto.setPage(0L);
		dto.setSize(1L);

		// テスト対象の実行
		val actual = userDetailService.findByUserSearchDto(dto);

		assertEquals(1, actual.size(), "1件のみ取得できること");
		assertEquals(expected1, actual.get(0), "1ページ目のユーザ1件が取得できること");
	}

	@Test
	public void testFindByUserSearchDto_検索条件が未設定_ページャ1件縛り_2ページ目の検索結果あり() {
		// テストデータ登録
		registerUser1();
		val expected2 = registerUser2();
		registerUser3();

		var dto = new UserSearchDto();
		// ページ番号は0始まり
		dto.setPage(1L);
		dto.setSize(1L);

		// テスト対象の実行
		val actual = userDetailService.findByUserSearchDto(dto);

		assertEquals(1, actual.size(), "1件のみ取得できること");
		assertEquals(expected2, actual.get(0), "2ページ目のユーザ1件が取得できること");
	}

	@Test
	public void testFindByUserSearchDto_検索条件が未設定_ページャ縛りなし_検索結果あり() {
		// テストデータ登録
		val expected1 = registerUser1();
		val expected2 = registerUser2();
		val expected3 = registerUser3();

		var dto = new UserSearchDto();

		// テスト対象の実行
		val actual = userDetailService.findByUserSearchDto(dto);

		assertEquals(3, actual.size(), "全件取得できること");
		assertEquals(expected1, actual.get(0), "1件目のユーザ情報");
		assertEquals(expected2, actual.get(1), "2件目のユーザ情報");
		assertEquals(expected3, actual.get(2), "3件目のユーザ情報");
	}

	@Test
	public void testFindByUserSearchDto_検索条件が全入力_検索結果あり() {
		// テストデータ登録
		val expected1 = registerUser1();
		registerUser2();
		registerUser3();

		var dto = new UserSearchDto();
		dto.setUserName("山田");
		dto.setMailaddress("test");
		dto.setPrefCd("13");
		dto.setGender(GenderEnum.MALE.getCode());
		dto.setAdditions(List.of("01"));
		dto.setPage(0L);
		dto.setSize(5L);

		// テスト対象の実行
		val actual = userDetailService.findByUserSearchDto(dto);

		assertEquals(1, actual.size(), "件数が一致すること");
		assertEquals(expected1, actual.get(0), "検索条件に一致したユーザ情報が取得できること");
	}

	@Test
	public void testFindByUserSearchDto_検索条件が全入力_検索結果なし() {
		// テストデータ登録
		registerUser1();
		registerUser2();
		registerUser3();

		var dto = new UserSearchDto();
		dto.setUserName("山田");
		dto.setMailaddress("test");
		dto.setPrefCd("01");
		dto.setGender(GenderEnum.MALE.getCode());
		dto.setAdditions(List.of("01"));
		dto.setPage(0L);
		dto.setSize(5L);

		// テスト対象の実行
		val actual = userDetailService.findByUserSearchDto(dto);

		assertEquals(0, actual.size(), "件数が一致すること");
	}

	/**
	 * 最大データのユーザ情報1を登録する。<br>
	 * その他情報：全データ入力あり。
	 * 
	 * @return 期待値用のユーザ一覧DTO
	 */
	private UserListDto registerUser1() {
		var user = new User();
		user.setCreatedAt(new Timestamp(0));
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(new Timestamp(0));
		user.setUpdatedStaffId(Constants.STAFF_ID);
		val resultUser = userService.saveAndFlush(user);

		var userDetail = new UserDetail();
		userDetail.setUserId(resultUser.get().getId());
		userDetail.setUserName("山田太郎");
		userDetail.setUserNameKana("ヤマダタロウ");
		userDetail.setMailaddress("test1@example.com");
		userDetail.setZipCd("1600022");
		userDetail.setPrefCd("13");
		userDetail.setAddress("新宿区新宿2丁目12番13号 新宿アントレサロンビル2階");
		userDetail.setTel("0369103204");
		userDetail.setBirthday("1980/04/01");
		userDetail.setGender(GenderEnum.MALE.getCodeAsInt());
		userDetail.setNote("備考1");
		userDetailService.saveAndFlush(userDetail);

		var userAddition1 = new UserAddition();
		userAddition1.setUserId(resultUser.get().getId());
		userAddition1.setAddition(AdditionEnum.CODE_01.getCode());
		userAdditionService.saveAndFlush(userAddition1);

		var userAddition2 = new UserAddition();
		userAddition2.setUserId(resultUser.get().getId());
		userAddition2.setAddition(AdditionEnum.CODE_02.getCode());
		userAdditionService.saveAndFlush(userAddition2);

		// 期待値用のユーザ一覧DTO
		var userListDto = new UserListDto();
		userListDto.setUserId(resultUser.get().getId().toString());
		userListDto.setUserCd(resultUser.get().getUserCd());
		userListDto.setUserName(userDetail.getUserName());
		userListDto.setMailaddress(userDetail.getMailaddress());
		userListDto.setPrefCd(userDetail.getPrefCd());
		userListDto.setAddress(userDetail.getAddress());
		userListDto.setGender(userDetail.getGender());
		userListDto.setAdditions("01,02");
		userListDto.setNote(userDetail.getNote());

		return userListDto;
	}

	/**
	 * 最小データのユーザ情報2を登録する。<br>
	 * その他情報：性別・備考・付加情報の入力なし。
	 * 
	 * @return 期待値用のユーザ一覧DTO
	 */
	private UserListDto registerUser2() {
		var user = new User();
		user.setCreatedAt(new Timestamp(1));
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(new Timestamp(1));
		user.setUpdatedStaffId(Constants.STAFF_ID);
		val resultUser = userService.saveAndFlush(user);

		var userDetail = new UserDetail();
		userDetail.setUserId(resultUser.get().getId());
		userDetail.setUserName("田中花子");
		userDetail.setUserNameKana("タナカハナコ");
		userDetail.setMailaddress("test2@example.com");
		userDetail.setZipCd("2100007");
		userDetail.setPrefCd("14");
		userDetail.setAddress("川崎市川崎区駅前本町２６－１");
		userDetail.setTel("0441234567");
		userDetail.setBirthday("1980/05/01");
		userDetail.setGender(null);
		userDetail.setNote(null);
		userDetailService.saveAndFlush(userDetail);

		// 期待値用のユーザ一覧DTO
		var userListDto = new UserListDto();
		userListDto.setUserId(resultUser.get().getId().toString());
		userListDto.setUserCd(resultUser.get().getUserCd());
		userListDto.setUserName(userDetail.getUserName());
		userListDto.setMailaddress(userDetail.getMailaddress());
		userListDto.setPrefCd(userDetail.getPrefCd());
		userListDto.setAddress(userDetail.getAddress());
		userListDto.setGender(userDetail.getGender());
		userListDto.setAdditions(null);
		userListDto.setNote(userDetail.getNote());

		return userListDto;
	}

	/**
	 * 削除済みのユーザ情報3を登録する。<br>
	 * その他情報：全データ入力あり。
	 * 
	 * @return 期待値用のユーザ一覧DTO
	 */
	private UserListDto registerUser3() {
		var user = new User();
		user.setCreatedAt(new Timestamp(2));
		user.setCreatedStaffId(Constants.STAFF_ID);
		user.setUpdatedAt(new Timestamp(2));
		user.setUpdatedStaffId(Constants.STAFF_ID);
		user.setDeletedAt(new Timestamp(2));
		user.setDeletedStaffId(Constants.STAFF_ID);
		val resultUser = userService.saveAndFlush(user);

		var userDetail = new UserDetail();
		userDetail.setUserId(resultUser.get().getId());
		userDetail.setUserName("佐藤次郎");
		userDetail.setUserNameKana("サトウジロウ");
		userDetail.setMailaddress("test3@example.com");
		userDetail.setZipCd("2200011");
		userDetail.setPrefCd("14");
		userDetail.setAddress("横浜市西区高島２丁目");
		userDetail.setTel("0451234567");
		userDetail.setBirthday("1980/06/01");
		userDetail.setGender(GenderEnum.OTHER.getCodeAsInt());
		userDetail.setNote("備考3");
		userDetailService.saveAndFlush(userDetail);

		var userAddition = new UserAddition();
		userAddition.setUserId(resultUser.get().getId());
		userAddition.setAddition(AdditionEnum.CODE_03.getCode());
		userAdditionService.saveAndFlush(userAddition);

		// 期待値用のユーザ一覧DTO
		var userListDto = new UserListDto();
		userListDto.setUserId(resultUser.get().getId().toString());
		userListDto.setUserCd(resultUser.get().getUserCd());
		userListDto.setUserName(userDetail.getUserName());
		userListDto.setMailaddress(userDetail.getMailaddress());
		userListDto.setPrefCd(userDetail.getPrefCd());
		userListDto.setAddress(userDetail.getAddress());
		userListDto.setGender(userDetail.getGender());
		userListDto.setAdditions("03");
		userListDto.setNote(userDetail.getNote());

		return userListDto;
	}
}
