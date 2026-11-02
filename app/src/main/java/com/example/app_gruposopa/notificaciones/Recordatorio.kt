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


  //AlarmManager la dispara aunque la app esté cerrada.

object Recordatorio {
    const val EXTRA_VIDEO_ID = "videoId"
    const val EXTRA_TITULO = "titulo"
    private const val ID_CANAL = "recordatorios"

    private const val SEGUNDOS_DE_ESPERA = 30

    //toda notificación necesita un canal
    fun crearCanal(context: Context) {
        val canal = NotificationChannelCompat.Builder(ID_CANAL, NotificationManagerCompat.IMPORTANCE_HIGH)
            .setName("Recordatorios de videos")
            .setDescription("Avisos para ver los videos que pediste recordar")
            .build()
        NotificationManagerCompat.from(context).createNotificationChannel(canal)
    }

    fun programar(context: Context, video: ContenidoMasterMartini) {
        val intent = Intent(context, RecordatorioReceiver::class.java)
            .putExtra(EXTRA_VIDEO_ID, video.id)
            .putExtra(EXTRA_TITULO, video.titulo)
        val alarma = PendingIntent.getBroadcast(
            context,
            video.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + SEGUNDOS_DE_ESPERA * 1000L,
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
            .putExtra(EXTRA_VIDEO_ID, videoId)
        val alTocar = PendingIntent.getActivity(
            context,
            videoId.hashCode(),
            abrirVideo,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notificacion = NotificationCompat.Builder(context, ID_CANAL)
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
        val videoId = intent.getStringExtra(Recordatorio.EXTRA_VIDEO_ID) ?: return
        val titulo = intent.getStringExtra(Recordatorio.EXTRA_TITULO).orEmpty()
        Recordatorio.mostrar(context, videoId, titulo)
    }
}
