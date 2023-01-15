package hu.bme.aut.android.spaceinspector.view

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.spaceinspector.MainActivity
import hu.bme.aut.android.spaceinspector.databinding.FragmentMenuBinding
import java.util.*


class MenuFragment : Fragment(){
    private lateinit var binding: FragmentMenuBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        checkDate()

        initMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun initView() {
        binding.cvApod.setOnClickListener {
            findNavController().navigate(hu.bme.aut.android.spaceinspector.R.id.action_menuFragment_to_apodDetailsFragment)
        }

        binding.cvNeoWs.setOnClickListener {
            findNavController().navigate(hu.bme.aut.android.spaceinspector.R.id.action_menuFragment_to_neoWsListFragment)
        }

        binding.cvMarsRover.setOnClickListener {
            findNavController().navigate(hu.bme.aut.android.spaceinspector.R.id.action_menuFragment_to_marsRoverMenuFragment)
        }

        binding.cvImages.setOnClickListener {
            findNavController().navigate(hu.bme.aut.android.spaceinspector.R.id.action_menuFragment_to_nasaImagesListFragment)
        }
    }

    private fun checkDate() {
        val cal = Calendar.getInstance()
        val currentDate = "${String.format("%02d",cal.get(Calendar.YEAR))}-${String.format("%02d",cal.get(
            Calendar.MONTH)+1)}-${String.format("%02d",cal.get(Calendar.DAY_OF_MONTH))}"

        val prefs: SharedPreferences = requireActivity().getPreferences(MODE_PRIVATE)
        val lastDate = prefs.getString("LAST_APOD", null)

        if (lastDate != null) {
            if (lastDate != currentDate) {
                sendNotification()
            }
        }
        else {
            sendNotification()
        }
        prefs.edit().putString("LAST_APOD", currentDate).apply();
    }

    private fun sendNotification() {
        val intent = Intent(
            requireContext(),
            MainActivity::class.java
        )
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 101, intent, PendingIntent.FLAG_IMMUTABLE)
        val CHANNEL_ID = "channel_name" // The id of the channel.

        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(hu.bme.aut.android.spaceinspector.R.mipmap.ic_launcher)
                .setContentTitle("New APOD image available!")
                .setContentText("Check the new daily APOD image!")
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Channel Name" // The user-visible name of the channel.
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(mChannel)
        }
        notificationManager.notify(101, notificationBuilder.build())
    }

    private fun initMenu() {
        binding.toolbarMenu.inflateMenu(hu.bme.aut.android.spaceinspector.R.menu.main_menu)
        binding.toolbarMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                hu.bme.aut.android.spaceinspector.R.id.favourites -> {
                    findNavController().navigate(hu.bme.aut.android.spaceinspector.R.id.action_menuFragment_to_favouritesListFragment)
                    true
                }
                else ->
                    true
            }
        }
    }
}