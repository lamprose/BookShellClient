package io.github .lamprose.bookshell.ui.main.me

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.gson.Gson
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseFragment
import io.github.lamprose.bookshell.databinding.MeFragmentBinding
import io.github.lamprose.bookshell.network.entity.SettingItem
import io.github.lamprose.bookshell.network.entity.UserBean
import io.github.lamprose.bookshell.ui.login.LoginActivity
import io.github.lamprose.bookshell.ui.user.UserActivity
import io.github.lamprose.bookshell.utils.AppManager
import io.github.lamprose.bookshell.utils.DataCleanManager
import io.github.lamprose.bookshell.utils.PrefUtils
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.me_fragment.*

class MeFragment : BaseFragment<MeViewModel, MeFragmentBinding>() {

    private val mAdapter by lazy { MeWebAdapter() }

    companion object {
        fun newInstance() = MeFragment()
    }


    override fun onResume() {
        super.onResume()
        PrefUtils.getObjectJsonStr("user",UserBean::class.javaObjectType).let {
            if (it!=null){
                mBinding?.user= it as UserBean
            }else{
                mBinding?.user=null
            }
        }
    }

    override fun layoutId() = R.layout.me_fragment

    override fun initView(savedInstanceState: Bundle?) {
        Glide.with(h_head.context).asBitmap().load(R.drawable.head)
            .transform(CircleCrop()).into(h_head)
        Glide.with(h_back.context).asBitmap().load(R.drawable.head)
            .transform(BlurTransformation(50,1),CenterCrop())
            .into(h_back)

        PrefUtils.getObjectJsonStr("user",UserBean::class.javaObjectType).let {
            if (it!=null){
                mBinding?.user= it as UserBean
            }
        }

        h_head.setOnClickListener {
            if(!AppManager.isLogin())
                startActivity(Intent(this@MeFragment.context,LoginActivity::class.java))
            else{
                val intent = Intent(this@MeFragment.context, UserActivity::class.java)
                intent.putExtra("userName", PrefUtils.getString("userName"))
                startActivity(intent)
                Toast.makeText(this@MeFragment.context,"已登录",Toast.LENGTH_SHORT).show()
            }
        }



        viewModel.getPopularWeb(this@MeFragment.context)
        with(rv_me_uesd_web) {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        viewModel.popularWeb.observe(viewLifecycleOwner, Observer {
            mAdapter.setNewData(it)
        })
        mAdapter.setOnItemClickListener { _, _, position ->
            when((mAdapter.data[position] as SettingItem).id){
                1->{
                    DataCleanManager.clearAllCache(this@MeFragment.context!!)
                    viewModel.getPopularWeb(this@MeFragment.context)
                    Toast.makeText(this.context,"清理成功",Toast.LENGTH_SHORT).show()
                }
                2->{
                    Toast.makeText(this.context,"2",Toast.LENGTH_SHORT).show()
                }
            }
//            val intent = Intent().apply {
//                setClass(activity!!, DetailActivity::class.java)
//                putExtra("url", (mAdapter.data[position] as UsedWeb).link)
//            }
//            startActivity(intent)
        }
    }


    override fun lazyLoadData() {
//        viewModel.getPopularWeb()
    }
}
