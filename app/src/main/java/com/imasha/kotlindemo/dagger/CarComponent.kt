package com.imasha.kotlindemo.dagger

import com.imasha.kotlindemo.activity.MainActivity
import dagger.Component

@Component
interface CarComponent {
    fun getCar(): Car;

    fun inject(mainActivity: MainActivity);
}