package cz.yanas.bitcoin.widgets

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AppMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_main)

        for (viewId in listOf(R.id.source_code, R.id.issue_tracking, R.id.lightning_address)) {
            findViewById<TextView>(viewId).movementMethod = LinkMovementMethod.getInstance()
        }
    }

}
