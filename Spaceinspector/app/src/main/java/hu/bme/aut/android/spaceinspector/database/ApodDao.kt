package hu.bme.aut.android.spaceinspector.database

import android.icu.text.CaseMap
import androidx.room.*
import hu.bme.aut.android.spaceinspector.model.apod.Apod


@Dao
interface ApodDao {
    @Query("SELECT * FROM apod")
    fun getAll(): List<Apod>

    @Query("DELETE FROM apod")
    fun deleteAll()

    @Query("DELETE FROM apod WHERE title = :title AND date = :date")
    fun deleteItem(title: String, date: String)

    @Query("SELECT * FROM apod WHERE title = :title AND date = :date")
    fun isFavourite(title: String, date: String): Apod

    @Insert
    fun insert(apod: Apod): Long

    @Update
    fun update(apod: Apod)

    @Delete
    fun delete(apod: Apod)
}