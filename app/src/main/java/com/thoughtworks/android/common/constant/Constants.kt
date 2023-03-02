package com.thoughtworks.android.common.constant

import com.thoughtworks.android.data.model.Sender
import com.thoughtworks.android.data.model.Tweet

class Constants {

    companion object {
        const val SHARED_PREFERENCE_FILE = "preference"

        val TWEETS = listOf(
            Tweet(
                sender = Sender(
                    nick = "Cheng Yao",
                    avatar = "https://c-ssl.duitang.com/uploads/item/201710/17/20171017215814_fwPMQ.jpeg"
                ),
                content = "沙发！"
            ),
            Tweet(
                sender = Sender(
                    nick = "Wei Fan",
                    avatar = "https://c-ssl.duitang.com/uploads/item/201710/17/20171017215814_fwPMQ.jpeg"
                ),
                content = "Unlike many proprietary big data processing platform different, Spark is built on a unified abstract RDD, making it possible to deal with different ways consistent with large data processing scenarios, including MapReduce, Streaming, SQL, Machine Learning and Graph so on. To understand the Spark, you have to understand the RDD. "
            ),
            Tweet(
                sender = Sender(
                    nick = "Xin Ge",
                    avatar = "https://c-ssl.duitang.com/uploads/item/201710/17/20171017215814_fwPMQ.jpeg"
                ),
                content = "Take space"
            ),
            Tweet(
                sender = Sender(
                    nick = "Xin Guo",
                    avatar = "https://c-ssl.duitang.com/uploads/item/201710/17/20171017215814_fwPMQ.jpeg"
                ),
                content = "3"
            ),
            Tweet(
                sender = Sender(
                    nick = "Jianing Zheng",
                    avatar = "https://c-ssl.duitang.com/uploads/item/201710/17/20171017215814_fwPMQ.jpeg"
                ),
                content = "As a programmer, we should as far as possible away from the Windows system. However, the most a professional programmer, and very difficult to bypass Windows this wretched existence but in fact very real, then how to quickly build a simple set of available windows based test environment? See Qiu Juntao's blog."
            ),
            Tweet(
                sender = Sender(
                    nick = "Xin Guo",
                    avatar = "https://c-ssl.duitang.com/uploads/item/201710/17/20171017215814_fwPMQ.jpeg"
                ),
                content = "这是第二页?"
            ),
            Tweet(
                sender = Sender(
                    nick = "Jianing Zheng",
                    avatar = "https://c-ssl.duitang.com/uploads/item/201710/17/20171017215814_fwPMQ.jpeg"
                ),
                content = "As a programmer, we should as far as possible away from the Windows system. However, the most a professional programmer, and very difficult to bypass Windows this wretched existence but in fact very real, then how to quickly build a simple set of available windows based test environment? See Qiu Juntao's blog."
            ),
            Tweet(
                sender = Sender(
                    nick = "Yanzi Li",
                    avatar = "https://c-ssl.duitang.com/uploads/item/201710/17/20171017215814_fwPMQ.jpeg"
                ),
                content = "楼下保持队形"
            ),
            Tweet(
                sender = Sender(
                    nick = "Heng Zeng",
                    avatar = "https://c-ssl.duitang.com/uploads/item/201710/17/20171017215814_fwPMQ.jpeg"
                ),
                content = "The data json will be hosted in http://thoughtworks-ios.herokuapp.com/- Nibs or storyboards are prohibited- Implement cache mechanism by you own if needed- Unit tests is appreciated.- Functional programming is appreciated- Deployment Target should be 7.0- Utilise Git for source control, for git log is the directly evidence of the development process- Utilise GCD for multi-thread operation- Only binary, framework or cocopods dependency is allowed. do not copy other developer's source code(*.h, *.m) into your project- Keep your code clean as much as possible"
            )
        )
    }

}