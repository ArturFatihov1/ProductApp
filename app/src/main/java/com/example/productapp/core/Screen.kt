package com.example.productapp.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {
    fun show(containerId: Int, fragmentManager: FragmentManager)

    abstract class Replace(
        private val fragmentClass: Class<out Fragment>,
        private val addToBackStack: Boolean = false
    ) : Screen {
        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            val transaction = fragmentManager.beginTransaction()
            transaction.setReorderingAllowed(true)
                .replace(containerId, fragmentClass.getDeclaredConstructor().newInstance())
            if (addToBackStack) {
                transaction.addToBackStack(fragmentClass.simpleName)
            }
            transaction.commit()
        }
    }
}