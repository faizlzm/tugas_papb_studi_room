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

import kotlinx.coroutines.flow.Flow

/**
 * Repository yang menyediakan fungsi untuk menyisipkan, memperbarui, menghapus, dan mengambil [Item] dari sumber data tertentu.
 */
interface ItemsRepository {

    /**
     * Mengambil semua item dari sumber data yang diberikan.
     * Mengembalikan data dalam bentuk Flow<List<Item>> untuk mendukung pemantauan perubahan data secara real-time.
     */
    fun getAllItemsStream(): Flow<List<Item>>

    /**
     * Mengambil item dari sumber data yang sesuai dengan [id] yang diberikan.
     * Mengembalikan data dalam bentuk Flow<Item?> untuk pemantauan perubahan data secara real-time.
     */
    fun getItemStream(id: Int): Flow<Item?>

    /**
     * Menyisipkan item ke dalam sumber data.
     * Bersifat suspend agar dapat dijalankan secara asynchronous.
     */
    suspend fun insertItem(item: Item)

    /**
     * Menghapus item dari sumber data.
     * Bersifat suspend untuk operasi asynchronous.
     */
    suspend fun deleteItem(item: Item)

    /**
     * Memperbarui item dalam sumber data.
     * Bersifat suspend untuk operasi asynchronous.
     */
    suspend fun updateItem(item: Item)
}


