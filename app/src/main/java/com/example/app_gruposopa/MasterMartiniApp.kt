package com.example.app_gruposopa

import android.app.Application
import com.example.app_gruposopa.data.repository.ContenidosRepository
import com.example.app_gruposopa.notificaciones.Recordatorio

// Parte antes que las pantallas. Aquí se conecta DataStore
class MasterMartiniApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ContenidosRepository.instance.conectarAlmacenamiento(this)
        Recordatorio.crearCanal(this)
    }
}
