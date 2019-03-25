package se.falukropp

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.support.v4.os.ConfigurationCompat
import android.widget.RemoteViews
import java.time.LocalDate
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class WeekNumberAppWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            // maybe allow override?
            // val currentLocale = ConfigurationCompat.getLocales(context.resources.configuration)[0]

            val weekNumber = GregorianCalendar().get(Calendar.WEEK_OF_YEAR)

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.week_number_app_widget)
            views.setTextViewText(R.id.weekNumber, weekNumber.toString().padStart(2, '0'))

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

