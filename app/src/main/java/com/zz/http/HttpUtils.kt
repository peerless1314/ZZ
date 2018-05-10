package com.zz.http


import android.util.Log

import org.xutils.common.Callback
import org.xutils.common.Callback.CommonCallback
import org.xutils.http.HttpMethod
import org.xutils.http.RequestParams
import org.xutils.x
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.*


/**
 * 网络工具类
 * Created by 测试 on 2018/5/10.
 */
class HttpUtils {
    companion object {

        private const val url : String = "http://192.168.0.127:8080/PuLoginServer/LoginServlet"


        fun loginByPost(username: String, password: String): String? {
            try {
                val url = URL(url)
                val conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = 5000
                conn.requestMethod = "POST"
                val data = ("username=" + URLEncoder.encode(username) + "&password="
                        + URLEncoder.encode(password))
                println(data)
                conn.setRequestProperty("Content=Type", "application/x-wwww-form-urlencoded")
                conn.setRequestProperty("Content-length", data.length.toString() + "")
                conn.doOutput = true
                val os = conn.outputStream
                os.write(data.toByteArray())

                val message = ByteArrayOutputStream()
                val code = conn.responseCode
                println(code)
                if (code == 200) {
                    val `is` = conn.inputStream
                    var len : Int
                    var  buffer = ByteArray(1024)
                    do {
                        len = `is`.read(buffer)
                        if (len == -1){
                            break
                        }
                        message.write(buffer, 0, len)
                    }while (true)

                    `is`.close()
                    message.close()
                    return message.toByteArray().toString()
                } else {
                    return null
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: ProtocolException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * 向服务器发出访问请求,返回json
         * 如果为空，则 回调的optimaResponse也为空
         * @return
         */
        fun request(httpMethod: HttpMethod, methodName: String, requestId: String,
                    bodyParameterMap: Map<String, String>?): Callback.Cancelable {
            val params = RequestParams(url)
           // params.addHeader("Accept", "application/json")
            params.connectTimeout = 10*1000

            if (bodyParameterMap != null) {
                for (key in bodyParameterMap.keys) {
                    var value = bodyParameterMap[key]
                    params.addBodyParameter(key, value)
                }
            }

            val commonCallback = object : CommonCallback<String> {
                override fun onCancelled(cancelledException: Callback.CancelledException) {
                    Log.i("pxf","onCancelled")
                }
                override fun onError(throwable: Throwable, isOnCancelled: Boolean) {
                    Log.i("pxf","onError")
                }

                override fun onFinished() {
                    Log.i("pxf","onFinished")
                }
                override fun onSuccess(jsonObject: String) {
                    Log.i("pxf","onSuccess" + " == " + jsonObject)
                }
            }
            return x.http().request(httpMethod, params, commonCallback)
        }
    }

}