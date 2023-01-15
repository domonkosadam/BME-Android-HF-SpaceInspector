package hu.bme.aut.android.spaceinspector.view.neows

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.awesomedialog.*
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.adapter.ApproachAdapter
import hu.bme.aut.android.spaceinspector.adapter.AsteroidsAdapter
import hu.bme.aut.android.spaceinspector.databinding.FragmentNeoWsDetailsBinding
import hu.bme.aut.android.spaceinspector.model.neows.browse.NearEarthObject


class NeoWsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentNeoWsDetailsBinding

    private lateinit var adapter: ApproachAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val asteroid: NearEarthObject = arguments?.get("ASTEROID") as NearEarthObject

        binding.tvName.text = asteroid.name
        binding.tvDiameter.text = "Diameter: ${asteroid.estimated_diameter.meters.estimated_diameter_min} - ${asteroid.estimated_diameter.meters.estimated_diameter_max} m"
        binding.tvHazardous.text = "Is hazardous: ${asteroid.is_potentially_hazardous_asteroid.toString()}"
        if (asteroid.is_potentially_hazardous_asteroid)
            binding.ivHazardous.setImageResource(R.drawable.hazard)
        else
            binding.ivHazardous.setImageResource(R.drawable.shield)
        binding.btnJpl.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(asteroid.nasa_jpl_url.replace("http", "https")))
            startActivity(browserIntent)
        }
        adapter = ApproachAdapter()
        binding.asteroidsRecyclerView.adapter = adapter
        adapter.addApproaches(asteroid.close_approach_data)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNeoWsDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}