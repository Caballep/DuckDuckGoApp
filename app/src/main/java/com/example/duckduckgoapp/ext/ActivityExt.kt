package com.example.duckduckgoapp.ext

import android.app.Activity
import com.example.duckduckgoapp.R

fun Activity.deviceModeIsLandscape() = resources.getBoolean(R.bool.isLandscape)
