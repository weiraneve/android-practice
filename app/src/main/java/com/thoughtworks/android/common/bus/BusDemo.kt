package com.thoughtworks.android.common.bus

import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * need LifecycleOwner
 */

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