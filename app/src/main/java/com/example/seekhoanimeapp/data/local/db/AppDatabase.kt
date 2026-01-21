package com.example.seekhoanimeapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.seekhoanimeapp.data.local.entity.AnimeEntity
@Database(
    entities = [AnimeEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {

                db.execSQL(
                    "ALTER TABLE anime ADD COLUMN synopsis TEXT"
                )

                db.execSQL(
                    "ALTER TABLE anime ADD COLUMN genres TEXT"
                )

                db.execSQL(
                    "ALTER TABLE anime ADD COLUMN trailerUrl TEXT"
                )

                db.execSQL(
                    "ALTER TABLE anime ADD COLUMN duration TEXT"
                )

                db.execSQL(
                    "ALTER TABLE anime ADD COLUMN isDetailsFetched INTEGER NOT NULL DEFAULT 0"
                )
            }
        }
    }
}

