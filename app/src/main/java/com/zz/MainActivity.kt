package com.zz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zz.http.HttpUtils
import org.xutils.http.HttpMethod

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bodyMap = HashMap<String,String>()
        bodyMap.put("username","admin")
        bodyMap.put("password","123")
        HttpUtils.request(HttpMethod.POST,"GetUserInfo","GetUserInfo",bodyMap)

       /* Thread(Runnable {
            kotlin.run {
                var result = HttpUtils.loginByPost("admin","123")
                runOnUiThread(Runnable {
                    kotlin.run {
                        Toast.makeText(this,result,Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }).start()*/



    }
}
