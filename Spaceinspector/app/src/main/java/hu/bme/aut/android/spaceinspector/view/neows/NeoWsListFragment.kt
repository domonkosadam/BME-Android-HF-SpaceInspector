package hu.bme.aut.android.spaceinspector.view.neows

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
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
import hu.bme.aut.android.spaceinspector.adapter.AsteroidsAdapter
import hu.bme.aut.android.spaceinspector.databinding.FragmentApodDetailsBinding
import hu.bme.aut.android.spaceinspector.databinding.FragmentNeoWsListBinding
import hu.bme.aut.android.spaceinspector.model.neows.browse.NearEarthObject
import hu.bme.aut.android.spaceinspector.view.apod.ApodState
import hu.bme.aut.android.spaceinspector.viewmodel.ApodDetailsFragmentViewModel
import hu.bme.aut.android.spaceinspector.viewmodel.NeoWsListFragmentViewModel

class NeoWsListFragment : Fragment(), AsteroidsAdapter.OnAsteroidSelectedListener {
    private lateinit var binding: FragmentNeoWsListBinding

    private val viewModel: NeoWsListFragmentViewModel by viewModels()

    private lateinit var loadingDialog: LottieProgressDialog

    private lateinit var adapter: AsteroidsAdapter

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

        adapter = AsteroidsAdapter(this)
        binding.asteroidsRecyclerView.adapter = adapter

        if (viewModel.state == null) {
            viewModel.getAsteroids().observe(
                viewLifecycleOwner
            ) { asteroidsState ->
                render(asteroidsState)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNeoWsListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.state != null) {
            render(viewModel.state!!.value!!)
        }
    }

    private fun render(state: NeoWsBrowseState) {
        when (state) {
            is NeoWsBrowseState.inProgress -> {
                loadingDialog.show()
            }
            is NeoWsBrowseState.responseSuccess -> {
                loadingDialog.dismiss()
                adapter.addAsteroids(state.data.near_earth_objects)

                binding.btnNext.isEnabled = state.data.links.next != null
                binding.btnPrevious.isEnabled = state.data.links.prev != null

                binding.btnNext.setOnClickListener {
                    viewModel.page = state.data.page.number + 1
                    viewModel.getAsteroids().observe(viewLifecycleOwner
                    ) { asteroidsState ->
                        render(asteroidsState)
                    }
                }

                binding.btnPrevious.setOnClickListener {
                    viewModel.page = state.data.page.number - 1
                    viewModel.getAsteroids().observe(viewLifecycleOwner
                    ) { asteroidsState ->
                        render(asteroidsState)
                    }
                }

                binding.tvPageNumber.text = "${state.data.page.number+1} / ${state.data.page.size+1}"
            }
            is NeoWsBrowseState.responseError -> {
                loadingDialog.dismiss()
                AwesomeDialog.build(requireActivity())
                    .title("Error")
                    .body(state.exceptionMsg)
                    .icon(R.drawable.error)
                    .onPositive("Close")
            }
        }
    }

    override fun onAsteroidSelected(asteroid: NearEarthObject?) {
        val b = Bundle()
        b.putSerializable("ASTEROID", asteroid)
        findNavController().navigate(R.id.action_neoWsListFragment_to_neoWsDetailsFragment, b)
    }
}