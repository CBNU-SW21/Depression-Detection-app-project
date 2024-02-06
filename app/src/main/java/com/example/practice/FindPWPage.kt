package com.example.practice

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class FindPWPage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_pw)

        var sendCertBtnFindPW: Button = findViewById(R.id.sendCertification_findPW)
        var checkCertBtnFindPW: Button = findViewById(R.id.checkCertification_findPW)
        var inputIDFindPW:TextInputEditText = findViewById(R.id.userInputID_findPW)
        var inputEmailFindPW:EditText = findViewById(R.id.userInputEmail_findPW)
        var inputCertNumFindPW:TextInputEditText = findViewById(R.id.userInputNum_findPW)



        sendCertBtnFindPW.setOnClickListener {
        // 아이디와 이메일이 일치한 유저 확인
        //   -> 일치하면 인증번호 발송
        //   -> 불일치하면 아이디 혹은 이메일이 잘못됐습니다 라고 화면 띄우기

        }

        checkCertBtnFindPW.setOnClickListener {
            // 발송한 인증번호와 입력한 인증번호가 맞으면 비밀번호 변경 화면으로 이동
            // 인증번호가 다르면 잘못됐다고 팝업 안내

            val num = 12345 // 임의 설정 인증번호
            if (num.toString() == inputCertNumFindPW.text.toString()) {  // 번호 일치 화면 이동
                val intent = Intent(this, FindIDSettingPage::class.java)
                startActivity(intent)
            } else {
                val builder = NotMatchCertiNumDialog("인증번호가 일치하지 않습니다.\n인증번호를 다시 입력해주세요.")
                builder.show()
            }
        }
    }
    fun NotMatchCertiNumDialog(message: String): AlertDialog.Builder {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("인증번호 확인")
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                }
            })
        return builder
    }
}