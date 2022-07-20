package cz.yanas.bitcoin.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MempoolStatusWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        CoroutineScope(Dispatchers.IO).launch {
            val mempoolStatus = MempoolStatusProvider.getMempoolStatus()
            for (appWidgetId in appWidgetIds) {
                doUpdate(context, appWidgetManager, appWidgetId, mempoolStatus)
            }
        }
    }

    private fun doUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, mempoolStatus: MempoolStatus) {
        val views = RemoteViews(context.packageName, R.layout.mempool_status_widget)
        views.setTextViewText(R.id.mempool_status_block_height, mempoolStatus.blockHeight.toString())
        views.setTextViewText(R.id.mempool_status_fastest_fee, mempoolStatus.fastestFee.toString())
        views.setTextViewText(R.id.mempool_status_half_hour_fee, mempoolStatus.halfHourFee.toString())
        views.setTextViewText(R.id.mempool_status_hour_fee, mempoolStatus.hourFee.toString())
        views.setTextViewText(R.id.mempool_status_minimum_fee, mempoolStatus.minimumFee.toString())

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

}
