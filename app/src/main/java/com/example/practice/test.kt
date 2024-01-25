package com.example.practice

data class Diary(
    val title: String,
    val content: String,
    val date: String
)

fun test(): Array<Diary> {
    // 일기 데이터를 담은 배열 생성
    val diaryEntries = arrayOf(
        Diary("제목1", "일기 내용1 test aqwrrjojdknfkanvnadaqwrrjojdknfkanvnadaqwrrjojdknfkanvnadaqwrrjojdknfkanvnadaqwrrjojdknfkanvnadaqwrrjojdknfkanvnadaqwrrjojdknfkanvnadaqwrrjojdknfkanvnadaqwrrjojdknfkanvnad", "2024-01-20"),
        Diary("제목2", "일기 내용2 test aqwrrjojdknfkanvnad", "2024-01-21"),
        Diary("제목3", "일기 내용3 test aqwrrjojdknfkanvnad", "2024-01-23"),
        Diary("제목4", "일기 내용3 test aqwrrjojdknfkanvnad", "2024-01-25")
    )
    return diaryEntries
}
