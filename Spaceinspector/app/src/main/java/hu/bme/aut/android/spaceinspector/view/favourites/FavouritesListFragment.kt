package hu.bme.aut.android.spaceinspector.view.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.devhoony.lottieproegressdialog.LottieProgressDialog
import com.example.awesomedialog.*
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.adapter.FavouriteApodAdapter
import hu.bme.aut.android.spaceinspector.adapter.MarsRoverDetailsAdapter
import hu.bme.aut.android.spaceinspector.database.ApodDatabase
import hu.bme.aut.android.spaceinspector.databinding.FragmentApodDetailsBinding
import hu.bme.aut.android.spaceinspector.databinding.FragmentFavouritesListBinding
import hu.bme.aut.android.spaceinspector.model.DatabaseResult
import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.viewmodel.ApodDetailsFragmentViewModel
import hu.bme.aut.android.spaceinspector.viewmodel.FavouritesListFragmentViewModel

class FavouritesListFragment : Fragment(), FavouriteApodAdapter.OnApodItemClickedListener {

    private lateinit var binding: FragmentFavouritesListBinding

    private val viewModel: FavouritesListFragmentViewModel by viewModels()

    private lateinit var loadingDialog: LottieProgressDialog

    private lateinit var adapter: FavouriteApodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

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

        viewModel.database = ApodDatabase.getDatabase(requireContext())

        adapter = FavouriteApodAdapter(requireContext(), this)
        binding.favouritesRecyclerView.adapter = adapter

        initView()

    }

    private fun initView() {
        loadingDialog.show()
        viewModel.getFavourites().observe(viewLifecycleOwner
        ) { list ->
            loadingDialog.dismiss()
            adapter.addImages(list)
        }
    }

    override fun onApodItemClicked(image: Apod) {
        val b = Bundle()
        b.putSerializable("APOD", image)
        findNavController().navigate(R.id.action_favouritesListFragment_to_favouritesDetailsFragment, b)
    }

    override fun onApodItemRemove(image: Apod) {
        viewModel.removeFromDataBase(image)
        initView()
    }
}