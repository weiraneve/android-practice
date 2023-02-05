package com.thoughtworks.android.network.entity

import java.io.IOException

class NetworkReachableException(msg: String? = "network is unreachable") : IOException(msg)