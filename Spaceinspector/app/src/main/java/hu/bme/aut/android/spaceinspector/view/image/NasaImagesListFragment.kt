package hu.bme.aut.android.spaceinspector.view.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.devhoony.lottieproegressdialog.LottieProgressDialog
import com.example.awesomedialog.*
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.adapter.MarsRoverMenuAdapter
import hu.bme.aut.android.spaceinspector.adapter.NasaImagesAdapter
import hu.bme.aut.android.spaceinspector.databinding.FragmentMarsRoverMenuBinding
import hu.bme.aut.android.spaceinspector.databinding.FragmentNasaImagesListBinding
import hu.bme.aut.android.spaceinspector.model.image.Item
import hu.bme.aut.android.spaceinspector.view.marsrover.RoversState
import hu.bme.aut.android.spaceinspector.viewmodel.MarsRoverMenuFragmentViewModel
import hu.bme.aut.android.spaceinspector.viewmodel.NasaImagesListFragmentViewModel

class NasaImagesListFragment : Fragment(), NasaImagesAdapter.OnImageClickedListener {
    private lateinit var binding: FragmentNasaImagesListBinding

    private val viewModel: NasaImagesListFragmentViewModel by viewModels()

    private lateinit var loadingDialog: LottieProgressDialog

    lateinit var adapter: NasaImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNasaImagesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.state != null) {
            render(viewModel.state!!.value!!)
        }
    }

    private fun initView() {
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

        adapter = NasaImagesAdapter(requireContext(), this)
        binding.recyclerViewImages.adapter = adapter

        binding.recyclerViewImages.visibility = View.GONE
        binding.tvNoImages.visibility = View.VISIBLE
        binding.btnNext.isEnabled = false
        binding.btnPrevious.isEnabled = false

        binding.btnSearch.setOnClickListener {
            viewModel.page = null
            binding.recyclerViewImages.scrollToPosition(0)
            viewModel.getImages(binding.etSearchText.text.toString()).observe(viewLifecycleOwner
            ) { imageState ->
                render(imageState)
            }
        }
    }

    private fun render(state: NasaImageState) {
        when (state) {
            is NasaImageState.inProgress -> {
                loadingDialog.show()
            }
            is NasaImageState.responseSuccess -> {
                loadingDialog.dismiss()
                adapter.addImages(state.data.collection.items)
                binding.recyclerViewImages.visibility = View.VISIBLE
                binding.tvNoImages.visibility = View.GONE

                if (viewModel.page == null)
                    viewModel.page = 1

                state.data.collection.links.forEach {
                    if (it.rel == "prev") {
                        binding.btnPrevious.setOnClickListener {
                            viewModel.page = (viewModel?.page?.minus(1)) ?: 0
                            viewModel.getImages(binding.etSearchText.text.toString()).observe(viewLifecycleOwner
                            ) { imageState ->
                                render(imageState)
                            }
                        }
                    }
                    if (it.rel == "next") {
                        binding.btnNext.setOnClickListener {
                            viewModel.page = (viewModel?.page?.plus(1)) ?: 0
                            viewModel.getImages(binding.etSearchText.text.toString()).observe(viewLifecycleOwner
                            ) { imageState ->
                                render(imageState)
                            }
                        }
                    }
                }
                binding.btnNext.isEnabled = state.data.collection.links.map { l -> l.rel }.contains("next")
                binding.btnPrevious.isEnabled = state.data.collection.links.map { l -> l.rel }.contains("prev")
            }
            is NasaImageState.responseError -> {
                loadingDialog.dismiss()
                AwesomeDialog.build(requireActivity())
                    .title("Error")
                    .body(state.exceptionMsg)
                    .icon(R.drawable.error)
                    .onPositive("Close")
            }
        }
    }

    override fun onImageClickedListener(image: Item?) {
        val b = Bundle()
        b.putSerializable("IMAGE", image)
        findNavController().navigate(R.id.action_nasaImagesListFragment_to_nasaImagesDetailsFragment, b)
    }
}