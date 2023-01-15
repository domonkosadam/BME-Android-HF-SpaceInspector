package hu.bme.aut.android.spaceinspector.view.marsrover

import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.devhoony.lottieproegressdialog.LottieProgressDialog
import com.example.awesomedialog.*
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.adapter.AsteroidsAdapter
import hu.bme.aut.android.spaceinspector.adapter.MarsRoverDetailsAdapter
import hu.bme.aut.android.spaceinspector.adapter.MarsRoverMenuAdapter
import hu.bme.aut.android.spaceinspector.databinding.FragmentMarsRoverDetailsBinding
import hu.bme.aut.android.spaceinspector.databinding.FragmentMarsRoverMenuBinding
import hu.bme.aut.android.spaceinspector.model.marsrover.RoverDetails
import hu.bme.aut.android.spaceinspector.viewmodel.MarsRoverDetailsFragmentViewModel
import hu.bme.aut.android.spaceinspector.viewmodel.MarsRoverMenuFragmentViewModel
import java.util.*

class MarsRoverDetailsFragment : Fragment(), MarsRoverDetailsAdapter.OnRoverImageClickedListener, AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentMarsRoverDetailsBinding

    private val viewModel: MarsRoverDetailsFragmentViewModel by viewModels()

    private lateinit var loadingDialog: LottieProgressDialog

    private var selectedRover: RoverDetails? = null

    private lateinit var adapter: MarsRoverDetailsAdapter

    private val cameraItems: MutableList<String> = mutableListOf()

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

        initView()

        if (viewModel.state == null) {
            viewModel.getLatestImages().observe(
                viewLifecycleOwner
            ) { asteroidsState ->
                render(asteroidsState)
            }
        }

        binding.tvDate.setOnClickListener {
            handleDatePicker()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMarsRoverDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.state != null) {
            render(viewModel.state!!.value!!)
        }
    }

    private fun render(state: MarsRoverListState) {
        when (state) {
            is MarsRoverListState.inProgress -> {
                loadingDialog.show()
            }
            is MarsRoverListState.responseSuccess -> {
                loadingDialog.dismiss()
                Log.d("Data", state.data.toString())
                adapter.addImages(state.data)
                if (state.data.isEmpty()) {
                    AwesomeDialog.build(requireActivity())
                        .title("Info")
                        .body("No images available with these parameters")
                        .icon(R.drawable.error)
                        .onPositive("Close")
                }
            }
            is MarsRoverListState.responseError -> {
                loadingDialog.dismiss()
                AwesomeDialog.build(requireActivity())
                    .title("Error")
                    .body(state.exceptionMsg)
                    .icon(R.drawable.error)
                    .onPositive("Close")
            }
        }
    }

    override fun onRoverImageClicked(image: Bitmap) {
        val b = Bundle()
        b.putParcelable("IMAGE", image)
        findNavController().navigate(R.id.action_marsRoverDetailsFragment_to_imageViewFragment, b)
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        if (position == 0) {
            viewModel.cameraCode = null
            loadImages()
        }
        else {
            val cameraCode = selectedRover!!.cameras[position-1].name.lowercase()
            if (cameraCode != viewModel.cameraCode) {
                viewModel.cameraCode = cameraCode
                loadImages()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private fun initView() {
        selectedRover = arguments?.get("ROVER") as RoverDetails?

        cameraItems.clear()
        cameraItems.add("-")
        cameraItems.addAll(selectedRover.let { selectedRover!!.cameras.map { c -> c.full_name } })
        //cameraItems.add(0, "-")

        viewModel.roverName = selectedRover?.name?.lowercase()

        binding.tvDate.text = viewModel.earthDate

        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cameraItems)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCamera.adapter = arrayAdapter
        binding.spCamera.setSelection(0, false);
        binding.spCamera.onItemSelectedListener = this

        adapter = MarsRoverDetailsAdapter(requireContext(), this)
        binding.imagesRecyclerView.adapter = adapter
    }

    private fun handleDatePicker() {
        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            viewModel.earthDate = "${String.format("%02d",cal.get(Calendar.YEAR))}-${String.format("%02d",cal.get(
                Calendar.MONTH)+1)}-${String.format("%02d",cal.get(Calendar.DAY_OF_MONTH))}"
            binding.tvDate.text = viewModel.earthDate

            loadImages()
        }

        val minDate = listOf(
            selectedRover?.landing_date?.subSequence(0, 4)?.toString()?.toInt() ?: 0,
            selectedRover?.landing_date?.subSequence(5, 7)?.toString()?.toInt() ?: 0,
            selectedRover?.landing_date?.subSequence(8, 10)?.toString()?.toInt() ?: 0,
        )
        val maxDate = listOf(
            selectedRover?.max_date?.subSequence(0, 4)?.toString()?.toInt() ?: 0,
            selectedRover?.max_date?.subSequence(5, 7)?.toString()?.toInt() ?: 0,
            selectedRover?.max_date?.subSequence(8, 10)?.toString()?.toInt() ?: 0,
        )
        val dialog = DatePickerDialog(requireContext(), dateSetListener,
            maxDate[0],
            maxDate[1],
            maxDate[2],
        )

        cal.set(Calendar.YEAR, maxDate[0])
        cal.set(Calendar.MONTH, maxDate[1])
        cal.set(Calendar.DAY_OF_MONTH, maxDate[2])
        dialog.datePicker.maxDate = cal.timeInMillis

        cal.set(Calendar.YEAR, minDate[0])
        cal.set(Calendar.MONTH, minDate[1])
        cal.set(Calendar.DAY_OF_MONTH, minDate[2])
        dialog.datePicker.minDate = cal.timeInMillis

        dialog.show()
    }

    private fun loadImages() {
        viewModel.getImages().observe(
            viewLifecycleOwner
        ) { asteroidsState ->
            render(asteroidsState)
        }
    }
}