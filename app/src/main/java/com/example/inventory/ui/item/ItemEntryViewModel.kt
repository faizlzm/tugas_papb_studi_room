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

package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import java.text.NumberFormat

/**
 * ViewModel untuk memvalidasi dan memasukkan item ke dalam database Room.
 */
class ItemEntryViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    /**
     * Menyimpan status UI item saat ini.
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /**
     * Memperbarui [itemUiState] dengan nilai yang diberikan pada argumen. Metode ini juga
     * memicu validasi untuk nilai input.
     */
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    // Fungsi untuk menyimpan item ke dalam database jika input valid
    suspend fun saveItem() {
        if (validateInput()) { // Memeriksa validitas input sebelum menyimpan
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }

    // Fungsi privat untuk memvalidasi input
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank() // Memastikan semua field terisi
        }
    }
}

/**
 * Mewakili status UI untuk sebuah item.
 */
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(), // Detail item yang diisi pengguna
    val isEntryValid: Boolean = false // Status validitas input
)

// Data class untuk menyimpan detail dari item, termasuk id, nama, harga, dan kuantitas
data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)

/**
 * Fungsi ekstensi untuk mengonversi [ItemDetails] menjadi [Item]. Jika nilai dari [ItemDetails.price]
 * bukan [Double] yang valid, maka harga akan diatur menjadi 0.0. Begitu juga jika nilai dari
 * [ItemDetails.quantity] bukan [Int] yang valid, maka kuantitas akan diatur menjadi 0.
 */
fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0, // Mengonversi harga, default ke 0.0 jika tidak valid
    quantity = quantity.toIntOrNull() ?: 0 // Mengonversi kuantitas, default ke 0 jika tidak valid
)

// Fungsi ekstensi untuk memformat harga item menjadi bentuk mata uang
fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

/**
 * Fungsi ekstensi untuk mengonversi [Item] menjadi [ItemUiState].
 */
fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(), // Mengonversi ke ItemDetails
    isEntryValid = isEntryValid // Menyimpan status validitas input
)

/**
 * Fungsi ekstensi untuk mengonversi [Item] menjadi [ItemDetails].
 */
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)

