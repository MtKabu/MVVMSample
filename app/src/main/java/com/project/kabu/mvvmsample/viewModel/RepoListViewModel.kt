package com.project.kabu.mvvmsample.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.project.kabu.mvvmsample.service.model.Repo
import com.project.kabu.mvvmsample.service.repository.ProjectRepository

/**
 * リポジトリー一覧用のViewModel
 * リサイクラービューに対する操作とUIイベントに責任を持つ
 * Viewをいじることもないし、データを扱うこともない。データの受け渡しのみする
 */
class RepoListViewModel(application: Application) : AndroidViewModel(application) {
    // 監視対象のLiveData
    lateinit var repoListObservable: LiveData<ArrayList<Repo>>

    init {
        // ProjectRepositoryからインスタンスを取得し、getProjectListを呼び出し、LiveDataオブジェクトに。
        //変換が必要な場合、これをTransformationsクラスで単純に行うことができる。
        val projectRepository = ProjectRepository()
        repoListObservable = projectRepository.getRepoList("Airbnb")
    }
}