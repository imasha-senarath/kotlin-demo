package com.imasha.kotlindemo.unit_test

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import com.imasha.kotlindemo.R
import org.junit.Before

class ResourceComparerTest {

    private lateinit var resourceComparer: ResourceComparer;

    @Before
    fun setup() {
        resourceComparer = ResourceComparer();
    }

    @Test
    fun stringResourceSameAsGivenString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>();
        val result = resourceComparer.isEqual(context, R.string.app_name, "Kotlin Demo");
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDiffAsGivenString_returnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>();
        val result = resourceComparer.isEqual(context, R.string.app_name, "Nothing");
        assertThat(result).isFalse()
    }
}