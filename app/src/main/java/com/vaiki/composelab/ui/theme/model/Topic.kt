package com.vaiki.composelab.ui.theme.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(@StringRes val title: Int, val qty: Int, @DrawableRes val imageRes: Int)
