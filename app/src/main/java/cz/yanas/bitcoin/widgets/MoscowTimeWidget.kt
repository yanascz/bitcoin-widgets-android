package cz.yanas.bitcoin.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Currency

class MoscowTimeWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        doUpdate(context, appWidgetManager, appWidgetIds)
    }

    internal companion object {

        fun doUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
            CoroutineScope(Dispatchers.IO).launch {
                val moscowTime = MoscowTimeProvider.getMoscowTime()
                for (appWidgetId in appWidgetIds) {
                    doUpdate(context, appWidgetManager, appWidgetId, moscowTime)
                }
            }
        }

        private fun doUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, moscowTime: MoscowTime) {
            val updateIntent = WidgetUtils.getUpdateIntent(context, MoscowTimeWidget::class, appWidgetId)

            val views = RemoteViews(context.packageName, R.layout.moscow_time_widget)
            views.setTextViewText(R.id.moscow_time_primary, time(moscowTime.primaryPrice))
            views.setTextViewText(R.id.moscow_time_primary_price, price(moscowTime.primaryPrice, moscowTime.primaryCurrencyCode))
            views.setTextViewText(R.id.moscow_time_secondary, time(moscowTime.secondaryPrice))
            views.setTextViewText(R.id.moscow_time_secondary_price, price(moscowTime.secondaryPrice, moscowTime.secondaryCurrencyCode))
            views.setOnClickPendingIntent(R.id.moscow_time_refresh, updateIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun time(price: Double): String {
            return (100_000_000 / price).toInt().toString()
        }

        private fun price(price: Double, currencyCode: String): String {
            val numberFormat = NumberFormat.getCurrencyInstance()
            numberFormat.currency = Currency.getInstance(currencyCode)
            numberFormat.maximumFractionDigits = 0

            return numberFormat.format(price)
        }

    }

    class UpdateReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val moscowTimeWidgetName = ComponentName(context, MoscowTimeWidget::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(moscowTimeWidgetName)

            doUpdate(context, appWidgetManager, appWidgetIds)
        }

    }

}
