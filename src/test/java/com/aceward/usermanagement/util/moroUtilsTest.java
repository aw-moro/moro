package com.aceward.usermanagement.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import lombok.val;

public class moroUtilsTest {

    @Test
    public void testisuserNamecheck_52() {
        val actual = ValidateUtils.isuserNamecheck("0123456789012345678901234567890123456789012345678901");

        assertFalse(actual, "期待値が一致すること");
    }

    @Test
    public void testisuserNamecheck_51() {
        val actual = ValidateUtils.isuserNamecheck("012345678901234567890123456789012345678901234567890");

        assertTrue(actual, "期待値が一致すること");
    }

    @Test
    public void testisuserNamecheck_50() {
        val actual = ValidateUtils.isuserNamecheck("01234567890123456789012345678901234567890123456789");

        assertTrue(actual, "期待値が一致すること");
    }

    @Test
    public void testisuserNamecheck_() {
        val actual = ValidateUtils.isuserNamecheck("");

        assertFalse(actual, "期待値が一致すること");
    }

    @Test
    public void testisuserNamecheckequal_brank() {
        val actual = ValidateUtils.isuserNamecheckequal("");

        assertEquals(actual, "");
    }

    @Test
    public void testisuserNamecheckequal_UserA() {
        val actual = ValidateUtils.isuserNamecheckequal("A");

        assertEquals(actual, "UserA");
    }

    @Test
    public void testisuserNamecheckequal_UserB() {
        val actual = ValidateUtils.isuserNamecheckequal("B");

        assertEquals(actual, "UserB");
    }

    @Test
    public void testValidateUtilsConstructor() {
        // コンストラクタを呼び出すだけのテスト
        new ValidateUtils();
    }

}
