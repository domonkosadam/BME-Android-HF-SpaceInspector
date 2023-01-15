package hu.bme.aut.android.spaceinspector.view.apod

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.devhoony.lottieproegressdialog.LottieProgressDialog
import com.example.awesomedialog.*
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.database.ApodDatabase
import hu.bme.aut.android.spaceinspector.databinding.FragmentApodDetailsBinding
import hu.bme.aut.android.spaceinspector.model.DatabaseResult
import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.viewmodel.ApodDetailsFragmentViewModel
import java.util.*


class ApodDetailsFragment : Fragment() {

    private lateinit var binding: FragmentApodDetailsBinding

    private val viewModel: ApodDetailsFragmentViewModel by viewModels()

    private lateinit var loadingDialog: LottieProgressDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LottieProgressDialog(
            context = requireContext(),
            isCancel = true,
            dialogWidth = null,
            dialogHeight = null,
            animationViewWidth = null,
            animationViewHeight = null,
            fileName = LottieProgressDialog.SAMPLE_8,
            title = null,
            titleVisible = null
        )

        if (viewModel.state == null) {
            viewModel.getApodData().observe(
                viewLifecycleOwner
            ) { apodState ->
                render(apodState)
            }
        }

        binding.btnNext.setOnClickListener {
            viewModel.increaseDate()
            viewModel.getApodData().observe(viewLifecycleOwner
            ) { apodState ->
                render(apodState)
            }
        }

        binding.btnPrevious.setOnClickListener {
            viewModel.decreaseDate()
            viewModel.getApodData().observe(viewLifecycleOwner
            ) { apodState ->
                render(apodState)
            }
        }

        viewModel.database = ApodDatabase.getDatabase(requireContext())

    }

    override fun onResume() {
        super.onResume()

        if (viewModel.state != null) {
            render(viewModel.state!!.value!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentApodDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun render(state: ApodState) {
        when (state) {
            is ApodState.inProgress -> {
                loadingDialog.show()
            }
            is ApodState.responseSuccess -> {
                checkFavourite(state.data)
                loadImage(state.data)
                binding.tvApodTitle.text = state.data.title
                binding.tvApodDescription.text = state.data.explanation
                binding.tvApodCopyright.text = state.data.copyright
                binding.tvApodDate.text = state.data.date
                viewModel.date = state.data.date
                binding.btnNext.isEnabled = viewModel.currentDate != viewModel.date

                binding.ivApod.setOnClickListener {
                    if (state.data.media_type == "image") {
                        val b = Bundle()
                        b.putParcelable("IMAGE", viewModel.img?.toBitmap())
                        findNavController().navigate(R.id.action_apodDetailsFragment_to_imageViewFragment, b)
                    }
                    else {
                        val b = Bundle()
                        b.putString("VIDEO", state.data.url)
                        findNavController().navigate(R.id.action_apodDetailsFragment_to_videoViewFragment, b)
                    }
                }
            }
            is ApodState.responseError -> {
                loadingDialog.dismiss()
                AwesomeDialog.build(requireActivity())
                    .title("Error")
                    .body(state.exceptionMsg)
                    .icon(R.drawable.error)
                    .onPositive("Close")
            }
        }
    }

    private fun loadImage(apod: Apod) {
        if (apod.media_type == "image")
            Glide.with(requireContext()).load(apod.hdurl).listener(object :
                RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    loadingDialog.dismiss()
                    AwesomeDialog.build(requireActivity())
                        .title("Error")
                        .body("Error occured while loading the image!")
                        .icon(R.drawable.error)
                        .onPositive("Close")
                    return false
                }
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    loadingDialog.dismiss()
                    viewModel.img = resource
                    return false
                }
            }).into(binding.ivApod)
        else {
            Glide.with(requireContext()).load(apod.thumbnail_url).listener(object :
                RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    loadingDialog.dismiss()
                    AwesomeDialog.build(requireActivity())
                        .title("Error")
                        .body("Error occured while loading the image!")
                        .icon(R.drawable.error)
                        .onPositive("Close")
                    return false
                }
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    loadingDialog.dismiss()
                    return false
                }
            }).into(binding.ivApod)
        }
    }

    private fun checkFavourite(apod: Apod) {
        viewModel.isFavourite(apod).observe(viewLifecycleOwner
        ) { favourite ->
            when (favourite){
                    is DatabaseResult.inProgress -> {}
                    is DatabaseResult.success -> {
                        if (favourite.data)
                            caseFavourite(apod)
                        else
                            caseNotFavourite(apod)
                    }
                    is DatabaseResult.error -> {
                        AwesomeDialog.build(requireActivity())
                            .title("Favourite")
                            .body("Error while reading from favourites")
                            .icon(R.drawable.error)
                            .onPositive("Close")
                    }
                }
        }
    }

    private fun caseFavourite(apod: Apod) {
        binding.btnFavourite.setImageResource(R.drawable.ic_baseline_star_24)
        binding.btnFavourite.setOnClickListener {
            viewModel.setFavourite(apod, false).observe(viewLifecycleOwner
            ) { isSuccess ->
                when (isSuccess){
                    is DatabaseResult.inProgress -> {}
                    is DatabaseResult.success -> {
                        AwesomeDialog.build(requireActivity())
                            .title("Favourite")
                            .body("Removed from favourite")
                            .icon(R.drawable.success)
                            .onPositive("Close")
                        caseNotFavourite(apod)
                    }
                    is DatabaseResult.error -> {
                        AwesomeDialog.build(requireActivity())
                            .title("Favourite")
                            .body("Error while removing from favourites")
                            .icon(R.drawable.error)
                            .onPositive("Close")
                    }
                }

            }
        }
    }

    private fun caseNotFavourite(apod: Apod) {
        binding.btnFavourite.setImageResource(R.drawable.ic_baseline_star_border_24)
        binding.btnFavourite.setOnClickListener {
            viewModel.setFavourite(apod, true).observe(viewLifecycleOwner
            ) { isSuccess ->
                when (isSuccess){
                    is DatabaseResult.inProgress -> {}
                    is DatabaseResult.success -> {
                        AwesomeDialog.build(requireActivity())
                            .title("Favourite")
                            .body("Set as favourite")
                            .icon(R.drawable.success)
                            .onPositive("Close")
                        caseFavourite(apod)
                    }
                    is DatabaseResult.error -> {
                        AwesomeDialog.build(requireActivity())
                            .title("Favourite")
                            .body("Error while setting as favourite")
                            .icon(R.drawable.error)
                            .onPositive("Close")
                    }
                }

            }
        }
    }
}