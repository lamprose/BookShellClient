package io.github.lamprose.bookshell.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.BarUtils
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.ui.book.BookActivity
import io.github.lamprose.bookshell.ui.capture.CaptureActivity
import io.github.lamprose.bookshell.ui.main.home.HomeFragment
import io.github.lamprose.bookshell.ui.main.me.MeFragment
import io.github.lamprose.bookshell.ui.main.search.SearchFragment
import io.github.lamprose.bookshell.ui.main.shelf.ShelfFragment
import io.github.lamprose.bookshell.ui.share.ShareActivity
import io.github.lamprose.bookshell.widget.VagueDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user.*
import me.majiajie.pagerbottomtabstrip.NavigationController
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var navCtl: NavigationController

    private val fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BarUtils.setStatusBarColor(this, resources.getColor(R.color.colorPrimary))
        initView()
    }

    private fun initView() {
        fragments.add(HomeFragment.newInstance())
        fragments.add(ShelfFragment.newInstance())
        fragments.add(Fragment())
        fragments.add(SearchFragment.newInstance())
        fragments.add(MeFragment.newInstance())
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragments[0])
            .commitNow()

        navCtl = page_navigation_view.material()
            .addItem(R.drawable.tab_shop_selected, "首页")
            .addItem(R.drawable.tab_car_selected,"书架")
            .addItem(R.drawable.ic_tab_add,"",resources.getColor(R.color.gary))
            .addItem(R.drawable.ic_tab_search,"搜索")
            .addItem(R.drawable.tab_me_selected, "我的")
            .build()

        navCtl.addTabItemSelectedListener(object : OnTabItemSelectedListener {

            override fun onSelected(index: Int, old: Int) {
                switchPage(index,old)
            }

            override fun onRepeat(index: Int) {
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, ShareActivity::class.java)
            intent.putExtra("ISBN", result.contents)
            startActivity(intent)
        }
    }

    private fun switchPage(index: Int, old: Int) {
        if(index==2){
            VagueDialog(this, R.layout.dialog_home_publish)
                .setOnDismiss { navCtl.setSelect(old) }
                .setOnItemClickListener { _, p1, _, _ ->
                    when(p1!!.id){
                        R.id.tv_release_circle->Toast.makeText(this@MainActivity,"tv_release_circle",Toast.LENGTH_SHORT).show()
                        R.id.tv_release_active->{
                            Toast.makeText(this@MainActivity,"tv_release_active",Toast.LENGTH_SHORT).show()
                            IntentIntegrator(this)
                                .setCaptureActivity(CaptureActivity::class.java)
                                .setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
                                .setPrompt("请对准二维码")
                                .setCameraId(0)
                                .setBeepEnabled(true)
                                .setBarcodeImageEnabled(false)
                                .initiateScan()
                        }
                        R.id.tv_release_dynamic->Toast.makeText(this@MainActivity,"tv_release_dynamic",Toast.LENGTH_SHORT).show()
                    }
                }.show()
            return
        }
        val now = fragments[index]
        supportFragmentManager.beginTransaction().apply {
            if (!now.isAdded) {
                add(R.id.container, now)
            }
            show(now)
            hide(fragments[old])
            commit()
        }
    }
}
