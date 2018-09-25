package com.project.kabu.mvvmsample.view.ui

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.kabu.mvvmsample.R
import com.project.kabu.mvvmsample.databinding.FragmentRepoListBinding
import com.project.kabu.mvvmsample.service.model.Repo
import com.project.kabu.mvvmsample.view.adapter.RepoListAdapter
import com.project.kabu.mvvmsample.view.callback.ItemClickListener
import com.project.kabu.mvvmsample.viewModel.RepoListViewModel
import kotlinx.android.synthetic.main.activity_main.view.*

/**
 * リポジトリー一覧を表示するFragment
 * Viewの形成を行うため、DataBindingを紐付けする
 * データバインディングライブラリのDataBindingUtilにより、Databindingするビューファイルの設定をする
 * RecyclerViewのアダプターをセットしたり、loadの制御、また、ViewModelのLiveDataを監視する
 */
class RepoListFragment : Fragment()/**, ItemClickListener */ {

    companion object {
        val TAG = "RepoListFragment"
    }

    // データとレイアウトの紐付けは、
    // <data>付きのレイアウトファイルを作り、
    // 以下のように「レイアウトファイルの名前+Binding」でできるようになる
    lateinit var binding: FragmentRepoListBinding
    // アダプター
    lateinit var adapter: RepoListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // DataBinding用のレイアウトリソースをセット
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repo_list, container, false)

        // イベントのcallbackをadapterに伝達
        adapter = RepoListAdapter()

        // 上記adapterをrecyclerViewに適応
        binding.mainRecyclerView.adapter = adapter

        // layoutmanagerの設定
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        // ロード開始
        binding.setIsLoading(true)

        // rootviewを取得
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // RepoListViewModelクラス
        val viewModel = ViewModelProviders.of(this).get(RepoListViewModel::class.java)
        observeViewModel(viewModel)
    }

    /**
     * observe開始
     * observe関数で、LiveDataのアクティブ状態を監視する
     * データが更新されたらアップデートするように、LifecycleOwnerを紐付け、ライフサイクル内にオブザーバを追加する
     * オブザーバは、STARTED かRESUMED状態である場合にのみ、イベントを受信する
     */
    private fun observeViewModel(viewModel: RepoListViewModel) {
        //データが更新されたらアップデートするように、LifecycleOwnerを紐付け、ライフサイクル内にオブザーバを追加
        //オブザーバーは、STARTED かRESUMED状態である場合にのみ、イベントを受信する
        viewModel.repoListObservable.observe(this, Observer {
            repolist ->
            if (repolist != null) {
                binding.setIsLoading(false)
                adapter.setRepoList(repolist)
            }
        })
    }

//    /**
//     * リストタップの操作イベント設定
//     */
//    override fun onItemClick(repo: Repo) {
//        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
//            if (activity is MainActivity) {
//                (activity as MainActivity).show(repo)
//            }
//        }
//    }

//    val itemClickCallback: ItemClickListener = ItemClickListener {
//     override fun onItemClick(view: View, position: Int) {
//
//     }
//    }

}