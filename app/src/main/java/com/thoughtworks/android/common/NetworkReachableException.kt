package com.thoughtworks.android.common

import java.io.IOException

class NetworkReachableException(msg: String? = "network is unreachable") : IOException(msg)