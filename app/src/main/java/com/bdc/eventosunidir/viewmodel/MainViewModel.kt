package com.bdc.eventosunidir.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // Eventos de UI: _eventoUI es el flujo mutable y privado de tipo SharedFlow que emite Strings (en este caso)
    //MutableSharedFlow no guarda el valor emitido con lo que no es recolectado si la UI no está escuchando
    //Es 100% obligatorio que ña vista esté escuchando ya que son unidireccionales.
    private val _eventoUI = MutableSharedFlow<String>()

    //eventoUI es el flujo público e inmutable
    //Este eventoUI va a ser expuesto a la interfaz de usuario para que escuche a este evento
    //Si la UI recolecta este evento el evento automáticamente desaparece
    //Si la UI no estaba escuchando este evento, también se va a perder.
    //La UI debe de escuchar este evento
    val eventoUI: SharedFlow<String> = _eventoUI


    //Función que lanza una corrutina
    fun mostrarMensaje() {
        viewModelScope.launch {
            //emit es una función suspendida, por ello podemos incluirla dentro de la
            // corrutina (launch). Lo que hace es enviar un nuevo valor al flujo
            _eventoUI.emit("Hola, soy un evento enviado desde el ViewModel")
        }
    }
}

