package hu.bme.aut.android.spaceinspector.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.example.awesomedialog.*
import hu.bme.aut.android.spaceinspector.databinding.FragmentVideoViewBinding

class VideoViewFragment : Fragment() {
    private lateinit var binding: FragmentVideoViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoUrl = arguments?.get("VIDEO") as String
        initView(videoUrl)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVideoViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun initView(videoUrl: String) {
        binding.wvVideo.webViewClient = WebViewClient()
        binding.wvVideo.settings.javaScriptEnabled = true
        binding.wvVideo.loadUrl(videoUrl)

    }
}