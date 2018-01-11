package com.gxut.simple

import com.gxut.baseutil.base.BaseToolBarActivity

class IndexActivity : BaseToolBarActivity() {
    override fun initLayout(): Int {
        return R.layout.activity_index
    }

    override fun init() {
        showProgress(false)
    }


}
