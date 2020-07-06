package com.zhen.base.https

import java.io.Serializable

class BaseBean<T> : Serializable {
    var result: T? = null
        private set
    var code = 0
    var codeMsg: String? = null

    fun setResult(result: T) {
        this.result = result
    }

}