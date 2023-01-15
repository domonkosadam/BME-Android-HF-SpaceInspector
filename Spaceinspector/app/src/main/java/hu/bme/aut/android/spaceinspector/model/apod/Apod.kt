package hu.bme.aut.android.spaceinspector.model.apod

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apod")
data class Apod (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo val copyright: String?,
    @ColumnInfo val date: String,
    @ColumnInfo val explanation: String,
    @ColumnInfo val hdurl: String?,
    @ColumnInfo val media_type: String,
    @ColumnInfo val service_version: String,
    @ColumnInfo val title: String,
    @ColumnInfo val url: String,
    @ColumnInfo val thumbnail_url: String?,
) : java.io.Serializable