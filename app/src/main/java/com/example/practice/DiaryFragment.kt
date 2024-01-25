package com.example.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DiaryFragment : Fragment() {
    private var selectedDate: String? = null
    private lateinit var bottomNavActivity: BottomNavActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedDate = it.getString(DiaryFragment.ARG_SELECTED_DATE)
            bottomNavActivity = requireActivity() as BottomNavActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_diary_page, container, false)
        var rewriteButton = rootView.findViewById<Button>(R.id.rewrite_button)
        var diaryDate = rootView.findViewById<TextView>(R.id.diary_date)
        var deleteButton = rootView.findViewById<Button>(R.id.delete_button)

        var todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

//        bottomNavActivity.setSelectedNavItem(R.id.ic_diary)

        if (selectedDate.isNullOrEmpty()) {
            diaryDate.text = todayDate
        } else {
            diaryDate.text = selectedDate.toString()
        }


        rewriteButton.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val writeDiaryFragment = WriteDiaryFragment.newInstance(bottomNavActivity, selectedDate)
//                val writeDiaryFragment = WriteDiaryFragment.newInstance( selectedDate)

                transaction.replace(R.id.mainFrameLayout, writeDiaryFragment)
//                bottomNavActivity.setSelectedNavItem(R.id.ic_diary)
                transaction.addToBackStack(null)
                transaction.commit()

                // 작성한 값 전달 코드 추가 필요
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return rootView
    }

    companion object {
        private const val ARG_SELECTED_DATE = "selected_date"

        @JvmStatic
        fun newInstance(selectedDate: String?): DiaryFragment {
            return DiaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SELECTED_DATE, selectedDate.orEmpty())
                }
            }
        }
    }
}