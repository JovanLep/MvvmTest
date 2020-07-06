package com.zhen.base.https

object Constant {
    const val ERROR_MSG_CONNECT = "网络连接失败，请检查网络后重试！";
    const val ERROR_MSG_TIMEOUT = "网络连接超时，请检查网络后重试！";
    const val ERROR_MSG_BUSINESS = "数据异常，请稍后重试！";    // 其他异常
    const val BUSINESS_ERROR = -610
    //请求成功, 正确的操作方式
    const val CODE_200 = 200

    //参数错误
    const val CODE_108 = 108;

    //未注册code10134
    const val CODE_10134 = 10134;

    //请求失败，打印Message
    const val CODE_330 = 330;

    //服务器内部异常
    const val CODE_500 = 500;

    //参数为空
    const val CODE_503 = 503;

    //没有数据
    const val CODE_502 = 502;

    //无效的Token
    const val CODE_510 = 510;

    //未登录
    const val CODE_530 = 530;

    //请求的操作异常终止：未知的页面类型
    const val CODE_551 = 551;
}