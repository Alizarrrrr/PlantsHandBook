package com.example.plantshandbook.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity (tableName = "stat_list")
data class StatItem(
    @PrimaryKey (autoGenerate = true)
    val id: Int?,
    @ColumnInfo (name = "game_iteration_free")
    var game_iteration_free: Int,
    @ColumnInfo (name = "game_iteration_10g")
    val game_iteration_10g: Int


): Serializable