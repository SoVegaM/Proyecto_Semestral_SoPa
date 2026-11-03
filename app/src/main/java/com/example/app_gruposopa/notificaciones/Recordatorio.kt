package com.example.app_gruposopa.notificaciones

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.app_gruposopa.MainActivity
import com.example.app_gruposopa.R
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini

// Valores fijos de los recordatorios
data class ConfigRecordatorio(
    val extraVideoId: String = "videoId",
    val extraTitulo: String = "titulo",
    val idCanal: String = "recordatorios",
    val segundosDeEspera: Int = 30
)

// AlarmManager la dispara aunque la app esté cerrada.
object Recordatorio {
    val config = ConfigRecordatorio()

    // toda notificación necesita un canal
    fun crearCanal(context: Context) {
        val canal = NotificationChannelCompat.Builder(config.idCanal, NotificationManagerCompat.IMPORTANCE_HIGH)
            .setName("Recordatorios de videos")
            .setDescription("Avisos para ver los videos que pediste recordar")
            .build()
        NotificationManagerCompat.from(context).createNotificationChannel(canal)
    }

    fun programar(context: Context, video: ContenidoMasterMartini) {
        val intent = Intent(context, RecordatorioReceiver::class.java)
            .putExtra(config.extraVideoId, video.id)
            .putExtra(config.extraTitulo, video.titulo)
        val alarma = PendingIntent.getBroadcast(
            context,
            video.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + config.segundosDeEspera * 1000L,
            alarma
        )
    }

    // El permiso se revisa antes de programar, y areNotificationsEnabled cubre si se quitó después
    @SuppressLint("MissingPermission")
    fun mostrar(context: Context, videoId: String, titulo: String) {
        val manager = NotificationManagerCompat.from(context)
        if (!manager.areNotificationsEnabled()) return

        // Al tocarla abre la app en ese video
        val abrirVideo = Intent(context, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .putExtra(config.extraVideoId, videoId)
        val alTocar = PendingIntent.getActivity(
            context,
            videoId.hashCode(),
            abrirVideo,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notificacion = NotificationCompat.Builder(context, config.idCanal)
            .setSmallIcon(R.drawable.ic_notificacion)
            .setContentTitle("Tienes un video pendiente")
            .setContentText(titulo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(alTocar)
            .setAutoCancel(true)
            .build()
        manager.notify(videoId.hashCode(), notificacion)
    }
}

// Lo llama AlarmManager cuando se cumple el tiempo
class RecordatorioReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val videoId = intent.getStringExtra(Recordatorio.config.extraVideoId) ?: return
        val titulo = intent.getStringExtra(Recordatorio.config.extraTitulo).orEmpty()
        Recordatorio.mostrar(context, videoId, titulo)
    }
}