package io.github.lamprose.bookshell.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.utils.UIUtils
import kotlinx.android.synthetic.main.item_setting.view.*


class SettingItem:LinearLayout {


    /**
     * 整个一行被点击
     */
    interface OnRootClickListener {
        fun onRootClick(view: View?)
    }


    /**
     * 右边箭头的点击事件
     */
    interface OnArrowClickListener {
        fun onArrowClick(view: View?)
    }

    constructor(mContext: Context?):super(mContext,null){
        init(mContext)
    }

    constructor(mContext: Context?,attrs: AttributeSet?):super(mContext,attrs){
        init(mContext,attrs)
    }

    constructor(mContext: Context?,attrs: AttributeSet?,defStyleAttr:Int):super(mContext,attrs,defStyleAttr){
        init(mContext,attrs)
    }

    /**
     * 初始化各个控件
     */
    private fun init(mContext: Context?) {
        LayoutInflater.from(context).inflate(R.layout.item_setting, this, true)
        setTextContent("默认")
        setRightText("")
        showLeftIcon(false)
    }

    /**
     * 初始化各个控件
     */
    private fun init(mContext: Context?,attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.item_setting, this, true)
        val typeArray:TypedArray=mContext!!.obtainStyledAttributes(attrs,R.styleable.SettingItem)
        setTextContent(typeArray.getString(R.styleable.SettingItem_textContent))
        showArrow(typeArray.getBoolean(R.styleable.SettingItem_isShowArrowImage,false))
        showLeftIcon(typeArray.getBoolean(R.styleable.SettingItem_isShowLeftIcon,false))
        setLeftIcon(typeArray.getResourceId(R.styleable.SettingItem_leftIcon,R.mipmap.homepage_right_arrow))
        setRightText(typeArray.getString(R.styleable.SettingItem_rightContent))
        typeArray.recycle()
    }

    //---------------------下面是对每个部分的一些设置     上面是应用中常用场景封装-----------------------//

    //---------------------下面是对每个部分的一些设置     上面是应用中常用场景封装-----------------------//
    /**
     * 设置root的paddingTop 与 PaddingBottom 从而控制整体的行高
     * paddingLeft 与 paddingRight 保持默认 20dp
     */
    fun setRootPaddingTopBottom(paddintTop: Int, paddintBottom: Int): SettingItem? {
        ll_root.setPadding(
            UIUtils.dp2px(context, 20),
            UIUtils.dp2px(context, paddintTop),
            UIUtils.dp2px(context, 20),
            UIUtils.dp2px(context, paddintBottom)
        )
        return this
    }

    /**
     * 设置root的paddingLeft 与 PaddingRight 从而控制整体的行高
     *
     *
     * paddingTop 与 paddingBottom 保持默认 15dp
     */
    fun setRootPaddingLeftRight(paddintTop: Int, paddintRight: Int): SettingItem? {
        ll_root.setPadding(
            UIUtils.dp2px(context, paddintTop),
            UIUtils.dp2px(context, 15),
            UIUtils.dp2px(context, paddintRight),
            UIUtils.dp2px(context, 15)
        )
        return this
    }

    /**
     * 设置上下分割线的显示情况
     *
     * @return
     */
    fun showDivider(
        showDividerTop: Boolean,
        showDivderBottom: Boolean
    ): SettingItem? {
        if (showDividerTop) {
            divider_top.visibility = View.VISIBLE
        } else {
            divider_top.visibility = View.GONE
        }
        if (showDivderBottom) {
            divider_bottom.visibility = View.VISIBLE
        } else {
            divider_bottom.visibility = View.GONE
        }
        return this
    }

    /**
     * 设置上分割线的颜色
     *
     * @return
     */
    fun setDividerTopColor(dividerTopColorRes: Int): SettingItem? {
        divider_top.setBackgroundColor(resources.getColor(dividerTopColorRes))
        return this
    }

    /**
     * 设置上分割线的高度
     *
     * @return
     */
    fun setDividerTopHigiht(dividerTopHigihtDp: Int): SettingItem? {
        val layoutParams = divider_top.layoutParams
        layoutParams.height = UIUtils.dp2px(context, dividerTopHigihtDp)
        divider_top.layoutParams = layoutParams
        return this
    }

    /**
     * 设置下分割线的颜色
     *
     * @return
     */
    fun setDividerBottomColor(dividerBottomColorRes: Int): SettingItem? {
        divider_bottom.setBackgroundColor(resources.getColor(dividerBottomColorRes))
        return this
    }

    /**
     * 设置上分割线的高度
     *
     * @return
     */
    fun setDividerBottomHigiht(dividerBottomHigihtDp: Int): SettingItem? {
        val layoutParams = divider_bottom.layoutParams
        layoutParams.height = UIUtils.dp2px(context, dividerBottomHigihtDp)
        divider_bottom.layoutParams = layoutParams
        return this
    }

    /**
     * 设置左边Icon
     *
     * @param iconRes
     */
    fun setLeftIcon(iconRes: Int): SettingItem? {
        iv_left_icon.setImageResource(iconRes)
        return this
    }

    /**
     * 设置左边Icon显示与否
     *
     * @param showLeftIcon
     */
    fun showLeftIcon(showLeftIcon: Boolean): SettingItem? {
        if (showLeftIcon) {
            iv_left_icon.setVisibility(View.VISIBLE)
        } else {
            iv_left_icon.setVisibility(View.GONE)
        }
        return this
    }

    /**
     * 设置右边Icon 以及Icon的宽高
     */
    fun setLeftIconSize(widthDp: Int, heightDp: Int): SettingItem? {
        val layoutParams: ViewGroup.LayoutParams = iv_left_icon.getLayoutParams()
        layoutParams.width = UIUtils.dp2px(context, widthDp)
        layoutParams.height = UIUtils.dp2px(context, heightDp)
        iv_left_icon.setLayoutParams(layoutParams)
        return this
    }


    /**
     * 设置中间的文字内容
     *
     * @param textContent
     * @return
     */
    fun setTextContent(textContent: String?): SettingItem? {
        tv_text_content.text = textContent
        return this
    }

    /**
     * 设置中间的文字颜色
     *
     * @return
     */
    fun setTextContentColor(colorRes: Int): SettingItem? {
        tv_text_content.setTextColor(resources.getColor(colorRes))
        return this
    }

    /**
     * 设置中间的文字大小
     *
     * @return
     */
    fun setTextContentSize(textSizeSp: Int): SettingItem? {
        tv_text_content.textSize = textSizeSp.toFloat()
        return this
    }

    /**
     * 设置右边文字内容
     *
     * @return
     */
    fun setRightText(rightText: String?): SettingItem? {
        tv_right_text.text = rightText
        return this
    }


    /**
     * 设置右边文字颜色
     *
     * @return
     */
    fun setRightTextColor(colorRes: Int): SettingItem? {
        tv_right_text.setTextColor(resources.getColor(colorRes))
        return this
    }

    /**
     * 设置右边文字大小
     *
     * @return
     */
    fun setRightTextSize(textSize: Int): SettingItem? {
        tv_right_text.textSize = textSize.toFloat()
        return this
    }

    /**
     * 设置右箭头的显示与不显示
     *
     * @param showArrow
     */
    fun showArrow(showArrow: Boolean): SettingItem? {
        if (showArrow) {
            iv_right_icon.setVisibility(View.VISIBLE)
        } else {
            iv_right_icon.setVisibility(View.GONE)
        }
        return this
    }

    /**
     * 获取右边icon
     */
    fun setIvRightIcon(iconRes: Int): SettingItem? {
        iv_right_icon.setImageResource(iconRes)
        return this
    }

    /**
     * 获取右边icon
     */
    fun getIvRightIcon(): ImageView? {
        return iv_right_icon
    }


    /**
     * 设置中间的输入框显示与否
     *
     * @param showEdit
     * @return
     */
//    fun showEdit(showEdit: Boolean): SettingItem? {
//        if (showEdit) {
//            edit_content.visibility = View.VISIBLE
//        } else {
//            edit_content.visibility = View.GONE
//        }
//        return this
//    }


    /**
     * 设置中间的输入框 是否可输入
     *
     * @param editable
     * @return
     */
    fun setEditable(editable: Boolean): SettingItem? {
//        edit_content.isFocusable = editable
        return this
    }


    /**
     * 设置中间的输入框hint内容
     *
     * @param showEditHint
     * @return
     */
//    fun setEditHint(showEditHint: String?): SettingItem? {
//        edit_content.hint = showEditHint
//        return this
//    }

    /**
     * 设置中间的输入框内容
     *
     * @param s
     * @return
     */
//    fun setEditContent(s: String?): SettingItem? {
//        edit_content.setText(s)
//        return this
//    }

    /**
     * 获取Edit输入的内容
     *
     * @param s
     * @return
     */
//    fun getEditContent(s: String?): String? {
//        return edit_content.text.toString()
//    }


    /**
     * 设置 edit 颜色
     *
     * @param colorRes
     * @return
     */
//    fun setEditColor(colorRes: Int): SettingItem? {
//        edit_content.setTextColor(resources.getColor(colorRes))
//        return this
//    }

    /**
     * 设置 edit 字体大小
     *
     * @param editSizeSp
     * @return
     */
//    fun setEditSize(editSizeSp: Int): SettingItem? {
//        edit_content.textSize = editSizeSp.toFloat()
//        return this
//    }
    //----------------------下面是一些点击事件

    //----------------------下面是一些点击事件
    fun setOnRootClickListener(
        onRootClickListener: OnRootClickListener,
        tag: Int
    ): SettingItem? {
        ll_root.setOnClickListener {
            ll_root.tag = tag
            onRootClickListener.onRootClick(ll_root)
        }
        return this
    }

    fun setOnArrowClickListener(
        onArrowClickListener: OnArrowClickListener,
        tag: Int
    ): SettingItem? {
        iv_right_icon.setOnClickListener(OnClickListener {
            iv_right_icon.setTag(tag)
            onArrowClickListener.onArrowClick(iv_right_icon)
        })
        return this
    }
}