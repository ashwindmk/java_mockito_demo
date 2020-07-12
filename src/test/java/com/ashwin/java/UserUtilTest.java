package com.ashwin.java;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserUtilTest {
    @Before
    public void setUp() {
    }

    @Mock
    String url;

    @Test
    public void isAdultTest() {
        UserService userService = Mockito.mock(UserService.class);

        UserUtil userUtil = new UserUtil(userService);

        assertEquals(true, userUtil.isAdult(20));
    }

    @Test
    public void getRandomUsernameTest() {
        UserService userService = Mockito.mock(UserService.class);
        when(userService.getRandomUsername()).thenReturn("mock_user");

        UserUtil userUtil = new UserUtil(userService);

        assertNotNull(userUtil.getRandomUsername());

        verify(userService).getRandomUsername();
    }

    @Test
    public void getRandomUserNameWithPre() {
        final String mockPre = "smart";
        final String mockUsername = "mock_user";

        UserService userService = Mockito.mock(UserService.class);
        when(userService.getRandomUsername()).thenReturn(mockUsername);
        UserUtil userUtil = new UserUtil(userService);

        int count = 0;

        count++;
        assertTrue(userUtil.getRandomUsername(mockPre).startsWith(mockPre));

        count++;
        assertEquals( mockPre + "_" + mockUsername, userUtil.getRandomUsername(mockPre));

        verify(userService, times(count)).getRandomUsername();
    }

    @Test
    public void getRandomPasswordTest() {
        UserService userService = Mockito.mock(UserService.class);

        UserUtil userUtil = new UserUtil(userService);

        when(userService.getRandomPassword(5)).thenReturn("awd4g");

        assertNotNull(userUtil.getRandomPassword(5));

        verify(userService).getRandomPassword(5);
    }

    @Test
    public void loginTest() {
        String username = "ashwin";
        String password = "pass123";

        UserService service = new UserService(url);
        UserService userService = Mockito.spy(service);
        when(userService.validateUser(username, password)).thenReturn(true);
        UserUtil userUtil = new UserUtil(userService);

        assertEquals(true, userUtil.login(username, password));
    }

    @Test
    public void isValidTest() {
        UserService service = new UserService(url);
        UserService userService = Mockito.spy(service);

        UserUtil userUtil = new UserUtil(userService);

        assertEquals("Valid test", true, userUtil.isValid("green_lantern"));

        assertEquals("Short length test", false, userUtil.isValid("asd"));

        assertEquals("Empty test", false, userUtil.isValid(""));

        assertEquals("Null test", false, userUtil.isValid(null));
    }

    @Test
    public void updateNameTest() {
        String username = "ashwin1";
        String password = "pass123";
        String newName = "Ashwin";

        UserService service = new UserService(url);
        UserService userService = Mockito.spy(service);
        when(userService.validateUser(username, password)).thenReturn(true);

        User user = Mockito.mock(User.class);
        when(userService.getUser(username, password)).thenReturn(user);

        UserUtil userUtil = new UserUtil(userService);

        assertEquals(true, userUtil.updateName(username, password, newName));
    }
}
