package com.bdc.eventosunidir.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    //Creamos un objeto de tipo MutableSharedFlow<String> que emite Strings (en este caso)
    // _eventoUI es el flujo mutable y privado que guarda la dirección de este objeto.
    //MutableSharedFlow no guarda el valor emitido con lo que no es recolectado si la UI no está escuchando
    private val _eventoUI = MutableSharedFlow<String>()

    //eventoUI es el flujo público e inmutable que guarda la misma dirección de memoria que _eventoUI.
    //Este eventoUI va a ser expuesto a la interfaz de usuario para que escuche a este evento.
    //Si la UI recolecta este evento el evento automáticamente desaparece.
    //Si la UI no estaba escuchando este evento, también se va a perder.
    val eventoUI: SharedFlow<String> = _eventoUI


    //Función que lanza una corrutina
    fun mostrarMensaje() {
        viewModelScope.launch {
            //emit es el método de SharedFlow que emite un valor al flujo.
            // Es una función suspendida, por ello podemos incluirla dentro de la corrutina
            // viewModelScope.launch.
            _eventoUI.emit("Hola, soy un evento enviado desde el ViewModel")
        }
    }
}

