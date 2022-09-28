package cz.yanas.bitcoin.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CombinedStatusWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        doUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            NodeConfigurationRepository.clearConfiguration(context, appWidgetId)
        }
    }

    internal companion object {

        fun doUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
            CoroutineScope(Dispatchers.IO).launch {
                val mempoolStatus = MempoolStatusProvider.getMempoolStatus()
                for (appWidgetId in appWidgetIds) {
                    doUpdate(context, appWidgetManager, appWidgetId, mempoolStatus)
                }
            }
        }

        fun doUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, mempoolStatus: MempoolStatus) {
            val configuration = NodeConfigurationRepository.getConfiguration(context, appWidgetId) ?: return
            var views: RemoteViews

            try {
                val nodeStatus = NodeStatusProvider.getNodeStatus(configuration)

                views = RemoteViews(context.packageName, R.layout.combined_status_widget)
                views.setTextViewText(R.id.node_status_block_height, nodeStatus.blockHeight.toString())
                views.setTextViewText(R.id.node_status_user_agent, nodeStatus.userAgent)
                views.setTextViewText(R.id.node_status_protocol_version, nodeStatus.protocolVersion.toString())
                views.setTextViewText(R.id.mempool_status_block_height, mempoolStatus.blockHeight.toString())
                views.setTextViewText(R.id.mempool_status_fastest_fee, mempoolStatus.fastestFee.toString())
                views.setTextViewText(R.id.mempool_status_half_hour_fee, mempoolStatus.halfHourFee.toString())
                views.setTextViewText(R.id.mempool_status_hour_fee, mempoolStatus.hourFee.toString())
                views.setTextViewText(R.id.mempool_status_economy_fee, mempoolStatus.economyFee.toString())
                views.setTextViewText(R.id.mempool_status_minimum_fee, mempoolStatus.minimumFee.toString())
            } catch (throwable: Throwable) {
                Log.e("CombinedStatusWidget", "Node status not available", throwable)
                views = RemoteViews(context.packageName, R.layout.widget_error)
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    class ConfigurationActivity : NodeConfigurationActivity() {

        override fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            CoroutineScope(Dispatchers.IO).launch {
                val mempoolStatus = MempoolStatusProvider.getMempoolStatus()
                doUpdate(context, appWidgetManager, appWidgetId, mempoolStatus)
            }
        }

    }

    class UpdateReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val combinedStatusWidgetName = ComponentName(context, CombinedStatusWidget::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(combinedStatusWidgetName)

            doUpdate(context, appWidgetManager, appWidgetIds)
        }

    }

}
