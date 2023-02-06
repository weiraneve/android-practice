package com.thoughtworks.android.network.exception

import java.io.IOException

class NetworkReachableException(msg: String? = "network is unreachable") : IOException(msg)