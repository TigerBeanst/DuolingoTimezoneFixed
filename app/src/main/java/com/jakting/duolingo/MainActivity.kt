package com.jakting.duolingo

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.highcapable.yukihookapi.hook.factory.prefs
import com.highcapable.yukihookapi.hook.xposed.prefs.YukiHookPrefsBridge
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pref = prefs("tiger_duolingo")
        val availableTimezoneList = TimeZone.getAvailableIDs().toMutableList()
        val psStatusText = findViewById<TextView>(R.id.ps_status)

        // Default timezone
        val defaultTzEdit = findViewById<EditText>(R.id.default_tz_edit)
        val defaultTzButton = findViewById<Button>(R.id.default_tz_button)
        defaultTzEdit.setText(pref.getString("default_timezone"))
        defaultTzButton.setOnClickListener {
            if (availableTimezoneList.contains(defaultTzEdit.text.toString())) {
                pref.edit().putString("default_timezone", defaultTzEdit.text.toString()).apply()
                if (pref.getBoolean("is_save", false) == false) {
                    renewTimezone(defaultTzEdit.text.toString(), pref, psStatusText)
                }
                Toast.makeText(this, getText(R.string.setting_success), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getText(R.string.rescue_error), Toast.LENGTH_SHORT).show()
            }
        }

        // Rescue timezone
        val rescueTzEdit = findViewById<EditText>(R.id.rescue_tz_edit)
        val rescueTzButton = findViewById<Button>(R.id.rescue_tz_button)
        rescueTzEdit.setText(pref.getString("rescue_timezone"))
        rescueTzButton.setOnClickListener {
            if (availableTimezoneList.contains(rescueTzEdit.text.toString())) {
                pref.edit().putString("rescue_timezone", rescueTzEdit.text.toString()).apply()
                if (pref.getBoolean("is_save", false) == true) {
                    renewTimezone(rescueTzEdit.text.toString(), pref, psStatusText)
                }
                Toast.makeText(this, getText(R.string.setting_success), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getText(R.string.rescue_error), Toast.LENGTH_SHORT).show()
            }
        }

        val saveModeButton = findViewById<Button>(R.id.function_save)
        val clearModeButton = findViewById<Button>(R.id.function_clear)

        saveModeButton.setOnClickListener {
            pref.edit().putBoolean("is_save", true).apply()
            renewTimezone(
                pref.getString("rescue_timezone", TimeZone.getDefault().id),
                pref,
                psStatusText
            )
            Toast.makeText(this, getText(R.string.setting_success), Toast.LENGTH_SHORT).show()
        }

        clearModeButton.setOnClickListener {
            pref.edit().putBoolean("is_save", false).apply()
            renewTimezone(
                pref.getString("default_timezone", TimeZone.getDefault().id),
                pref,
                psStatusText
            )
            Toast.makeText(this, getText(R.string.setting_success), Toast.LENGTH_SHORT).show()
        }
    }

    private fun Context.renewTimezone(
        nowTimezone: String,
        pref: YukiHookPrefsBridge,
        view: TextView
    ) {
        pref.edit().putString("now_timezone", nowTimezone).apply()
        view.text = getText(R.string.rescue_status).toString() + pref.getString("now_timezone")
    }
}