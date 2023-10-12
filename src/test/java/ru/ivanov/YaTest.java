package ru.ivanov;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class YaTest {

    @BeforeAll
    static void browserSettings() {
        Configuration.browser = "FIREFOX";
        Configuration.browserSize = "1920x1200";
    }

    @Tag("blocker")
    @DisplayName("Search with word - Selenide")
    @Test
    void selenideSearchTest(){
        open("https://ya.ru");
        $("input[id='text']").setValue("Selenide");
        $("button[type='submit']").click();
        $("#search-result").shouldHave(Condition.text("Selenide"));

    }

    @ValueSource(strings = {"Allure", "Selenide"})
    @Tag("blocker")
    @ParameterizedTest(name = "Search in ya.ru word {0}")
    void commonYaSearchTest(String searchQuery){
        open("https://ya.ru");
        $("input[id='text']").setValue(searchQuery);
        $("button[type='submit']").click();
        $("#search-result").shouldHave(Condition.text(searchQuery));

    }
}
