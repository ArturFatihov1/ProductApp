package com.example.productapp.product.presentation


import android.app.AlertDialog
import android.content.Context
import com.example.productapp.load.cloud.CategoryDTO

class CategoryDialog(private val context: Context) {

    fun show(categories: List<CategoryDTO>, onSelected: (String) -> Unit) {
        val names = categories.map { it.name }.toTypedArray()

        AlertDialog.Builder(context)
            .setTitle("Выберите категорию")
            .setItems(names) { _, which ->
                onSelected(categories[which].slug)
            }
            .create()
            .show()
    }
}