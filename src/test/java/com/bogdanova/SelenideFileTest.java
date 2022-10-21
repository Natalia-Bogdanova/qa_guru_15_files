package com.bogdanova;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFileTest {

//    static {
//        Configuration.fileDownload = FileDownloadMode.PROXY; //скачивает файл вне зависимости от наличия атрибута href
//    }
    @Test
    void selenideFileDowloadTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloaderFile = $("#raw-url").download(); //путь к файлу
        //I, II, III варианты работают если есть атрибут href
        //I вариант
//        InputStream is = new FileInputStream(downloaderFile); //reader чтение по символам, inputStream побайтовое чтение - читает содержимое файла
//        try {
//            byte[] fileSource = is.readAllBytes(); //метод возвращает все содержимое файла - должен лежать в массиве fileSource, т.е. в fileSource будет все содержимое файла
//            String fileContent = new String(fileSource, StandardCharsets.UTF_8); //полученное в байтах (byte) преобразовать в строку
//            assertThat(fileContent).contains("This repository is the home of the next generation of JUnit, JUnit 5.");//проверить файл fileContent, например что он содержит какую то строку .contains()
//        } finally {
//            is.close(); //закрывает файловый скриптор и высвобождает ресурс, благодаря try/finnaly закроет не зависимо от того будет найден фрагмент текста или нет
//        }

    //II вариант той же конструкции - она автоматически закрывает инструкции открытые после слова "try"
        try (InputStream is = new FileInputStream(downloaderFile)) {
            byte[] fileSource = is.readAllBytes(); //метод возвращает все содержимое файла - должен лежать в массиве fileSource, т.е. в fileSource будет все содержимое файла
            String fileContent = new String(fileSource, StandardCharsets.UTF_8); //полученное в байтах (byte) преобразовать в строку
            assertThat(fileContent).contains("This repository is the home of the next generation of JUnit");//проверить файл fileContent, например что он содержит какую то строку .contains()
        }
        //III вариант, ЕЩЕ короче - то же самое
      //  String contents = FileUtils.readFileToString(downloaderFile, StandardCharsets.UTF_8);
    }

    @Test //проверяем - что то загрузили и проверили что оно загрузилось
    void upLoadFile() throws Exception {
        open("https://fineuploader.com/demos.html");
        $("input[type='file']").uploadFromClasspath("cat_images.jpg");//$("input[type='file']") выглядит одинаково для всех файлов в мире, где есть загрузка, uploadFile - загружает из папки build->downloads что не очень удобно пир тестировании, uploadFromClasspath берет из папки ресурсы
        $("div.qq-file-info").shouldHave(text("cat_images.jpg"));
    }
}
