package io.github.lamprose.bookshell.ui.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseActivity
import io.github.lamprose.bookshell.databinding.ActivityLoginBinding
import io.github.lamprose.bookshell.event.Message
import io.github.lamprose.bookshell.utils.UIUtils.context
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: BaseActivity<LoginViewModel, ActivityLoginBinding>() {


    override fun layoutId() = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.result.observe(this, Observer {
            Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show()
            finish()
        })
        login.setOnClickListener {
            viewModel.login(username.text.toString(),password.text.toString())
        }
    }

    override fun handleEvent(msg: Message) {
        if (msg.code==1){
            AlertDialog.Builder(context)
                .setTitle("是否直接注册并登录")
                .setPositiveButton("确定",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Toast.makeText(context,"hahahha",Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }

    override fun initData() {

    }

}