package com.megamind.todoapp.models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import com.megamind.todoapp.dataBase.AppDataBase


@Entity(tableName = "User")
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val passWord: String,


    )

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM User WHERE id=:id")
    fun selectUserById(id: Int): LiveData<User>

    @Query("SELECT * FROM User WHERE userName=:userName AND passWord=:passWord ")
    fun login(userName: String, passWord: String): LiveData<User>


}


class UserRepository(application: Application) {

    private var userDao: UserDao

    init {
        val dataBase = AppDataBase.getDatabase(application)
        userDao = dataBase.userDao()

    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    fun login(userName: String, passWord: String) {

        userDao.login(userName, passWord)
    }


}
