package it.marcodallaba.beerbox.util

import android.view.View
import androidx.annotation.IdRes
import it.marcodallaba.beerbox.data.Beer

@OptIn(ExperimentalStdlibApi::class)
fun Beer.type(): BeerType {
    return BeerType.values().first {
        ebc in it.minEbc..<it.maxEbc
    }
}

enum class BeerType(val displayName: String, val minEbc: Float, val maxEbc: Float) {
    UNKNOWN("Unknown", Float.MIN_VALUE, 0.0f),
    PALE_LAGER("Pale Lager", 0.0f, 6.0f),
    BLONDE_ALE("Blonde Ale", 6.0f, 8.0f),
    WEISSBIER("Weissbier", 8.0f, 12.0f),
    PALE_ALE("Pale Ale", 12.0f, 16.0f),
    SAISON("Saison", 16.0f, 20.0f),
    EBS("EBS", 20.0f, 26.0f),
    DOUBLE_IPA("Double IPA", 26.0f, 33.0f),
    AMBER_ALE("Amber Ale", 33.0f, 39.0f),
    BROWN_ALE("Brown Ale", 39.0f, 47.0f),
    STOUT("Stout", 47.0f, 79.0f),
    IMPERIAL_STOUT("Imperial Stout", 79.0f, Float.MAX_VALUE);
}

enum class BeerTypeId(@IdRes val id: Int) {
    UNKNOWN(View.generateViewId()),
    PALE_LAGER(View.generateViewId()),
    BLONDE_ALE(View.generateViewId()),
    WEISSBIER(View.generateViewId()),
    PALE_ALE(View.generateViewId()),
    SAISON(View.generateViewId()),
    EBS(View.generateViewId()),
    DOUBLE_IPA(View.generateViewId()),
    AMBER_ALE(View.generateViewId()),
    BROWN_ALE(View.generateViewId()),
    STOUT(View.generateViewId()),
    IMPERIAL_STOUT(View.generateViewId())
}
