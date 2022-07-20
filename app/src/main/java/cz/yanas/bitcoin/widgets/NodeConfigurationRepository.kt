package cz.yanas.bitcoin.widgets

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object NodeConfigurationRepository {

    internal fun getConfiguration(context: Context, appWidgetId: Int): NodeConfiguration? {
        val sharedPreferences = getSharedPreferences(context)
        val host = sharedPreferences.getString(hostKey(appWidgetId), null)
        val port = sharedPreferences.getInt(portKey(appWidgetId), 0)

        if (host != null && port > 0) {
            return NodeConfiguration(host = host, port = port)
        }

        return null
    }

    internal fun setConfiguration(context: Context, appWidgetId: Int, configuration: NodeConfiguration) {
        getSharedPreferences(context).edit {
            putString(hostKey(appWidgetId), configuration.host)
            putInt(portKey(appWidgetId), configuration.port)
        }
    }

    internal fun clearConfiguration(context: Context, appWidgetId: Int) {
        getSharedPreferences(context).edit {
            remove(hostKey(appWidgetId))
            remove(portKey(appWidgetId))
        }
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("cz.yanas.bitcoin.widgets.NodeConfiguration", 0)
    }

    private fun portKey(appWidgetId: Int) = "port_$appWidgetId"
    private fun hostKey(appWidgetId: Int) = "host_$appWidgetId"

}
