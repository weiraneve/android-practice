package com.thoughtworks.android.common.bus

class EventMessage {

    /**
     * 消息的key
     */
    var key: Int

    /**
     * 消息的主体message
     */
    var message: Any? = null
    private var messageMap: HashMap<String, Any?>? = null

    constructor(key: Int, message: Any?) {
        this.key = key
        this.message = message
    }

    constructor(key: Int) {
        this.key = key
    }

    fun put(key: String, message: Any?) {
        if (messageMap == null) {
            messageMap = HashMap()
        }
        messageMap?.set(key, message)
    }

    operator fun <T> get(key: String?): T? {
        if (messageMap != null) {
            try {
                return messageMap!![key] as T?
            } catch (e: ClassCastException) {
                e.printStackTrace()
            }
        }
        return null
    }
}