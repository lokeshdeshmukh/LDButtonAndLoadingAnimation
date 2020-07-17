package com.example.sampleprojectlibrary.ui.home

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.AttributeSet
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cchat.ldbuttonsandloadinganimation.LoadingAnimation.SimpleLoadingAnimation
import com.cchat.ldbuttonsandloadinganimation.LoadingAnimation.SimpleLoadingAnimationWithBlur
import com.example.sampleprojectlibrary.R
import java.util.jar.Attributes


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        var  simpleLoadingAnimationWithBlur=SimpleLoadingAnimationWithBlur(requireContext())
        root.findViewById<Button>(R.id.clickme2).setOnClickListener(View.OnClickListener {
            simpleLoadingAnimationWithBlur.finish()

        })
        root.findViewById<Button>(R.id.clickme).setOnClickListener(View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                var temp=ArrayList<String>()
                temp.add("Please Wait")
                temp.add("While We Are")
                temp.add("Processing Your Request")
                simpleLoadingAnimationWithBlur.setBottomTextVisibleDuration(4000)//optional
                simpleLoadingAnimationWithBlur.setBottomTextVisibilityEnabled(false)
                simpleLoadingAnimationWithBlur.startWithRespectToApplication(requireActivity(),temp)
            }


        })

        return root
    }


}