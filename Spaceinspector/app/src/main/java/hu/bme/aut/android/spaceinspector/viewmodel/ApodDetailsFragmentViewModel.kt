package hu.bme.aut.android.spaceinspector.viewmodel

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.spaceinspector.database.ApodDatabase
import hu.bme.aut.android.spaceinspector.model.DatabaseResult
import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.repository.ApiRepository
import hu.bme.aut.android.spaceinspector.view.apod.ApodState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ApodDetailsFragmentViewModel : ViewModel() {
    private val apiRepository = ApiRepository()

    var img: Drawable? = null

    var date: String? = null

    val currentDate: String

    lateinit var database: ApodDatabase

    var state: LiveData<ApodState>? = null

    init {
        val cal = Calendar.getInstance()
        currentDate = "${String.format("%02d",cal.get(Calendar.YEAR))}-${String.format("%02d",cal.get(Calendar.MONTH)+1)}-${String.format("%02d",cal.get(Calendar.DAY_OF_MONTH))}"
    }

    fun getApodData(): LiveData<ApodState> {
        state = apiRepository.getApodData(date, null, null, null, null, null, true)
        return state!!
    }

    fun increaseDate() {
        val cal = Calendar.getInstance()
        Log.d("Old Date", date.toString())
        val year = date?.subSequence(0, 4).toString().toInt()
        val month = date?.subSequence(5, 7).toString().toInt()
        val day = date?.subSequence(8, 10).toString().toInt()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, day)
        cal.add(Calendar.DATE, 1)
        date = "${String.format("%02d",cal.get(Calendar.YEAR))}-${String.format("%02d",cal.get(Calendar.MONTH))}-${String.format("%02d",cal.get(Calendar.DAY_OF_MONTH))}"
    }

    fun decreaseDate() {
        val cal = Calendar.getInstance()
        Log.d("Old Date", date.toString())
        val year = date?.subSequence(0, 4).toString().toInt()
        val month = date?.subSequence(5, 7).toString().toInt()
        val day = date?.subSequence(8, 10).toString().toInt()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, day)
        cal.add(Calendar.DATE, -1)
        date = "${String.format("%02d",cal.get(Calendar.YEAR))}-${String.format("%02d",cal.get(Calendar.MONTH))}-${String.format("%02d",cal.get(Calendar.DAY_OF_MONTH))}"
    }

    fun isFavourite(apod: Apod): LiveData<DatabaseResult> {
        val result = MutableLiveData<DatabaseResult>()
        result.value = DatabaseResult.inProgress
        GlobalScope.launch {
            val queryResult = database.apodDao().isFavourite(apod.title, apod.date)
            if (queryResult != null)
                result.postValue(DatabaseResult.success(true))
            else
                result.postValue(DatabaseResult.success(false))
        }
        return result
    }

    fun setFavourite(apod: Apod, newValue: Boolean): LiveData<DatabaseResult> {
        val result = MutableLiveData<DatabaseResult>()
        GlobalScope.launch {
            if (newValue)
                database.apodDao().insert(apod)
            else
                database.apodDao().deleteItem(apod.title, apod.date)
            result.postValue(DatabaseResult.success(true))
        }
        return result
    }
}