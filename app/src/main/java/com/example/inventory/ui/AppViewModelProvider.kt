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

package com.example.inventory.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetailsViewModel
import com.example.inventory.ui.item.ItemEditViewModel
import com.example.inventory.ui.item.ItemEntryViewModel
/**
 * Menyediakan Factory untuk membuat instance ViewModel bagi seluruh aplikasi Inventory.
 */
object AppViewModelProvider {
    // Factory untuk membuat ViewModel yang digunakan dalam aplikasi
    val Factory = viewModelFactory {

        // Initializer untuk ItemEditViewModel
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle() // Membuat SavedStateHandle untuk mengelola status yang disimpan
            )
        }

        // Initializer untuk ItemEntryViewModel
        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
            // Mengambil repository dari aplikasi Inventory untuk menginisialisasi ItemEntryViewModel
        }

        // Initializer untuk ItemDetailsViewModel
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle() // Membuat SavedStateHandle untuk status yang disimpan
            )
        }

        // Initializer untuk HomeViewModel
        initializer {
            HomeViewModel() // Membuat instance HomeViewModel tanpa parameter tambahan
        }
    }
}

/**
 * Fungsi ekstensi untuk mencari objek [Application] dan mengembalikan instance dari
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
// Mengakses InventoryApplication dari CreationExtras menggunakan kunci APPLICATION_KEY
