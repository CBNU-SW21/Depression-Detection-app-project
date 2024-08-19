package com.example.diaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.Date


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PostFragment : Fragment() {
    private var postTitleText: String? = null
    private var postContentText: String? = null
    private var userNameText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postTitleText = it.getString("postTitle")
            postContentText = it.getString("postContent")
            userNameText = it.getString("userName")
        }
    }

    // 지금 코드는 그냥 제목, 유저이름, 내용을 전달받아서 그대로 띄워주고 있음
    // 디비에서 적절히 불러오도록 수정해야함
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_post, container, false)

        var drawerLayout = rootView.findViewById<DrawerLayout>(R.id.drawerLayout)
        var imgMenu = rootView.findViewById<ImageView>(R.id.imageMenu)

        var postTitle = rootView.findViewById<TextView>(R.id.post_title)
        var postContent = rootView.findViewById<TextView>(R.id.post_content)
        var userNameView = rootView.findViewById<TextView>(R.id.user_name_view)
        var writeDate = rootView.findViewById<TextView>(R.id.write_date)
        var editBtn = rootView.findViewById<Button>(R.id.edit_btn)
        var deleteBtn = rootView.findViewById<Button>(R.id.delete_btn)
        var normalPostBtn = rootView.findViewById<RadioButton>(R.id.normal_post_btn)
        var photoPostBtn = rootView.findViewById<RadioButton>(R.id.photo_post_btn)
        var commentBtn = rootView.findViewById<Button>(R.id.comment_submit)
        var commentInput = rootView.findViewById<EditText>(R.id.comment_input)
        var imgView = rootView.findViewById<ImageView>(R.id.imageView1)
        var todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

        postTitle.text = postTitleText
        postContent.text = postContentText
        userNameView.text = userNameText



        commentBtn.setOnClickListener {
            commentInput.text.toString()
            // 코멘트 디비 저장
        }


        imgMenu.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val naviView = rootView.findViewById<NavigationView>(R.id.navigationView)
        naviView.itemIconTintList = null

        naviView.setNavigationItemSelectedListener { memuItem ->
            when (memuItem.itemId) {
                R.id.nav_home -> {
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    val communityFragment = CommunityFragment.newInstance("","")
                    transaction.replace(R.id.mainFrameLayout, communityFragment)
                    transaction.commit()
                }
                R.id.nav_hot -> {
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    val hotPostFragment = HotPostFragment.newInstance("","")
                    transaction.replace(R.id.mainFrameLayout, hotPostFragment)
                    transaction.commit()
                }
                R.id.nav_photo -> {
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    val photoPostFragment = PhotoPostFragment.newInstance("","")
                    transaction.replace(R.id.mainFrameLayout, photoPostFragment)
                    transaction.commit()
                }
                else -> { }
            }
            true
        }

        return rootView
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(postTitle: String, postContent: String, userName: String) =
            PostFragment().apply {
                arguments = Bundle().apply {
                    putString("postTitle", postTitle)
                    putString("postContent", postContent)
                    putString("userName", userName)
                }
            }
    }
}