package com.project.kabu.mvvmsample.view.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.project.kabu.mvvmsample.R
import com.project.kabu.mvvmsample.service.model.Readme

/**
 * MVVM設計
 * アプリの「プレゼンテーション層(ユーザーが見て触れられる層)とドメイン(ビジネスロジック)を分離」する設計
 * Model:データソースを操作する領域
 * ↓
 * ViewModel:DataBindingライブラリを通し、Viewに表示するデータの監視、取得をする領域
 * ↓
 * View:データの表示領域（xml、Activity、Fragment）
 * 上記のように、依存関係が単方向になる
 * ViewModelはModelの変更を監視し、データをViewにバインドし、View操作を伝達するクラス。
 * Viewとライフサイクルを共に歩むので、画面回転などユーザの想定外の操作に強くなる。
 *
 * 依存関係のない方向に向かって実装すると、わかりやすいかも（Model→ViewModel→View）
 *
 * !!まだ途中
 *
 * 参考：https://qiita.com/Tsutou/items/69a28ebbd69b69e51703
 *
 */

/**
 * MainActivityでは、Fragmentの生成、画面遷移にしか責任を持ちません。
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // Fragmentを生成
            val fragment = RepoListFragment();

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, RepoListFragment.TAG)
                    .commit();
        }
    }

    fun show(readme: Readme) {
        val readmeFragment = ReadmeFragment()

        supportFragmentManager
                .beginTransaction()
                .addToBackStack("readme")
                .replace(R.id.fragment_container, readmeFragment, null)
                .commit();
    }
}
