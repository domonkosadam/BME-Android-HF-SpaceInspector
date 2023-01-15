package hu.bme.aut.android.spaceinspector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.spaceinspector.database.ApodDatabase
import hu.bme.aut.android.spaceinspector.model.DatabaseResult
import hu.bme.aut.android.spaceinspector.model.apod.Apod
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavouritesListFragmentViewModel : ViewModel() {
    lateinit var database: ApodDatabase

    var state: LiveData<List<Apod>>? = null

    fun getFavourites(): LiveData<List<Apod>> {
        val result = MutableLiveData<List<Apod>>()
        GlobalScope.launch {
            result.postValue(database.apodDao().getAll())
            state = result
        }
        return result
    }

    fun removeFromDataBase(apod: Apod): LiveData<DatabaseResult> {
        val result = MutableLiveData<DatabaseResult>()
        GlobalScope.launch {
            database.apodDao().deleteItem(apod.title, apod.date)
            result.postValue(DatabaseResult.success(true))
        }
        return result
    }
}