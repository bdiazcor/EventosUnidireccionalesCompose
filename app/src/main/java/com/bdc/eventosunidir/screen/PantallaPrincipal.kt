package com.bdc.eventosunidir.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bdc.eventosunidir.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    viewModel: MainViewModel = viewModel() //obtenemos una instancia del viewModel
) {
    //Creamos una instancia directa de la clase snackBarHostState (controlador de estado de Snackbar)
    //Esta clase gestiona la cola de mensajes emergentes SnackBar que debe mostrarse, el estado del Snackbar activo
    // y la lógica para mostar el Snackbar (ejemplo: ocultar el Snackbar después de un tiempo).
    //Usamos remember paara evitar que se vuelva a crear una copia en cada recomposición
    val snackbarHostState = remember { SnackbarHostState() }

    //Creamos una corrutina LaunchedEffect y pasamos "true" como key con lo que se ejecuta solo una vez
    //Una vez se lanza la corrutina, sigue activa mientras el composable esté en el árbol de recomposición.
    //Es decir, si el composable se recompone porque cambiar el estado, el LauncheEffect no se volvera a lanzar pero
    //el flujo se sigue recolectado porque ya estaba activa la corrutina desde la primera vez.
    // Dentro de LaunchedEffect estamos recolectando el flujo del eventoUI del ViewModel: indicamos el viewModel, accedemos al evento
    //y usamos el método collect que nos va a devolver el evento (mensaje).
    //Collect se va a quedar escuchando permanentemente en el SharedFlow y va a devolver un mensaje nuevo (evento) cada vez que se genere
    //Con este mensaje, llamo al controlador de estado de Snackbar ( snackbarHostState) para que lo muestre.

    LaunchedEffect(true) {
        viewModel.eventoUI.collect { mensaje ->
            snackbarHostState.showSnackbar(mensaje)
        }
    }

    //Creamos ahora la interfaz de usuario de la pantalla principal.
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi aplicación Compose: Ejemplo de eventos unidireccionales") }
            )
        },
        //Parámetro de Scaffold que espera una función compose que muestra el Snackbar o mensaje emergente
        snackbarHost = { SnackbarHost(snackbarHostState) },

        //Al hacer click en el FAB se llama a la función mostrarMensaje del viewModel.
        //Dentro del viewModel, esta función emite un mensaje al flujo: eventoUI
        //Este mensaje lo estamos escuchando desde LaunchedEffect
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.mostrarMensaje()
                }) {
                Text("Mostrar mensaje")
            }
        }
    )
    { paddingValues ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                "Pantalla principal",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                "Este patrón MVVM de evento unidireccional se usa mucho cuando no queremos " +
                        "que un mensaje emergente se vuelva a mostrar, por ejemplo al rotar " +
                        "la pantalla",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                "MutableSharedFlow es mutable y privado para solo el ViewModel pueda" +
                        "emitir eventos",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                "SharedFlow es inmutable y público. Está expuesto a la interfaz de usuario para" +
                        "que lo recolecte con LaunchedEffect y reaccione a estos eventos",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                "La función mostrarMensaje() dispara el evento cuando lo ejecutamos a través" +
                        "del FloatingActionButton",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                "Presionamos el FAB, se ejecuta mostrarMensaje() pero el evento es unidireccional" +
                        "se emite desde ViewModel y se recolecta en LaunchedEffect." +
                        "Si la vista no escucha a través de .collect() el evento se pierde," +
                        "por ello hay que definir bien donde se captura el evento",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                "El evento unidireccional es transmitido solamente una vez. No debemos almacenarlo " +
                        "en estado porque el estado se guarda y aunque haya un cambio de pantalla, " +
                        "sobrevive con lo que el snackbar se mostraría siempre.",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Para casos donde tengamos que mostrar algún mensaje de error con un Snackbar, es" +
                        "preferible trabajar con eventos.",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

        }

    }
}



