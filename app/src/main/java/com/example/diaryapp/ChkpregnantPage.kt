package com.example.diaryapp

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

private const val TAG_Diary = "ic_home"
private const val PICK_IMAGE_REQUEST = 1

class ChkpregnantPage : Fragment() {
    private lateinit var bottomNavActivity: BottomNavActivity

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
        var rootView = inflater.inflate(R.layout.fragment_chkpregnant_page, container, false)

        var photo = rootView.findViewById<ImageView>(R.id.photo)
        var photoUploadBtn = rootView.findViewById<Button>(R.id.photo_button)
        var commuId = rootView.findViewById<EditText>(R.id.commu_id)
        var submitBtn = rootView.findViewById<Button>(R.id.submit_button)

        photoUploadBtn.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
        }

        submitBtn.setOnClickListener {
            Toast.makeText(context, "임산부 인증을 요청합니다.", Toast.LENGTH_SHORT).show()
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val homeFragment = HomeFragment.newInstance(bottomNavActivity)
                transaction.replace(R.id.mainFrameLayout, homeFragment)
                bottomNavActivity.setSelectedNavItem(R.id.ic_home)
                transaction.commit()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return rootView
    }

    companion object {
        fun newInstance(bottomNavActivity: BottomNavActivity): ChkpregnantPage {
            val fragment = ChkpregnantPage()
            fragment.bottomNavActivity = bottomNavActivity
            return fragment
        }
    }
}