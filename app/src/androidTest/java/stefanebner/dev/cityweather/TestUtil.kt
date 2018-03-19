package stefanebner.dev.cityweather

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import stefanebner.dev.cityweather.model.City
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun createCity(id: Int) = City(id, "name$id", "c$id",0.0, 0.0)

fun <T> LiveData<T>.blockingObserve(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)
    val innerObserver = Observer<T> {
        value = it
        latch.countDown()
    }
    observeForever(innerObserver)
    latch.await(2, TimeUnit.SECONDS)
    return value
}