package ru.ivanov;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileTest {
    @BeforeAll
    static void browserSettings() {
        Configuration.browser = "FIREFOX";
        Configuration.browserSize = "1920x1200";
    }

    @Test
    @DisplayName("Загрузка файла по абсолютному пути")
    void uploadFileAbsolutePathTest() {

        open("https://the-internet.herokuapp.com/upload");

        File file = new File("D:\\JavaProjects\\111\\qa_auto_file\\src\\test\\resources\\example.txt");

        $("input[type='file']").uploadFile(file);
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(Condition.text("example.txt"));
    }

    @Test
    @DisplayName("Загрузка файла по абсолютному пути")
    void uploadFileTest() {

        open("https://the-internet.herokuapp.com/upload");

        $("input[type='file']").uploadFromClasspath("example.txt");
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(Condition.text("example.txt"));
    }

    @Test
    @DisplayName("Скачивание текстового файла и проверка его содержимого")
    void downloadSimpleTextFileTest() throws IOException {
//        open("https://github.com/junit-team/junite5/blob/main/README.md");
        open("https://github.com/othneildrew/Best-README-Template/blob/master/README.md");
        File download = $x("//*[@id=\"repos-sticky-header\"]/div[1]/div[2]/div[2]/div[1]/div[1]/a").download();

        System.out.println(download.getAbsolutePath());

        String fileContent = IOUtils.toString(new FileReader(download));
        assertTrue(fileContent.contains("There are many great README templates available on GitHub; however"));
    }

    @Test
    @DisplayName("Скачивание PDF файла")
    void pdfFileDownloadTest() throws IOException {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File pdf = $(byText("PDF download")).download();
        PDF parsPDF = new PDF(pdf);
        Assertions.assertEquals(191, parsPDF.numberOfPages);

    }

    @Test
    @DisplayName("Скачивание XLS файла")
    void xlsFileDownloadTest() throws IOException {
        open("http://romashka2008.ru/price");
        File xls = $(byText("Прайс от 08.09")).download();
        XLS parsedXLS = new XLS(xls);
//        Assertions.assertEquals();

    }

    @Test
    @DisplayName("Парсинг CSV")
    void parseCsvFileTest() throws IOException, CsvException {
        ClassLoader classLoader = this.getClass().getClassLoader();

        try (InputStream is = classLoader.getResourceAsStream("csv.csv");
             Reader reader = new InputStreamReader(is)) {

            CSVReader csvReader = new CSVReader(reader);

            List<String[]> strings = csvReader.readAll();
            assertEquals(3, strings.size());

        }
    }
}
