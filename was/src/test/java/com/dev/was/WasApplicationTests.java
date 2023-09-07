package com.dev.was;

import static org.junit.jupiter.api.Assertions.*;


import com.dev.was.controller.ApiException;
import com.dev.was.dto.AccountDto;
import com.dev.was.repository.AccountRepository;
import com.dev.was.repository.UserRepository;
import com.dev.was.service.AccountService;
import com.dev.was.service.SerialService;
import com.dev.was.service.UserService;
import com.dev.was.util.DBUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class WasApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private SerialService service;

	@Test
	void SerialTest() throws Exception {
		service.sendSerialMsg("HI TEST @");
	}

	@Test
	void UserTest() throws Exception {
		assertThrows(ApiException.class, () -> {
			userService.getUserInfo("123");
		});

		assertEquals("eomgr55@naver.com",
			userService.getUserInfo("QgOzdZCgQ8ZaWCR8fB3G::3K4KV1mds2OY6ws4LRGi_u-gRxI4kptkTnTnNnnFNp8").getEmail()
		);
	}

	@Test
	void AccountTest() throws Exception {
		String date = "20230808";
		String memo = "test";
		Long money = 5000L;
		String time = "10:00";
		String category = "category";
		String type = "income";

		AccountDto addAccountDto = accountService.saveAccount(
				null, "test", DBUtil.toLocalDate(date), time, memo, money, category, type
		);
		assertNotNull(addAccountDto.getId());

		AccountDto updateAccountDto = accountService.saveAccount(
				addAccountDto.getId(), "test", DBUtil.toLocalDate(date), time, "change memo", money, category, type
		);
		assertNotNull(addAccountDto.getId());

		accountService.deleteAccount(updateAccountDto.getId());

		List<AccountDto> list = accountService.getAccountsByUserIdAndDateBetween(
				"test", DBUtil.toLocalDate("20230801"), DBUtil.toLocalDate("20230831"));
		assertTrue(list.isEmpty());
	}

	private static final Logger logger = LoggerFactory.getLogger(WasApplicationTests.class);
}
