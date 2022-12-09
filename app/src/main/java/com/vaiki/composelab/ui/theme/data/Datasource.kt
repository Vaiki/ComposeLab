package com.vaiki.composelab.ui.theme.data

import com.vaiki.composelab.R
import com.vaiki.composelab.ui.theme.model.Affirmation

class Datasource() {
    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(R.string.affirmation1, R.drawable.img),
            Affirmation(R.string.affirmation2, R.drawable.img),
            Affirmation(R.string.affirmation3, R.drawable.img),
            Affirmation(R.string.affirmation4, R.drawable.img),
            Affirmation(R.string.affirmation5, R.drawable.img),
            Affirmation(R.string.affirmation6, R.drawable.img),
            Affirmation(R.string.affirmation7, R.drawable.img),
            Affirmation(R.string.affirmation8, R.drawable.img),
            Affirmation(R.string.affirmation9, R.drawable.img),
            Affirmation(R.string.affirmation10, R.drawable.img))
    }
}