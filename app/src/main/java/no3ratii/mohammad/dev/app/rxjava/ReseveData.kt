package no3ratii.mohammad.dev.app.rxjava

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

abstract class ReseveData {
    @SuppressLint("CheckResult")
    fun test() {
        val fetchWeatherInterval: Observable<ArrayList<Task>>? = Observable
                .interval(5, TimeUnit.SECONDS)
                .map {
                    val taskList: ArrayList<Task> = ArrayList()
                    taskList.add(Task("Take out the trash", true, 3))
                    taskList.add(Task("Walk the dog", false, 2))
                    taskList.add(Task("Make my bed", true, 1))
                    taskList.add(Task("Unload the dishwasher", false, 0))
                    taskList.add(Task("Make dinner", true, 5))
                    return@map taskList
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        fetchWeatherInterval?.subscribe {
            test(it)
        }
    }

    abstract fun test(tt: ArrayList<Task>)
}