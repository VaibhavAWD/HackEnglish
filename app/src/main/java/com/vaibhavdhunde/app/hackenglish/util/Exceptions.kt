package com.vaibhavdhunde.app.hackenglish.util

import java.io.IOException

class ApiException(message: String): IOException(message)
class NetworkException(message: String): IOException(message)