package com.osamaalek.kiosklauncher.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.osamaalek.kiosklauncher.R
import com.osamaalek.kiosklauncher.util.KioskUtil

class HomeFragment : Fragment() {

    private lateinit var fabApps: FloatingActionButton
    private lateinit var imageButtonExit: ImageButton
    private var backPressCount = 0 // Contador de cliques no botão de voltar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home, container, false)

        fabApps = v.findViewById(R.id.floatingActionButton)
        imageButtonExit = v.findViewById(R.id.imageButton_exit)

        fabApps.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, AppsListFragment()).commit()
        }

        imageButtonExit.setOnClickListener {
            KioskUtil.stopKioskMode(requireActivity())
        }

        // Configura o listener para o botão de voltar
        v.isFocusableInTouchMode = true
        v.requestFocus()
        v.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                backPressCount++
                if (backPressCount >= 4) {
                    KioskUtil.stopKioskMode(requireActivity())
                    backPressCount = 0 // Reseta o contador
                }
                true
            } else {
                false
            }
        }

        return v
    }
}
