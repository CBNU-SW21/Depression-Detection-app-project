package com.example.practice

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        var duplicateID: Button = findViewById(R.id.duplicateID)
        var signUp: Button = findViewById(R.id.signUp)



        duplicateID.setOnClickListener {
            // DB에서 중복 아이디가 있는지 확인하는 코드 작성 필요

//            val message = true
            val message = false
            if (message) {
                val builder = NoDuplicateDialog("사용 가능한 아이디 입니다.")
                builder.show()
            } else {
                val builder = DuplicateDialog("이미 사용중인 아이디 입니다.\n아이디를 다시 입력해주세요.")
                builder.show()
            }
        }

        signUp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun NoDuplicateDialog(message: String): AlertDialog.Builder {
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

    fun DuplicateDialog(message: String): AlertDialog.Builder {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("아이디 중복 확인")
            // 전달된 메시지 사용
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    Toast.makeText(baseContext, "아이디 중복! 다시 입력하세요", Toast.LENGTH_SHORT).show()
                }
            })
        return builder
    }
}
