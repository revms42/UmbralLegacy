package org.ajar.umbrallegacy.content

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import org.ajar.umbrallegacy.model.Ability

@Dao
interface AbilityDAO {

    @Query("SELECT * FROM ${Ability.TABLE_NAME}")
    fun getAll(): List<Ability>

    @Query("SELECT * FROM ${Ability.TABLE_NAME} WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Ability?

    @Query("SELECT * FROM ${Ability.TABLE_NAME} WHERE id = :id")
    fun findById(id: Long): Ability?

    @Query("SELECT * FROM ${Ability.TABLE_NAME} order by name")
    fun selectAll(): Cursor

    @Query("SELECT * FROM ${Ability.TABLE_NAME} WHERE id = :id")
    fun selectById(id: Long): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ob: Ability)

    @Delete
    fun delete(ob: Ability)

    @Query("SELECT * FROM ${Ability.TABLE_NAME} order by name")
    fun observeAllByName(): LiveData<List<Ability>>

    @Query("SELECT * FROM ${Ability.TABLE_NAME} order by type")
    fun observeAllByType(): LiveData<List<Ability>>
}