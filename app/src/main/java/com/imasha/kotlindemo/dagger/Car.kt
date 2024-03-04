package com.imasha.kotlindemo.dagger

import android.util.Log
import javax.inject.Inject

class Car @Inject constructor(val engine: Engine, val wheel: Wheel) {

    companion object {
        private const val TAG = "Car"
    }

    fun drive() {
        Log.d(TAG, "driving...")
    }
}