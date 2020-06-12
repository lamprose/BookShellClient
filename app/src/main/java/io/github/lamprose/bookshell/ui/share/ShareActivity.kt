package io.github.lamprose.bookshell.ui.share

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseActivity
import io.github.lamprose.bookshell.databinding.ActivityShareBinding
import io.github.lamprose.bookshell.network.entity.BookBean
import io.github.lamprose.bookshell.ui.capture.CaptureActivity
import kotlinx.android.synthetic.main.activity_share.*

class ShareActivity:BaseActivity<ShareViewModel,ActivityShareBinding>() {

    var isbn:String?=null

    override fun layoutId()=R.layout.activity_share

    override fun initView(savedInstanceState: Bundle?) {
        share_post.setOnClickListener {
            viewModel.postBook( mBinding?.bookBean!!)
        }
        share_bookshelf_scan.setOnClickListener {
            IntentIntegrator(this)
                .setCaptureActivity(CaptureActivity::class.java)
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                .setPrompt("请对准二维码")
                .setCameraId(0)
                .setBeepEnabled(true)
                .setBarcodeImageEnabled(false)
                .initiateScan()
        }
        viewModel.postResult.observe(this, Observer {
            if(it){
                Toast.makeText(this,"发布成功",Toast.LENGTH_SHORT).show()
                finish()
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            mBinding?.bookBean= BookBean(isbn = mBinding?.bookBean?.isbn,bookShelfId = result.contents.toInt())
        }
    }

    override fun initData() {
        mBinding?.bookBean= BookBean()
        intent.getStringExtra("ISBN").let {
            if(it!=null){
                isbn=it
                mBinding?.bookBean?.isbn=it
            }
        }
    }

}