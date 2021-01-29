package org.ajar.umbrallegacy.content

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.work.*
import org.ajar.umbrallegacy.model.Ability
import org.ajar.umbrallegacy.model.PrincipleAbilityType

class InitialAbilityLoader(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val contentDAO = AbilityDatabase.instance?.abilityDao()
        contentDAO?.also {
            if (it.getAll().isEmpty()) {
                PrincipleAbilityType.values().flatMap { abilityType ->
                    abilityType.collection.asIterable()
                }.map { abilityDefinition ->
                    Ability.create(abilityDefinition, applicationContext)
                }.forEach { ability ->
                    it.insert(ability)
                }
            }
        }
        return Result.success()
    }
}

@Database(entities = [ Ability::class ], version = 1)
@TypeConverters(ContentTypeConverter::class)
abstract class AbilityDatabase : RoomDatabase() {
    abstract fun abilityDao(): AbilityDAO

    companion object {
        private const val DATABASE_NAME = "AbilityDatabase"

        private var database: AbilityDatabase? = null
        val instance: AbilityDatabase?
            get() = database

        fun init(context: Context): LiveData<Operation.State>? {
            if(database == null) {
                database = Room.databaseBuilder(context, AbilityDatabase::class.java, DATABASE_NAME).allowMainThreadQueries().build()

                val workManager = WorkManager.getInstance(context)

                val operation = workManager.enqueue(OneTimeWorkRequest.from(InitialAbilityLoader::class.java))
                return operation.state
            }
            return null
        }
    }
}