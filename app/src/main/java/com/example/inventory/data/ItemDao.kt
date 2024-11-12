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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao // Menandai interface ini sebagai Data Access Object (DAO) untuk mengakses database
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // Menyisipkan data item baru ke dalam tabel, dan jika terdapat konflik (data dengan primary key yang sama), akan diabaikan
    suspend fun insert(item: Item) // Fungsi untuk menyisipkan data item, bersifat suspend agar dapat dijalankan secara asynchronous

    @Update // Menandai fungsi ini untuk memperbarui data item yang sudah ada dalam tabel
    suspend fun update(item: Item) // Fungsi untuk memperbarui data item, juga bersifat suspend untuk operasi asynchronous

    @Delete // Menandai fungsi ini untuk menghapus data item dari tabel
    suspend fun delete(item: Item) // Fungsi untuk menghapus data item, bersifat suspend untuk menjalankan secara asynchronous

    @Query("SELECT * from items WHERE id = :id") // Menjalankan query untuk mengambil item berdasarkan id tertentu
    fun getItem(id: Int): Flow<Item> // Fungsi untuk mendapatkan item berdasarkan id, mengembalikan hasil berupa Flow untuk mendukung pemantauan perubahan data secara real-time

    @Query("SELECT * from items ORDER BY name ASC") // Menjalankan query untuk mengambil semua item dan mengurutkannya berdasarkan nama secara ascending (A-Z)
    fun getAllItems(): Flow<List<Item>> // Fungsi untuk mendapatkan semua item dalam bentuk Flow<List<Item>>, berguna untuk mendeteksi perubahan data secara real-time
}
