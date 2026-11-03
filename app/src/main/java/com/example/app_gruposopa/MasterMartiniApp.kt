package com.example.app_gruposopa

import android.app.Application
import com.example.app_gruposopa.data.repository.ContenidosRepository
import com.example.app_gruposopa.notificaciones.Recordatorio

class MasterMartiniApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ContenidosRepository.instance.conectarAlmacenamiento(this)
        Recordatorio.crearCanal(this)
    }
}
