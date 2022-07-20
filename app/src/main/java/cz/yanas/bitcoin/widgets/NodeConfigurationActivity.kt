package cz.yanas.bitcoin.widgets

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

abstract class NodeConfigurationActivity : AppCompatActivity() {

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_node_configuration)
        setResult(Activity.RESULT_CANCELED)

        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        findViewById<Button>(R.id.configuration_confirm_button).setOnClickListener {
            val configuration = NodeConfiguration(
                host = findViewById<EditText>(R.id.configuration_host).text.toString(),
                port = findViewById<EditText>(R.id.configuration_port).text.toString().toInt()
            )
            NodeConfigurationRepository.setConfiguration(this, appWidgetId, configuration)

            val appWidgetManager = AppWidgetManager.getInstance(this)
            updateAppWidget(this, appWidgetManager, appWidgetId)

            val resultData = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultData)
            finish()
        }

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }
    }

    abstract fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int)

}
