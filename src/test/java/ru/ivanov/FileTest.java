package ru.ivanov;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

public class FileTest {
    @BeforeAll
    static void browserSettings() {
        Configuration.browser = "FIREFOX";
        Configuration.browserSize = "1920x1200";
    }

    @Test
    @DisplayName("Загрузка файла по абсолютному пути")
    void uploadFileAbsolutePathTest(){

        open("https://the-internet.herokuapp.com/upload");

        File file = new File("D:\\JavaProjects\\111\\qa_auto_file\\src\\test\\resources\\example.txt");

        $("input[type='file']").uploadFile(file);
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(Condition.text("example.txt"));
    }

    @Test
    @DisplayName("Загрузка файла по абсолютному пути")
    void uploadFileTest(){

        open("https://the-internet.herokuapp.com/upload");

        $("input[type='file']").uploadFromClasspath("example.txt");
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(Condition.text("example.txt"));
    }

}
