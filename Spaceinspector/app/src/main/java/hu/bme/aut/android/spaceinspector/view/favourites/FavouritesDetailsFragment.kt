package hu.bme.aut.android.spaceinspector.view.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.devhoony.lottieproegressdialog.LottieProgressDialog
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.adapter.FavouriteApodAdapter
import hu.bme.aut.android.spaceinspector.database.ApodDatabase
import hu.bme.aut.android.spaceinspector.databinding.FragmentFavouritesDetailsBinding
import hu.bme.aut.android.spaceinspector.databinding.FragmentFavouritesListBinding
import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.viewmodel.FavouritesListFragmentViewModel

class FavouritesDetailsFragment: Fragment() {
    private lateinit var binding: FragmentFavouritesDetailsBinding

    //private val viewModel: FavouritesListFragmentViewModel by viewModels()

    //private lateinit var loadingDialog: LottieProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
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

        viewModel.database = ApodDatabase.getDatabase(requireContext())
*/
        initView()

    }

    private fun initView() {
        val apod = requireArguments().get("APOD") as Apod

        if (apod.media_type == "image")
            Glide.with(requireContext()).load(apod.hdurl).into(binding.ivApod)
        else
            Glide.with(requireContext()).load(apod.thumbnail_url).into(binding.ivApod)
        binding.tvApodTitle.text = apod.title
        binding.tvApodDescription.text = apod.explanation
        binding.tvApodCopyright.text = apod.copyright
        binding.tvApodDate.text = apod.date

        binding.ivApod.setOnClickListener {
            if (apod.media_type == "image") {
                val b = Bundle()
                b.putParcelable("IMAGE", binding.ivApod.drawable.toBitmap())
                findNavController().navigate(R.id.action_favouritesDetailsFragment_to_imageViewFragment, b)
            }
            else {
                val b = Bundle()
                b.putString("VIDEO", apod.url)
                findNavController().navigate(R.id.action_favouritesDetailsFragment_to_videoViewFragment, b)
            }
        }
    }
}