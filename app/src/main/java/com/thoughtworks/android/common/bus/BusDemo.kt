package com.thoughtworks.android.common.bus

import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * LifecycleOwner usecase demo
 * need LifecycleOwner
 */
class FlowBusDemo {
    //private fun demo() {
    //    observeEvent {
    //        Log.d(
    //            "TweetsActivity FlowBus.register3",
    //            "FlowBus.register: ${it.get<String>("key1")}"
    //        )
    //    }
    //    lifecycleScope.launch(Dispatchers.IO) {
    //        delay(1000)
    //        postValue(EventMessage(100))
    //        postValue(EventMessage(101), 1000)
    //        postValue(EventMessage(102, "bbb"), dispatcher = Dispatchers.IO)
    //        val event3 = EventMessage(103, "ccc")
    //        event3.put("key1", 123)
    //        event3.put("key2", "abc")
    //        postValue(event3, 2000, dispatcher = Dispatchers.Main)
    //    }
    //}
}

/**
 * need application init
 */
class Application {

    //    private val flowBusInitializer = FlowBusInitializer

    // 避免内存泄露
    //    override fun onCreate() {
    //        super.onCreate()
    //        flowBusInitializer.init(this)
    //    }

}

/**
 * SharedFlowBus usecase demo
 */
class SharedFlowBusDemo {
    // 发送消息
    //SharedFlowBus.with(objectKey: Class<T>).tryEmit(value: T)

    // 发送粘性消息
    //SharedFlowBus.withSticky(objectKey: Class<T>).tryEmit(value: T)

    // 订阅消息
    //SharedFlowBus.on(objectKey: Class<T>).observe(owner){ it ->
    //    println(it)
    //}

    // 订阅粘性消息
    //SharedFlowBus.onSticky(objectKey: Class<T>).observe(owner){ it ->
    //    println(it)
    //}
}
