package com.chat.translate.service;

import com.chat.translate.model.User;
import com.chat.translate.translator.TranslatorLanguage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/** There is some redundancy in these tests. That is fine. **/

@RunWith(SpringRunner.class)
public class UserServiceTests {

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService();
        userService.addUser(new User("Joe", "en"));
        userService.addUser(new User("Jane", "de"));
        userService.addUser(new User("Jon", "en"));
        userService.addUser(new User("Jose", "es"));
    }

    @Test
    public void getUserList() {
        List<User> users = userService.getUserList();
        assertThat(users.size()).isEqualTo(4);
    }

    @Test
    public void addUser() {
        assertThat(userService.getUserList().size()).isEqualTo(4);
        userService.addUser(new User("Doug", "fr"));
        assertThat(userService.getUserList().size()).isEqualTo(5);
        assertThat(userService.getUserLanguages().contains(TranslatorLanguage.FRENCH)).isTrue();
    }

    @Test
    public void removeUser() {
        userService.removeUser("Jane");
        assertThat(userService.getUserList().size()).isEqualTo(3);
        assertThat(userService.getUserLanguages().contains(TranslatorLanguage.GERMAN)).isFalse();
    }

    @Test
    public void getUserLanguages() {
        assertThat(userService.getUserLanguages().size()).isEqualTo(3);
    }

    @Test
    public void addUserLanguage() {
        userService.addUser(new User("Emily", "es"));
        assertThat(userService.getUserLanguages().size()).isEqualTo(3);
        userService.addUser(new User("Doug", "fr"));
        assertThat(userService.getUserLanguages().size()).isEqualTo(4);
        assertThat(userService.getUserLanguages().contains(TranslatorLanguage.FRENCH)).isTrue();
    }

    @Test
    public void removeUserLanguage() {
        userService.removeUser("Joe");
        assertThat(userService.getUserLanguages().size()).isEqualTo(3);
        assertThat(userService.getUserLanguages().contains(TranslatorLanguage.ENGLISH)).isTrue();
        userService.removeUser("Jose");
        assertThat(userService.getUserLanguages().size()).isEqualTo(2);
        assertThat(userService.getUserLanguages().contains(TranslatorLanguage.SPANISH)).isFalse();
    }
}
