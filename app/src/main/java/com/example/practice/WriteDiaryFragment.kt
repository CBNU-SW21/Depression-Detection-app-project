package com.example.practice

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WriteDiaryFragment : Fragment() {
    private var selectedDate: String? = null
    private lateinit var bottomNavActivity: BottomNavActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedDate = it.getString(ARG_SELECTED_DATE)
            bottomNavActivity = it.getSerializable(ARG_BOTTOM_NAV_ACTIVITY) as? BottomNavActivity
                ?: throw IllegalArgumentException("BottomNavActivity must not be null")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_write_diary, container, false)
        var diaryContent = rootView.findViewById<EditText>(R.id.diary_content)
        var wordCount = rootView.findViewById<TextView>(R.id.user_word_count)
        var diaryDate = rootView.findViewById<TextView>(R.id.write_diary_date)
        var saveDiary = rootView.findViewById<Button>(R.id.save_diary)

//        bottomNavActivity.setSelectedNavItem(R.id.ic_diary)

        var todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        Log.i("selectedDate???", selectedDate.toString())

        if (selectedDate.isNullOrEmpty()) {
            diaryDate.text = todayDate
        } else {
            diaryDate.text = selectedDate.toString()
        }


        saveDiary.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val diaryFragment = DiaryFragment.newInstance(selectedDate)
                transaction.replace(R.id.mainFrameLayout, diaryFragment)
                transaction.addToBackStack(null)
                transaction.commit()

                // 작성한 값 전달 코드 추가 필요
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        diaryContent.setOnTouchListener { view, event ->
            if (view.id == R.id.diary_content) {
                view.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            false }

        diaryContent.addTextChangedListener ( object: TextWatcher {
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
        fun newInstance(bottomNavActivity: BottomNavActivity, selectedDate: String?): WriteDiaryFragment {
//        fun newInstance(selectedDate: String?): WriteDiaryFragment {
                return WriteDiaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SELECTED_DATE, selectedDate.orEmpty())
                    putSerializable(ARG_BOTTOM_NAV_ACTIVITY, bottomNavActivity)
                }
            }
        }
    }
}