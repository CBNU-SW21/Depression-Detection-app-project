package com.example.practice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Date

private const val TAG_Diary = "ic_diary"

class CalendarFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var bottomNavActivity: BottomNavActivity
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            if (it is BottomNavActivity) {
                bottomNavActivity = it
            } else {
                throw IllegalStateException("Activity must implement BottomNavActivity")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_calendar, container, false)

        val addButton = rootView.findViewById<FloatingActionButton>(R.id.addButton)
        val todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val clickedDate = rootView.findViewById<CalendarView>(R.id.calendarView)
        var selectedDate = todayDate

        listView = rootView.findViewById(R.id.calendarListView)

        updateListView(todayDate, addButton) // 오늘 날짜로 초기화

        clickedDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            Log.i("SelectedDate", selectedDate)

            updateListView(selectedDate, addButton)

        }

        addButton.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val writeDiaryFragment = WriteDiaryFragment.newInstance(bottomNavActivity, selectedDate)
                transaction.replace(R.id.mainFrameLayout, writeDiaryFragment)
                bottomNavActivity.setSelectedNavItem(R.id.ic_diary)
                transaction.addToBackStack(null)
                transaction.commit()

                // 작성한 값 전달 코드 추가 필요
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return rootView
    }

    private fun updateListView(selectedDate: String, addButton: FloatingActionButton) {
        val diaryEntries = test()
        val diaryList: MutableList<String> = mutableListOf()

        for (diaryEntry in diaryEntries) {
            val date = diaryEntry.date

            if (date == selectedDate) {
                val contentPreview = if (diaryEntry.content.length > 30) {
                    "${diaryEntry.content.substring(0, 30)}..."
                } else {
                    diaryEntry.content
                }
                val tmp = "${diaryEntry.title}\n$contentPreview"
                diaryList.add(tmp)
                addButton.isEnabled = false
            }
        }

        if (diaryList.isEmpty()) {
            diaryList.add("작성된 일기가 없습니다.")
            addButton.isEnabled = true
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, diaryList)
        listView.adapter = adapter
    }

    companion object {
        fun newInstance(bottomNavActivity: BottomNavActivity): CalendarFragment {
            val fragment = CalendarFragment()
            fragment.bottomNavActivity = bottomNavActivity
            return fragment
        }
    }
}
