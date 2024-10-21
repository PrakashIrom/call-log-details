package com.example.call_logs.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.call_logs.data.model.CallLogTable

@Database(entities = [CallLogTable::class], version = 1, exportSchema = false)
abstract class CallLogDatabase: RoomDatabase() {

    abstract fun callLogDao(): CallLogDAO

    companion object{

        @Volatile
        private var Instance:CallLogDatabase?=null

        fun getDatabase(context: Context):CallLogDatabase{

            return Instance?: synchronized(this){
                Room.databaseBuilder(context,  CallLogDatabase::class.java,"call_log_database")
                    .fallbackToDestructiveMigrationFrom()
                    .build()
                    .also{ Instance=it}
            }

        }
    }
}