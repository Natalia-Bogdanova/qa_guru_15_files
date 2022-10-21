package com.bogdanova.modelForJson;


import java.util.List;

//    {
//        "name": "Natalia",
//            "age": 25,
//            "isGoodStudent": true,
//            "lesson": ["JUnit 5", "Files"],
//        "passport": {
//        "number": 123456,
//                "issueData": "12.12.2022"
//    }
//    }
public class Student {
    public String name;
    public int age;
    public boolean isGoodStudent;
    public List<String> lesson;
    public Passport passport;

    public static class Passport {
        public int number;
        public String issueData;
    }
}
