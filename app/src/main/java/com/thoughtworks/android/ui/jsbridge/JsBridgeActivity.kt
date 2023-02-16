package com.thoughtworks.android.ui.jsbridge

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebChromeClient
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.github.lzyzsd.jsbridge.BridgeWebViewClient
import com.thoughtworks.android.R

/**
 * js桥的一个实践
 * [参考项目，对应的JS-react项目也在其中](https://github.com/beichensky/jsbridge-example)
 */
class JsBridgeActivity : AppCompatActivity(), View.OnClickListener {

    private var bridgeWebView: BridgeWebView? = null

    companion object {
        // URL 网络请求地址 (用局域网IP加对应项目的端口) eg: http://XXXXX:8000
        private const val ADDRESS = ""
        const val URL = "http://$ADDRESS:8000"

        private const val APP = "app"
        private const val RELOAD_URL = "reloadUrl"
        private const val ANDROID_FOR_JS_CONTENT = "安卓返回给 JS 的消息内容"
        private const val REFRESH_SUCCESSFUL = "刷新成功"
        private const val CHANGE_USER = "changeUser"
        private const val CHANGE_NAME = "changeName"
        private const val UPDATE_NAME_SUCCESSFUL = "name 修改成功"
        private const val COOKIE_SYNC_SUCCESSFUL = "Cookie 同步成功"
        private const val SYNC_COOKIE = "syncCookie"
        private const val AGAIN_BACK = "再按一次退出程序"
        private const val TAG = "JsBridgeActivity"
    }

    private var exitTime: Long = 0
    private var textView: TextView? = null
    private var editName: EditText? = null
    private var editCookie: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge)
        initWebView()
        registerHandlers()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        bridgeWebView!!.reload()
    }

    /**
     * 初始化 WebView
     */
    @Suppress("DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        bridgeWebView = findViewById(R.id.main_wv)
        bridgeWebView!!.run {
            settings.run {
                allowFileAccess = true
                setAppCacheEnabled(true)
                databaseEnabled = true
                // 开启 localStorage
                domStorageEnabled = true
                // 设置支持javascript
                javaScriptEnabled = true
                // 进行缩放
                builtInZoomControls = true
                // 设置UserAgent
                userAgentString = bridgeWebView!!.settings.userAgentString + APP
            }
            // 设置不用系统浏览器打开,直接显示在当前WebView
            webChromeClient = WebChromeClient()
            webViewClient = MyWebViewClient(bridgeWebView!!)
            loadUrl(URL)
        }
    }

    /**
     * 注册与 H5 交互的事件函数
     */
    private fun registerHandlers() {
        bridgeWebView!!.run {
            // 设置默认接收函数
            setDefaultHandler { data, function ->
                Toast.makeText(this@JsBridgeActivity, data, Toast.LENGTH_LONG).show()
                function.onCallBack(ANDROID_FOR_JS_CONTENT)
            }
            // 注册刷新页面的 reloadUrl 函数
            registerHandler(RELOAD_URL) { _, function ->
                reload()
                Toast.makeText(this@JsBridgeActivity, REFRESH_SUCCESSFUL, Toast.LENGTH_SHORT).show()
                function.onCallBack("")
            }
            // 注册修改 User 名称的 changeUser 函数
            registerHandler(CHANGE_USER) { user, function ->
                textView!!.text = user
                function.onCallBack("")
            }
        }
    }

    /**
     * 初始化其他 View 组件
     */
    private fun initViews() {
        listOf(
            R.id.btn_cookie,
            R.id.btn_name,
            R.id.btn_init
        ).forEach { findViewById<View>(it).setOnClickListener(this) }
        textView = findViewById(R.id.tv_user)
        editCookie = findViewById(R.id.edit_cookie)
        editName = findViewById(R.id.edit_name)
    }

    override fun onClick(view: View) {
        when (view.id) {
            // 调用 H5 界面的默认接收函数
            R.id.btn_init ->
                bridgeWebView!!.send(
                    ANDROID_FOR_JS_CONTENT
                ) { data -> Toast.makeText(this@JsBridgeActivity, data, Toast.LENGTH_LONG).show() }

            // 调用 H5 界面的 changeName 事件函数
            R.id.btn_name ->
                bridgeWebView!!.callHandler(CHANGE_NAME, editName!!.text.toString()) {
                    Toast.makeText(this@JsBridgeActivity, UPDATE_NAME_SUCCESSFUL, Toast.LENGTH_SHORT)
                        .show()
                    editName!!.setText("")
                }

            // 调用 H5 界面的 syncCookie 事件函数
            R.id.btn_cookie -> {
                syncCookie(this, "token=" + editCookie!!.text.toString())
                bridgeWebView!!.callHandler(SYNC_COOKIE, "") {
                    Toast.makeText(this@JsBridgeActivity, COOKIE_SYNC_SUCCESSFUL, Toast.LENGTH_SHORT)
                        .show()
                    editCookie!!.setText("")
                }
            }
        }
    }
    /**
     * 退出应用
     */
    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(
                applicationContext, AGAIN_BACK,
                Toast.LENGTH_SHORT
            ).show()
            exitTime = System.currentTimeMillis()
        } else {
            super.onBackPressed()
//            finish()
//            exitProcess(0)
        }
    }

    /**
     * 这只并同步 Cookie 的工具函数
     * @param context   上下文对象
     * @param cookie    需要设置的 cookie 值，例如："token=acked57chkstk"
     */
    @Suppress("DEPRECATION")
    private fun syncCookie(context: Context, cookie: String) {
        CookieSyncManager.createInstance(context)
        val cookieManager = CookieManager.getInstance()
        cookieManager.run {
            setAcceptCookie(true)
            removeSessionCookie() // 移除
            removeAllCookie()
            setCookie(URL, cookie)
        }
        val newCookie = cookieManager.getCookie(URL)
        Log.i(TAG, "newCookie == $newCookie")
        CookieSyncManager.getInstance().sync()
    }
}

class MyWebViewClient(webView: BridgeWebView) : BridgeWebViewClient(webView)