package com.example.practice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.practice.databinding.HomeBinding


private const val TAG_CALENDAR = "ic_calendar"
private const val TAG_HOME = "ic_home"
private const val TAG_Diary = "ic_diary"
private const val TAG_Community = "ic_community"
private const val TAG_Settings = "ic_settings"


class BottomNavActivity : AppCompatActivity() {

    private lateinit var binding : HomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_HOME, HomeFragment())
        binding.homeBottomNav.selectedItemId = R.id.ic_home

        binding.homeBottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.ic_calendar-> setFragment(TAG_CALENDAR, CalendarFragment())
                R.id.ic_home -> setFragment(TAG_HOME, HomeFragment())
                R.id.ic_diary-> setFragment(TAG_Diary, DiaryFragment())
                R.id.ic_community -> setFragment(TAG_Community, CommunityFragment())
                R.id.ic_settings -> setFragment(TAG_Settings, SettingsFragment())
            }
            true
        }
    }
    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val calendar = manager.findFragmentByTag(TAG_CALENDAR)
        val home = manager.findFragmentByTag(TAG_HOME)
        val diary = manager.findFragmentByTag(TAG_Diary)
        val commnunity = manager.findFragmentByTag(TAG_Community)
        val settings = manager.findFragmentByTag(TAG_Settings)

        if (calendar != null){
            fragTransaction.hide(calendar)
        }

        if (home != null){
            fragTransaction.hide(home)
        }

        if (diary != null) {
            fragTransaction.hide(diary)
        }

        if (commnunity != null) {
            fragTransaction.hide(commnunity)
        }

        if (settings != null) {
            fragTransaction.hide(settings)
        }

        if (tag == TAG_CALENDAR) {
            if (calendar!=null){
                fragTransaction.show(calendar)
            }
        }
        else if (tag == TAG_HOME) {
            if (home != null) {
                fragTransaction.show(home)
            }
        }

        else if (tag == TAG_Diary){
            if (diary != null){
                fragTransaction.show(diary)
            }
        }

        else if (tag == TAG_Community){
            if (commnunity != null){
                fragTransaction.show(commnunity)
            }
        }

        else if (tag == TAG_Settings){
            if (settings != null){
                fragTransaction.show(settings)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }

}