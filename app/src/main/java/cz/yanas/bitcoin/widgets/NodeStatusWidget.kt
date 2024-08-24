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
import kotlin.random.Random

class NodeStatusWidget : AppWidgetProvider() {

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
                for (appWidgetId in appWidgetIds) {
                    doUpdate(context, appWidgetManager, appWidgetId)
                }
            }
        }

        fun doUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val configuration = NodeConfigurationRepository.getConfiguration(context, appWidgetId) ?: return
            val updateIntent = WidgetUtils.getUpdateIntent(context, NodeStatusWidget::class, appWidgetId)

            var views: RemoteViews

            try {
                val nodeStatus = NodeStatusProvider.getNodeStatus(configuration)

                views = RemoteViews(context.packageName, R.layout.node_status_widget)
                views.setTextViewText(R.id.node_status_block_height, nodeStatus.blockHeight.toString())
                views.setTextViewText(R.id.node_status_user_agent, nodeStatus.userAgent)
                views.setTextViewText(R.id.node_status_protocol_version, nodeStatus.protocolVersion.toString())
                views.setOnClickPendingIntent(R.id.node_status_refresh, updateIntent)
            } catch (throwable: Throwable) {
                Log.e("NodeStatusWidget", "Node status not available", throwable)
                views = RemoteViews(context.packageName, R.layout.widget_error)
                views.setOnClickPendingIntent(R.id.widget_error_refresh, updateIntent)
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

    }

    class ConfigurationActivity : NodeConfigurationActivity() {

        override fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            CoroutineScope(Dispatchers.IO).launch {
                doUpdate(context, appWidgetManager, appWidgetId)
            }
        }

    }

    class UpdateReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val nodeStatusWidgetName = ComponentName(context, NodeStatusWidget::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(nodeStatusWidgetName)

            doUpdate(context, appWidgetManager, appWidgetIds)
        }

    }

}
