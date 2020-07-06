package com.zhen.base.https

class ApiException(var resultCode: Int, override var message:String) : RuntimeException() {


}