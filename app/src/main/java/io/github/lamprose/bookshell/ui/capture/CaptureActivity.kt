package io.github.lamprose.bookshell.ui.capture

import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.utils.UIUtils.context
import kotlinx.android.synthetic.main.activity_capture.*

class CaptureActivity:AppCompatActivity() {

    private lateinit var capture:CaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture)
        capture = CaptureManager(this, dbv_custom as DecoratedBarcodeView)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.decode()
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        capture.onRequestPermissionsResult(requestCode,permissions,grantResults)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return (dbv_custom as DecoratedBarcodeView).onKeyDown(keyCode,event) || super.onKeyDown(keyCode, event)
    }


}