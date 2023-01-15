package hu.bme.aut.android.spaceinspector.view.marsrover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.devhoony.lottieproegressdialog.LottieProgressDialog
import com.example.awesomedialog.*
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.adapter.AsteroidsAdapter
import hu.bme.aut.android.spaceinspector.adapter.MarsRoverMenuAdapter
import hu.bme.aut.android.spaceinspector.databinding.FragmentMarsRoverMenuBinding
import hu.bme.aut.android.spaceinspector.databinding.FragmentNeoWsListBinding
import hu.bme.aut.android.spaceinspector.model.marsrover.RoverDetails
import hu.bme.aut.android.spaceinspector.view.neows.NeoWsBrowseState
import hu.bme.aut.android.spaceinspector.viewmodel.MarsRoverMenuFragmentViewModel
import hu.bme.aut.android.spaceinspector.viewmodel.NeoWsListFragmentViewModel

class MarsRoverMenuFragment : Fragment(), MarsRoverMenuAdapter.OnRoverSelectedListener {

    private lateinit var binding: FragmentMarsRoverMenuBinding

    private val viewModel: MarsRoverMenuFragmentViewModel by viewModels()

    private lateinit var loadingDialog: LottieProgressDialog

    private lateinit var adapter: MarsRoverMenuAdapter

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

        adapter = MarsRoverMenuAdapter(this)
        binding.roversRecyclerView.adapter = adapter

        viewModel.getMarsRovers().observe(viewLifecycleOwner
        ) { asteroidsState ->
            render(asteroidsState)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMarsRoverMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun render(state: RoversState) {
        when (state) {
            is RoversState.inProgress -> {
                loadingDialog.show()
            }
            is RoversState.responseSuccess -> {
                loadingDialog.dismiss()
                adapter.addRovers(state.data.rovers)
            }
            is RoversState.responseError -> {
                loadingDialog.dismiss()
                AwesomeDialog.build(requireActivity())
                    .title("Error")
                    .body(state.exceptionMsg)
                    .icon(R.drawable.error)
                    .onPositive("Close")
            }
        }
    }

    override fun onRoverSelected(rover: RoverDetails?) {
        val b = Bundle()
        b.putSerializable("ROVER", rover)
        findNavController().navigate(R.id.action_marsRoverMenuFragment_to_marsRoverDetailsFragment, b)
    }
}