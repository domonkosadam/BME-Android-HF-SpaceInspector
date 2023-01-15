package hu.bme.aut.android.spaceinspector.view.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.devhoony.lottieproegressdialog.LottieProgressDialog
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.adapter.NasaImagesAdapter
import hu.bme.aut.android.spaceinspector.databinding.FragmentNasaImagesDetailsBinding
import hu.bme.aut.android.spaceinspector.databinding.FragmentNasaImagesListBinding
import hu.bme.aut.android.spaceinspector.model.image.Item

class NasaImagesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNasaImagesDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNasaImagesDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = arguments?.get("IMAGE") as Item
        initView(image)
    }

    private fun initView(image: Item) {
        Glide.with(requireContext()).load(image.links?.get(0)?.href).into(binding.ivImage)
        binding.tvTitle.text = image.data?.get(0)?.title
        binding.tvDescription.text = image.data?.get(0)?.description

        binding.ivImage.setOnClickListener {
            val b = Bundle()
            b.putParcelable("IMAGE", binding.ivImage.drawable.toBitmap())
            findNavController().navigate(R.id.action_nasaImagesDetailsFragment_to_imageViewFragment, b)
        }
    }
}