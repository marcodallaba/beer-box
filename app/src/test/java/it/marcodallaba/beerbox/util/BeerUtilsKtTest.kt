package it.marcodallaba.beerbox.util

import it.marcodallaba.beerbox.data.Beer
import junit.framework.TestCase.assertTrue
import org.junit.Test


internal class BeerUtilsKtTest {

    @Test
    fun paleLagerTest() {
        val paleLager1 = Beer(
            1, "pale_lager", "tagline", "description", "image_url", 0.0f
        )
        val paleLager2 = Beer(
            1, "pale_lager", "tagline", "description", "image_url", 5.9f
        )
        val paleLager3 = Beer(
            1, "pale_lager", "tagline", "description", "image_url", 3f
        )

        assertTrue(paleLager1.type() == BeerType.PALE_LAGER)
        assertTrue(paleLager2.type() == BeerType.PALE_LAGER)
        assertTrue(paleLager3.type() == BeerType.PALE_LAGER)
    }

    @Test
    fun blondeAleTest() {
        val blondeAle1 = Beer(
            1, "blonde_ale", "tagline", "description", "image_url", 6f
        )
        val blondeAle2 = Beer(
            1, "blonde_ale", "tagline", "description", "image_url", 7.9f
        )
        val blondeAle3 = Beer(
            1, "blonde_ale", "tagline", "description", "image_url", 7f
        )

        assertTrue(blondeAle1.type() == BeerType.BLONDE_ALE)
        assertTrue(blondeAle2.type() == BeerType.BLONDE_ALE)
        assertTrue(blondeAle3.type() == BeerType.BLONDE_ALE)
    }

    @Test
    fun weissbierTest() {
        val weissbier1 = Beer(
            1, "weissbier", "tagline", "description", "image_url", 8f
        )
        val weissbier2 = Beer(
            1, "weissbier", "tagline", "description", "image_url", 11.9f
        )
        val weissbier3 = Beer(
            1, "weissbier", "tagline", "description", "image_url", 10f
        )

        assertTrue(weissbier1.type() == BeerType.WEISSBIER)
        assertTrue(weissbier2.type() == BeerType.WEISSBIER)
        assertTrue(weissbier3.type() == BeerType.WEISSBIER)
    }

    @Test
    fun paleAleTest() {
        val paleAle1 = Beer(
            1, "paleAle", "tagline", "description", "image_url", 12f
        )
        val paleAle2 = Beer(
            1, "paleAle", "tagline", "description", "image_url", 15.9f
        )
        val paleAle3 = Beer(
            1, "paleAle", "tagline", "description", "image_url", 13f
        )

        assertTrue(paleAle1.type() == BeerType.PALE_ALE)
        assertTrue(paleAle2.type() == BeerType.PALE_ALE)
        assertTrue(paleAle3.type() == BeerType.PALE_ALE)
    }

    @Test
    fun saisonTest() {
        val saison1 = Beer(
            1, "saison", "tagline", "description", "image_url", 16f
        )
        val saison2 = Beer(
            1, "saison", "tagline", "description", "image_url", 19.9f
        )
        val saison3 = Beer(
            1, "saison", "tagline", "description", "image_url", 18f
        )

        assertTrue(saison1.type() == BeerType.SAISON)
        assertTrue(saison2.type() == BeerType.SAISON)
        assertTrue(saison3.type() == BeerType.SAISON)
    }

    @Test
    fun ebsTest() {
        val ebs1 = Beer(
            1, "ebs", "tagline", "description", "image_url", 20f
        )
        val ebs2 = Beer(
            1, "ebs", "tagline", "description", "image_url", 25.9f
        )
        val ebs3 = Beer(
            1, "ebs", "tagline", "description", "image_url", 22f
        )

        assertTrue(ebs1.type() == BeerType.EBS)
        assertTrue(ebs2.type() == BeerType.EBS)
        assertTrue(ebs3.type() == BeerType.EBS)
    }

    @Test
    fun doubleIpaTest() {
        val doubleIpa1 = Beer(
            1, "doubleIpa", "tagline", "description", "image_url", 26f
        )
        val doubleIpa2 = Beer(
            1, "doubleIpa", "tagline", "description", "image_url", 32.9f
        )
        val doubleIpa3 = Beer(
            1, "doubleIpa", "tagline", "description", "image_url", 30f
        )

        assertTrue(doubleIpa1.type() == BeerType.DOUBLE_IPA)
        assertTrue(doubleIpa2.type() == BeerType.DOUBLE_IPA)
        assertTrue(doubleIpa3.type() == BeerType.DOUBLE_IPA)
    }

    @Test
    fun amberAleTest() {
        val amberAle1 = Beer(
            1, "amberAle", "tagline", "description", "image_url", 33f
        )
        val amberAle2 = Beer(
            1, "amberAle", "tagline", "description", "image_url", 38.9f
        )
        val amberAle3 = Beer(
            1, "amberAle", "tagline", "description", "image_url", 35f
        )

        assertTrue(amberAle1.type() == BeerType.AMBER_ALE)
        assertTrue(amberAle2.type() == BeerType.AMBER_ALE)
        assertTrue(amberAle3.type() == BeerType.AMBER_ALE)
    }

    @Test
    fun brownAleTest() {
        val brownAle1 = Beer(
            1, "brownAle", "tagline", "description", "image_url", 39f
        )
        val brownAle2 = Beer(
            1, "brownAle", "tagline", "description", "image_url", 46.9f
        )
        val brownAle3 = Beer(
            1, "brownAle", "tagline", "description", "image_url", 44f
        )

        assertTrue(brownAle1.type() == BeerType.BROWN_ALE)
        assertTrue(brownAle2.type() == BeerType.BROWN_ALE)
        assertTrue(brownAle3.type() == BeerType.BROWN_ALE)
    }

    @Test
    fun stoutTest() {
        val stout1 = Beer(
            1, "stout", "tagline", "description", "image_url", 47f
        )
        val stout2 = Beer(
            1, "stout", "tagline", "description", "image_url", 78.9f
        )
        val stout3 = Beer(
            1, "stout", "tagline", "description", "image_url", 60f
        )

        assertTrue(stout1.type() == BeerType.STOUT)
        assertTrue(stout2.type() == BeerType.STOUT)
        assertTrue(stout3.type() == BeerType.STOUT)
    }

    @Test
    fun imperialStoutTest() {
        val imperialStout1 = Beer(
            1, "imperialStout", "tagline", "description", "image_url", 79f
        )
        val imperialStout2 = Beer(
            1, "imperialStout", "tagline", "description", "image_url", 200f
        )
        val imperialStout3 = Beer(
            1, "imperialStout", "tagline", "description", "image_url", 100f
        )

        assertTrue(imperialStout1.type() == BeerType.IMPERIAL_STOUT)
        assertTrue(imperialStout2.type() == BeerType.IMPERIAL_STOUT)
        assertTrue(imperialStout3.type() == BeerType.IMPERIAL_STOUT)
    }}