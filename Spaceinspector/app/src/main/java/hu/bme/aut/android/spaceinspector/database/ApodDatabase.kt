package hu.bme.aut.android.spaceinspector.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.android.spaceinspector.model.apod.Apod

@Database(entities = [Apod::class], version = 1)
abstract class ApodDatabase : RoomDatabase() {
    abstract fun apodDao(): ApodDao

    companion object {
        fun getDatabase(applicationContext: Context): ApodDatabase {
            return Room.databaseBuilder(
                applicationContext,
                ApodDatabase::class.java,
                "apod_database"
            ).build();
        }
    }
}