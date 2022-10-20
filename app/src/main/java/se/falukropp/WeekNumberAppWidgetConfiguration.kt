package se.falukropp

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import se.falukropp.databinding.WeekNumberAppWidgetConfigurationBinding

class WeekNumberAppWidgetConfiguration  : AppCompatActivity() {

    var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED)

        // https://github.com/android/user-interface-samples/blob/main/AppWidget/app/src/main/res/layout/activity_widget_configure.xml
        // https://developer.android.com/topic/libraries/view-binding
        val binding = WeekNumberAppWidgetConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // title = getString(R.string.select_list_for_widget)

        binding.configurationDone.setOnClickListener {
            onWidgetContainerClicked()
        }

        // Find the widget id from the intent.
        appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }
    }

    private fun onWidgetContainerClicked(/*@LayoutRes widgetLayoutResId: Int*/) {

        // https://github.com/android/user-interface-samples/blob/main/AppWidget/app/src/main/java/com/example/android/appwidget/rv/list/ListSharedPrefsUtil.kt

        // ListSharedPrefsUtil.saveWidgetLayoutIdPref(this, appWidgetId, widgetLayoutResId)
        // It is the responsibility of the configuration activity to update the app widget
        val appWidgetManager = AppWidgetManager.getInstance(this)
        WeekNumberAppWidget.updateAppWidget(this, appWidgetManager, appWidgetId)

        // Make sure we pass back the original appWidgetId
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(RESULT_OK, resultValue)
        finish()
    }
}