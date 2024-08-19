package com.example.diaryapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.Date
import android.widget.LinearLayout

class WriteDiaryFragment : Fragment() {
    private var selectedDateWD: String? = null
    private lateinit var bottomNavActivity: BottomNavActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedDateWD = it.getString(ARG_SELECTED_DATE)
            bottomNavActivity =
                it.getSerializable(ARG_BOTTOM_NAV_ACTIVITY) as? BottomNavActivity
                    ?: throw IllegalArgumentException("BottomNavActivity must not be null")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_write_diary, container, false)
        var diaryContent = rootView.findViewById<EditText>(R.id.diary_content)
        var wordCount = rootView.findViewById<TextView>(R.id.user_word_count)
        var diaryDate = rootView.findViewById<TextView>(R.id.write_diary_date)
        var saveDiary = rootView.findViewById<Button>(R.id.save_diary)
        var diaryTitle = rootView.findViewById<TextView>(R.id.diary_title)
        var diaryEmotion = rootView.findViewById<TextView>(R.id.diary_emotion)
        var diaryLength = rootView.findViewById<TextView>(R.id.user_word_count)

        var todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

        activity?.let {
            if (it is BottomNavActivity) {
                bottomNavActivity = it
            } else {
                throw IllegalStateException("Activity must be of type BottomNavActivity")
            }
        }


        if (selectedDateWD.isNullOrEmpty()) {
            diaryDate.text = todayDate
            selectedDateWD = todayDate
            showSettingDialog(diaryEmotion)
        } else {
            diaryDate.text = selectedDateWD.toString()

            // test 파일로 작성, 추후 DB에 담긴 일기로 가져와야 함
            for (diaryEntry in test()) {
                val date = diaryEntry.date

                if (date == selectedDateWD) {
                    diaryTitle.text = diaryEntry.title
                    diaryContent.setText(diaryEntry.content)
                    diaryEmotion.text = diaryEntry.emotion
                    diaryLength.text = diaryEntry.content.length.toString() + "/1000"
                }
            }
        }

        Log.i("selectedDateWD:", selectedDateWD.toString())
        saveDiary.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val diaryFragment = DiaryFragment.newInstance(bottomNavActivity, selectedDateWD)
                transaction.replace(R.id.mainFrameLayout, diaryFragment)
//                transaction.addToBackStack(null)
                transaction.commit()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        diaryEmotion.setOnClickListener {
            showSettingAfterWDDialog(diaryEmotion)
        }

        diaryContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                wordCount.text = "0/1000"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var userInput = diaryContent.text.toString()
                wordCount.text = userInput.length.toString() + "/1000"
            }

            override fun afterTextChanged(s: Editable?) {
                var userInput = diaryContent.text.toString()
                wordCount.text = userInput.length.toString() + "/1000"
            }
        }
        )

        return rootView
    }
    companion object {
        private const val ARG_SELECTED_DATE = "selected_date"
        private const val ARG_BOTTOM_NAV_ACTIVITY = "bottom_nav_activity"

        @JvmStatic
        fun newInstance(
            bottomNavActivity: BottomNavActivity,
            selectedDateWD: String?
        ): WriteDiaryFragment {
            return WriteDiaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SELECTED_DATE, selectedDateWD.orEmpty())
                    putSerializable(ARG_BOTTOM_NAV_ACTIVITY, bottomNavActivity)
                }
            }
        }
    }
    private fun showSettingDialog(diaryEmotion: TextView) {
        val builder = AlertDialog.Builder(requireContext(), R.style.RoundedAlertDialog)
        builder.setTitle("오늘의 기분을 선택하세요")
            .setMessage("기분을 선택하지 않으면 일기를 작성할 수 없어요")

        // 전체 레이아웃을 담을 LinearLayout 설정
        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(16, 16, 16, 16)

        // 라디오 그룹 생성
        val radioGroup = RadioGroup(requireContext())
        val options = arrayOf("좋음", "슬픔", "화남", "우울", "무감정")

        // 라디오 그룹에 마진 설정
        val radioGroupParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val marginInDp = 16
        val marginInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, marginInDp.toFloat(),
            resources.displayMetrics
        ).toInt()
        radioGroupParams.setMargins(marginInPx, marginInPx, marginInPx, marginInPx)
        radioGroup.layoutParams = radioGroupParams

        for ((index, option) in options.withIndex()) {
            val radioButton = RadioButton(requireContext())
            radioButton.text = option
            radioButton.id = index
            radioGroup.addView(radioButton)
        }

        // 라디오 그룹을 레이아웃에 추가
        layout.addView(radioGroup)

        // 확인 및 취소 버튼을 담을 새로운 LinearLayout 생성
        val buttonLayout = LinearLayout(requireContext())
        buttonLayout.orientation = LinearLayout.HORIZONTAL
        buttonLayout.gravity = android.view.Gravity.END

        // 확인 버튼 생성
        val positiveButton = Button(requireContext()).apply {
            text = "확인"
        }

        // 취소 버튼 생성
        val negativeButton = Button(requireContext()).apply {
            text = "취소"
        }

        // 버튼들을 버튼 레이아웃에 추가
        buttonLayout.addView(positiveButton)
        buttonLayout.addView(negativeButton)

        // 버튼 레이아웃을 전체 레이아웃에 추가
        layout.addView(buttonLayout)

        // 다이얼로그에 레이아웃 설정
        builder.setView(layout)

        // 다이얼로그 생성
        val dialog = builder.create()

        // 확인 버튼 클릭 시 다이얼로그 닫기
        positiveButton.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedOption = options[selectedId]
                diaryEmotion.text = selectedOption
                dialog.dismiss()
            }
        }

        // 취소 버튼 클릭 시 다이얼로그 닫기
        negativeButton.setOnClickListener {
            dialog.dismiss()
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val homeFragment = HomeFragment.newInstance("", "")
                transaction.replace(R.id.mainFrameLayout, homeFragment)
                bottomNavActivity.setSelectedNavItem(R.id.ic_home)
//                transaction.addToBackStack(null)
                transaction.commit()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        dialog.setCancelable(false)
        dialog.show()
    }

    private fun showSettingAfterWDDialog(diaryEmotion: TextView) {
        val builder = AlertDialog.Builder(requireContext(), R.style.RoundedAlertDialog)
        builder.setTitle("오늘의 기분을 선택하세요")
            .setMessage("기분을 선택하지 않으면 일기를 작성할 수 없어요")

        // 전체 레이아웃을 담을 LinearLayout 설정
        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(16, 16, 16, 16)

        // 라디오 그룹 생성
        val radioGroup = RadioGroup(requireContext())
        val options = arrayOf("좋음", "슬픔", "화남", "우울", "무감정")

        // 라디오 그룹에 마진 설정
        val radioGroupParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val marginInDp = 16
        val marginInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, marginInDp.toFloat(),
            resources.displayMetrics
        ).toInt()
        radioGroupParams.setMargins(marginInPx, marginInPx, marginInPx, marginInPx)
        radioGroup.layoutParams = radioGroupParams

        for ((index, option) in options.withIndex()) {
            val radioButton = RadioButton(requireContext())
            radioButton.text = option
            radioButton.id = index
            radioGroup.addView(radioButton)
        }

        // 라디오 그룹을 레이아웃에 추가
        layout.addView(radioGroup)

        // 확인 및 취소 버튼을 담을 새로운 LinearLayout 생성
        val buttonLayout = LinearLayout(requireContext())
        buttonLayout.orientation = LinearLayout.HORIZONTAL
        buttonLayout.gravity = android.view.Gravity.END

        // 확인 버튼 생성
        val positiveButton = Button(requireContext()).apply {
            text = "확인"
        }

        // 취소 버튼 생성
        val negativeButton = Button(requireContext()).apply {
            text = "취소"
        }

        // 버튼들을 버튼 레이아웃에 추가
        buttonLayout.addView(positiveButton)
        buttonLayout.addView(negativeButton)

        // 버튼 레이아웃을 전체 레이아웃에 추가
        layout.addView(buttonLayout)

        // 다이얼로그에 레이아웃 설정
        builder.setView(layout)

        // 다이얼로그 생성
        val dialog = builder.create()

        // 확인 버튼 클릭 시 다이얼로그 닫기
        positiveButton.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedOption = options[selectedId]
                diaryEmotion.text = selectedOption
            }
            dialog.dismiss()
        }

        // 취소 버튼 클릭 시 다이얼로그 닫기
        negativeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog.show()
    }

}