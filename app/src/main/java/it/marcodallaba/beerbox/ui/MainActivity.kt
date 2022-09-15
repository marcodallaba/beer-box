/*
 * Copyright 2022 Marco Dalla Ba'
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.marcodallaba.beerbox.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import it.marcodallaba.beerbox.R
import it.marcodallaba.beerbox.databinding.ActivityMainBinding
import it.marcodallaba.beerbox.ui.beers.BeersFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.toolbarTitle.text = SpannableStringBuilder().append(getString(R.string.beer_name))
            .append(" ")
            .append(
                getString(R.string.box_name),
                StyleSpan(Typeface.BOLD),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BeersFragment.newInstance())
                .commit()
    }
}