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
    //Estado del Snackbar
    //Creamos una instancia de la clase snackBarHostState (controlador de estado de Snackbar)
    //Esta clase gestiona la cola de mensajes emergentes SnackBar que debe mostrarse, el estado del Snackbar activo
    // y la lógica para mostar el Snackbar (ejemplo: ocultar el Snackbar después de un tiempo).
    //Usamos remember paara evitar que se vuelva a crear una copia en cada recomposición
    val snackbarHostState = remember { SnackbarHostState() }

    //Creamos una corrutina LaunchedEffect para escuchar el flujo de eventos del viewModel (eventoUI)
    // y pasamos "Unit" como parámetro para que se ejecute solo una vez Una vez se lanza la corrutina,
    // esta sigue activa mientras el composable esté activo. Es decir, si el composable se recompone
    // porque cambia el estado, el LauncheEffect no se volvera a lanzar pero el flujo se sigue
    // recolectando.
    // Dentro de LaunchedEffect estamos recolectando el flujo del eventoUI del ViewModel: indicamos el viewModel, accedemos al evento
    //y usamos el método collect que nos va a devolver el evento (mensaje).
    //El método collect devuelve el evento y se va a quedar escuchando permanentemente en el
    // SharedFlow, devolviendo un mensaje nuevo (evento) cada vez que se genere.
    //Luego pasamos el mensaje al controlador de estado de Snackbar (snackbarHostState) para que lo
    // muestre.

    LaunchedEffect(Unit) {
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
        // Aquí indicamos dónde se dibuja el Snackbar y qué estado usa
        snackbarHost = { SnackbarHost(snackbarHostState) },

        // Al pulsar el FAB pedimos al ViewModel que emita un evento (un mensaje) al flujo: eventoUI
        //Este es el mensaje que estamos escuchando desde LaunchedEffect
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
                "Este ejemplo usa eventos unidireccionales en MVVM para mostrar mensajes que solo " +
                        "deben aparecer una vez.",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                "No usamos estado para el Snackbar, porque el estado se guarda y el mensaje se " +
                        "repetiría al rotar la pantalla.",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                "El ViewModel es el encargado de emitir los eventos usando un MutableSharedFlow privado.",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                "La interfaz solo recibe los eventos a través de un SharedFlow público y los escucha" +
                        " con LaunchedEffect.",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                "Al pulsar el botón flotante, el ViewModel emite el evento llamando a mostrarMensaje().",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                "La UI recibe el evento, muestra el Snackbar y el mensaje se consume.",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Los eventos no se guardan: ocurren una sola vez y después desaparecen.",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Este patrón es ideal para errores, avisos y mensajes temporales.",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

        }

    }
}



