package com.bogdanova;

import com.bogdanova.modelForJson.Student;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

;
;
;
;

public class FileParseTest {

    ClassLoader cl = FileParseTest.class.getClassLoader(); //взять файл из ресурсов, если в ресурсах файл в папке пишем имя паки.имя файла

    @Test
    void  pdfTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File downloadedFile = $("a[href*='junit-user-guide-5.9.1.pdf']").download();
        PDF pdf = new PDF(downloadedFile);
        assertThat(pdf.author).contains("Sam Brannen");
    }

    @Test
    void  xlsxTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("Вопросы для собеседования.xlsx")) {
            XLS xls = new XLS(is);
            assertThat(
                    xls.excel.getSheetAt(0)//первая таблица
                            .getRow(1)//вторая ряд
                            .getCell(1)//вторая ячуйка
                            .getStringCellValue() //смотрим строковое значение ячейки
            ).isEqualTo("Что должно быть в тест-кейсе");//что должно быть в ней
            System.out.println("");
        }
    }

    @Test
    void csvTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("qa.guru.csv")) {
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            List<String[]> content = reader.readAll();
            String[] row = content.get(1); //2 tabl
            assertThat(row[0]).isEqualTo("Bogdanova"); //1 row
            assertThat(row[1]).isEqualTo("JInut 5");  // 2 row
            }
    }

    @Test
    void zipTest () throws Exception {
        InputStream is = cl.getResourceAsStream("Test1.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            String entryName = entry.getName();
            assertThat(entry.getName()).isIn("Test1.docx");
//сделать архив с тремя файлами и проверить каждый из них https://github.com/Max0490/qa_guru_15_files/blob/master/src/test/java/qa/homework/FileParseTestHomework.java
        }
    }

    @Test
    void jsonTest() throws Exception {
        InputStream is = cl.getResourceAsStream("Student.json");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);
        assertThat(jsonObject.get("name").getAsString()).isEqualTo("Natalia");
        assertThat(jsonObject.get("age").getAsInt()).isEqualTo(25);
        assertThat(jsonObject.get("isGoodStudent").getAsBoolean()).isTrue();
        assertThat(jsonObject.get("passport").getAsJsonObject().get("number").getAsInt()).isEqualTo(123456);
    }

    @Test
    void jsonTestWithModel() throws Exception {
        InputStream is = cl.getResourceAsStream("Student.json");
        Gson gson = new Gson();
        Student student = gson.fromJson(new InputStreamReader(is), Student.class);
        assertThat(student.name).isEqualTo("Natalia");
        assertThat(student.age).isEqualTo(25);
        assertThat(student.isGoodStudent).isTrue();
        assertThat(student.passport.number).isEqualTo(123456);
    }

}
