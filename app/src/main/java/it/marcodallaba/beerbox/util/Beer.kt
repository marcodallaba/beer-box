package it.marcodallaba.beerbox.util

import android.view.View
import androidx.annotation.IdRes
import it.marcodallaba.beerbox.data.Beer

fun Beer.type(): BeerType {
    return when (ebc) {
        in 0.0f..5.9f -> BeerType.PALE_LAGER
        in 6.0f..7.9f -> BeerType.BLONDE_ALE
        in 8.0f..11.9f -> BeerType.WEISSBIER
        in 12.0f..15.9f -> BeerType.PALE_ALE
        in 16.0f..19.9f -> BeerType.SAISON
        in 20.0f..25.9f -> BeerType.EBS
        in 26.0f..32.9f -> BeerType.DOUBLE_IPA
        in 33.0f..38.9f -> BeerType.AMBER_ALE
        in 39.0f..46.9f -> BeerType.BROWN_ALE
        in 47.0f..78.9f -> BeerType.STOUT
        in 79f..Float.MAX_VALUE -> BeerType.IMPERIAL_STOUT
        else -> BeerType.UNKNOWN
    }
}

enum class BeerType(val displayName: String, val ebcGt: Float?, val ebcLt: Float?) {
    UNKNOWN("Unknown", null, null),
    PALE_LAGER("Pale Lager", null, 6.0f),
    BLONDE_ALE("Blonde Ale", 5.9f, 8f),
    WEISSBIER("Weissbier", 7.9f, 12f),
    PALE_ALE("Pale Ale", 11.9f, 16f),
    SAISON("Saison", 15.9f, 20f),
    EBS("EBS", 19.9f, 26f),
    DOUBLE_IPA("Double IPA", 25.9f, 33f),
    AMBER_ALE("Amber Ale", 32.9f, 39f),
    BROWN_ALE("Brown Ale", 38.9f, 47f),
    STOUT("Stout", 46.9f, 79f),
    IMPERIAL_STOUT("Imperial Stout", 79.9f, null);
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
