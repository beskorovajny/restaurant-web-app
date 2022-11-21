package com.october.to.finish.app.web.restaurant.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PasswordEncryptionUtilTest {

    @Test
    void encryptToSHA256Test() {
        String password1 = "sBBuE%0&V9h6";
        String shaFor1 = "d9bbfb3d7aa0fcbd61cb0cfe606f80e444a36a0c0e62a54265255f54145545fb";
        assertEquals(shaFor1, PasswordEncryptionUtil.getEncrypted(password1));
        assertEquals(64, PasswordEncryptionUtil.getEncrypted(password1).length());
        String password2 = "PAssWOrd";
        String shaFor2 = "39e2cd784c762eaa71cf4503f4db770aeaa1da06f341621956f44decceb14293";
        assertEquals(shaFor2, PasswordEncryptionUtil.getEncrypted(password2));
        assertEquals(64, PasswordEncryptionUtil.getEncrypted(password2).length());
        String password3 = "U&nCjnNAHUvyQMtP";
        String shaFor3 = "f9a2dbc1912cdb806acece9781c97ea64129b3449e2e6519f5a7b9d723f13223";
        assertEquals(shaFor3, PasswordEncryptionUtil.getEncrypted(password3));
        assertEquals(64, PasswordEncryptionUtil.getEncrypted(password3).length());
    }

    @Test
    void wrongInputTest() {
        assertThrows(IllegalArgumentException.class, () -> PasswordEncryptionUtil.getEncrypted(null));
    }

}