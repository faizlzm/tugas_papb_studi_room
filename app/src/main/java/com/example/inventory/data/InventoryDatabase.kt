/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false) // Anotasi untuk mendefinisikan database Room dengan entitas Item dan versi 1, serta tidak mengekspor skema
abstract class InventoryDatabase : RoomDatabase() {

    // Mendeklarasikan fungsi abstrak untuk mengakses DAO (Data Access Object) ItemDao
    abstract fun itemDao(): ItemDao

    companion object {
        // Properti Instance bersifat volatile agar perubahan yang dilakukan pada instance ini segera terlihat oleh thread lain
        @Volatile
        private var Instance: InventoryDatabase? = null

        // Fungsi untuk mendapatkan instance database, menggunakan pola singleton untuk memastikan hanya ada satu instance database yang dibuat
        fun getDatabase(context: Context): InventoryDatabase {
            // Jika Instance tidak null, kembalikan instance tersebut. Jika null, buat instance baru.
            return Instance ?: synchronized(this) {
                // Membangun instance database baru menggunakan Room dan menetapkan nama database sebagai "item_database"
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .build().also { Instance = it } // Mengatur instance database baru ke Instance
            }
        }
    }
}


