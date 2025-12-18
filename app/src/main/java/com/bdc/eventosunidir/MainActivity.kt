package com.bdc.eventosunidir

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bdc.eventosunidir.screen.PantallaPrincipal
import com.bdc.eventosunidir.ui.theme.EventosUnidirTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventosUnidirTheme {
                PantallaPrincipal()
            }
        }
    }
}

