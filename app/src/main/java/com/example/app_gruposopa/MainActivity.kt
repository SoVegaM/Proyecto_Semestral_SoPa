package com.example.app_gruposopa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.app_gruposopa.data.repository.ContenidosRepository
import com.example.app_gruposopa.navigation.AppNavHost
import com.example.app_gruposopa.navigation.NavigationEvent
import com.example.app_gruposopa.ui.theme.App_GrupoSoPaTheme
import com.example.app_gruposopa.ui.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * Actividad principal: aplica el tema, carga la navegación y escucha los eventos del MainViewModel.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ContenidosRepository.instance.conectarAlmacenamiento(applicationContext)
        setContent {
            App_GrupoSoPaTheme {
                val mainViewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                // Escucha los eventos de navegación que emite el ViewModel
                LaunchedEffect(Unit) {
                    mainViewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(event.route) {
                                    event.popUpToRoute?.let {
                                        popUpTo(it) {
                                            inclusive = event.inclusive
                                            saveState = event.saveState
                                        }
                                    }
                                    launchSingleTop = event.singleTop
                                    restoreState = event.saveState
                                }
                            }
                            is NavigationEvent.PopBackStack -> navController.popBackStack()
                            is NavigationEvent.NavigateUp -> navController.navigateUp()
                        }
                    }
                }

                AppNavHost(navController = navController, mainViewModel = mainViewModel)
            }
        }
    }
}
