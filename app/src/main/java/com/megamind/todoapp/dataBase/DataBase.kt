package com.megamind.todoapp.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.megamind.todoapp.DateConverter

import com.megamind.todoapp.models.Task
import com.megamind.todoapp.models.TaskDao


@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)

abstract class AppDataBase() : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {



        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "todoDb"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

