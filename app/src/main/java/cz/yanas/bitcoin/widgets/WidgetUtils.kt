package cz.yanas.bitcoin.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

object WidgetUtils {

    fun getUpdateIntent(context: Context, cls: KClass<*>, appWidgetId: Int): PendingIntent {
        val updateIntent = Intent(context, cls.java)
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
        updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

        return PendingIntent.getBroadcast(
            context, appWidgetId, updateIntent, PendingIntent.FLAG_IMMUTABLE
        )
    }

}