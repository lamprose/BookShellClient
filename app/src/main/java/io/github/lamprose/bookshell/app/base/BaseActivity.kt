package io.github.lamprose.bookshell.app.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner

import io.github.lamprose.bookshell.event.Message
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.ui.login.LoginActivity
import io.github.lamprose.bookshell.utils.AppManager
import io.github.lamprose.bookshell.utils.StatusBarUtil
import io.github.lamprose.bookshell.utils.UIUtils.context
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var viewModel: VM

    protected var mBinding: DB? = null

    private var dialog: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewDataBinding()
        lifecycle.addObserver(viewModel)
        //注册 UI事件
        registorDefUIChange()
        initView(savedInstanceState)
        initData()
    }

    abstract fun layoutId(): Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()


    /**
     * DataBinding
     */
    private fun initViewDataBinding() {
        val cls =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.setContentView(this, layoutId())
            mBinding?.lifecycleOwner = this
        } else setContentView(layoutId())
        createViewModel()
    }


    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })
        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })
        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtils.showShort(it)
        })
        viewModel.defUI.msgEvent.observe(this, Observer {
            handleEvent(it)
        })
    }

    open fun handleEvent(msg: Message) {}

    /**
     * 打开等待框
     */
    private fun showLoading() {
        if (dialog == null) {
            dialog = MaterialDialog(this)
                .cancelable(false)
                .cornerRadius(8f)
                .customView(R.layout.custom_progress_dialog_view, noVerticalPadding = true)
                .lifecycleOwner(this)
                .maxWidth(R.dimen.dialog_width)
        }
        dialog?.show()

    }

    /**
     * 关闭等待框
     */
    private fun dismissLoading() {
        dialog?.run { if (isShowing) dismiss() }
    }


    /**
     * 创建 ViewModel
     */
    @Suppress("UNCHECKED_CAST")
    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            viewModel = ViewModelProvider(this,
                ViewModelFactory()
            ).get(tClass) as VM
        }
    }

    /**
     * 界面跳转
     * @param needLogin 启动界面是否需要登录
     */
    protected fun intent(clazz:Class<*>,needLogin:Boolean){
        //需要登录&&未登录
        if (needLogin && !AppManager.isLogin()) {
            startActivity(Intent(this, LoginActivity::class.java))
        }else{
            startActivity(Intent(this,clazz))
        }
    }
    /**
     * 携带bundle跳转
     * @param needLogin 启动界面是否需要登录
     */
    protected fun intent(bundle: Bundle,clazz:Class<*>,needLogin:Boolean){
        //需要登录&&未登录
        if (needLogin && !AppManager.isLogin()) {
            startActivity(Intent(this, LoginActivity::class.java))
        }else{
            startActivity(Intent(this, clazz).apply {
                putExtras(bundle)
            })
        }
    }

}