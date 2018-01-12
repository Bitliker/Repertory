package com.gxut.simple

import android.content.Intent
import com.gxut.baseutil.base.BaseToolBarActivity
import com.gxut.simple.ui.activity.CommonUiActivity

class IndexActivity : BaseToolBarActivity() {
    override fun initLayout(): Int {
        return R.layout.activity_index
    }

    override fun init() {
        startActivity(Intent(ct, CommonUiActivity::class.java))
        finish()
    }

    override fun needNavigation(): Boolean {
        return false
    }
}
