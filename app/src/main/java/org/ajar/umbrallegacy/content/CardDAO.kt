package org.ajar.umbrallegacy.content

import androidx.room.*
import org.ajar.umbrallegacy.model.Card

@Dao
interface CardDAO {

    @Query("SELECT * FROM ${Card.TABLE_NAME}")
    fun getAll(): List<Card>

    @Query("SELECT * FROM ${Card.TABLE_NAME} WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Card?

    @Query("SELECT * FROM ${Card.TABLE_NAME} WHERE id = :id")
    fun findById(id: Int): Card?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(ob: Card)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ob: Card)

    @Delete
    fun delete(ob: Card)
}