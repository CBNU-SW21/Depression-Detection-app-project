package com.example.diaryapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

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


//        diaryContent.setOnTouchListener { view, event ->
//            if (view.id == R.id.diary_content) {
//                view.parent.requestDisallowInterceptTouchEvent(true)
//                when (event.action and MotionEvent.ACTION_MASK) {
//                    MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
//                }
//            }
//            false }

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
}