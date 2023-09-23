package com.megamind.todoapp.models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import com.megamind.todoapp.dataBase.AppDataBase
import java.util.Date


@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String,
    val date: Date?,
    val description: String,
    val done: Boolean?
)


@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE done=0 ORDER BY id ")
    fun getAllTask(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE id= :taskId")
    fun getTaskById(taskId: Int): LiveData<Task>

    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun UdateTask(task: Task)

}


class TaskRepository(application: Application) {


    private var taskDao: TaskDao

    init {
        val dataBase = AppDataBase.getDatabase(application)
        taskDao = dataBase.taskDao()
    }




    val allTask: LiveData<List<Task>> = taskDao.getAllTask()


    fun taskById(taskId: Int):LiveData<Task> = taskDao.getTaskById(taskId)

    @Suppress("RedundantSuspendModifier")
    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun deleteTask(task: Task){

        taskDao.deleteTask(task)
    }

}
