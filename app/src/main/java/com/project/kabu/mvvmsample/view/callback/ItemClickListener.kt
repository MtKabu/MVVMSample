package com.project.kabu.mvvmsample.view.callback

import android.view.View
import com.project.kabu.mvvmsample.service.model.Repo

/**
 * リストタップ処理用のインターフェース
 */
interface ItemClickListener {
    fun onItemClick(repo: Repo)
}