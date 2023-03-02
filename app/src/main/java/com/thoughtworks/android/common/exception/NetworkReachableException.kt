package com.thoughtworks.android.common.exception

import java.io.IOException

class NetworkReachableException(msg: String? = "network is unreachable") : IOException(msg)