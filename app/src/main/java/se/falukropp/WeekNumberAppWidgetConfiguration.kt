package se.falukropp

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.LocaleList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import se.falukropp.databinding.WeekNumberAppWidgetConfigurationBinding
import java.util.*

// Based on https://github.com/android/user-interface-samples/blob/main/AppWidget/app/src/main/java/com/example/android/appwidget/rv/list/ListWidgetConfigureActivity.kt
class WeekNumberAppWidgetConfiguration : AppCompatActivity() {

    var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED)

        // https://github.com/android/user-interface-samples/blob/main/AppWidget/app/src/main/res/layout/activity_widget_configure.xml
        val layoutBinding = WeekNumberAppWidgetConfigurationBinding.inflate(layoutInflater)
        setContentView(layoutBinding.root)
        val selectedLocale = WeekNumberWidgetPrefs.loadLocaleSetting(this)
        layoutBinding.localesList.adapter =
            LocalesAdapter(this.resources.configuration.locales, selectedLocale) { locale ->
                saveLocaleSettingAndExit(locale)
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

    private fun saveLocaleSettingAndExit(locale: Locale) {
        WeekNumberWidgetPrefs.saveLocaleSetting(this, locale)

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

// https://github.com/android/views-widgets-samples/tree/main/RecyclerViewKotlin
// Based on https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
// https://developer.android.com/codelabs/android-paging#0
class LocalesAdapter(
    private val locales: LocaleList,
    private val selectedLocale: Locale?,
    private val listener: (Locale) -> Unit
) :
    RecyclerView.Adapter<LocalesAdapter.ViewHolder>() {

    // holder class to hold reference
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //get view reference
        var localesText: TextView = view.findViewById(R.id.locales_text) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create view holder to hold reference
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.locales_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locale = locales[position]
        if (locale == selectedLocale) {
            holder.localesText.setTypeface(null, Typeface.BOLD)
        }
        holder.localesText.text = locale.displayName

        // https://antonioleiva.com/recyclerview-listener/
        holder.itemView.setOnClickListener { listener(locale) }
    }

    override fun getItemCount(): Int {
        return locales.size()
    }

    // update your data
    //    fun updateData(locales: ArrayList<Locale>) {
    //        locales.clear()
    //        notifyDataSetChanged()
    //        locales.addAll(locales)
    //        notifyDataSetChanged()
    //    }
}