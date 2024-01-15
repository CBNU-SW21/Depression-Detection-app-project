package com.example.practice

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        var duplicateID: Button = findViewById(R.id.duplicateID)

        duplicateID.setOnClickListener {
            // 전달할 메시지를 정의
            val message = "전달된 값에 따른 동적 메시지"

            // AlertDialog 생성 시 메시지 전달
            val builder = basicDialog(message)

            builder.show()
        }
    }

    // 함수에 매개변수 추가
    fun basicDialog(message: String): AlertDialog.Builder {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("아이디 중복 확인")
            // 전달된 메시지 사용
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    Toast.makeText(baseContext, "아이디 중복 확인 완료", Toast.LENGTH_SHORT).show()
                }
            })
        return builder
    }
}
