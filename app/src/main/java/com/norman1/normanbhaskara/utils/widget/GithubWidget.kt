package com.norman1.normanbhaskara.utils.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.norman1.normanbhaskara.R

class GithubWidget : AppWidgetProvider() {

    companion object {
        private const val TOAST_ACTION = "com.norman1.normanbhaskara.TOAST_ACTION"
        const val EXTRA_ITEM = "com.norman1.normanbhaskara.EXTRA_ITEM"

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, WidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.github_widget)
            views.setRemoteAdapter(R.id.appwidget_list, intent)

            val toastIntent = Intent(context, WidgetService::class.java)
            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()
            val toastPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setPendingIntentTemplate(R.id.appwidget_list, toastPendingIntent)

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.layout.github_widget)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }
    }

    override fun onEnabled(context: Context) {
        Toast.makeText(context, "onEnabled called", Toast.LENGTH_LONG).show()
    }

    override fun onDisabled(context: Context) {
        Toast.makeText(context, "onDisabled called", Toast.LENGTH_LONG).show()
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != null) {
            if (intent.action == TOAST_ACTION) {
                val viewIndex = intent.getIntExtra(EXTRA_ITEM, 0)
                Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
            }
        }
    }
}