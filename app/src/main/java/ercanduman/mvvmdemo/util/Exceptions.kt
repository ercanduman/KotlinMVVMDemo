package ercanduman.mvvmdemo.util

import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoNetworkException(message: String) : IOException(message)
