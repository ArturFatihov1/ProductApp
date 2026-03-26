package com.example.productapp.product.presentation


import android.app.AlertDialog
import android.content.Context
import com.example.productapp.load.cloud.CategoryCloud

class CategoryDialog(private val context: Context) {

    fun show(categories: List<CategoryCloud>, onSelected: (String) -> Unit) {
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