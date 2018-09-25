package com.project.kabu.mvvmsample.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.project.kabu.mvvmsample.service.model.Readme
import com.project.kabu.mvvmsample.service.repository.ProjectRepository

class ReadmeViewModel(application: Application, reponame: String) : AndroidViewModel(application) {
    // 監視対象のLiveData
    lateinit var readmeObservable: LiveData<Readme>

    init {
        // ProjectRepositoryからインスタンスを取得し、getProjectListを呼び出し、LiveDataオブジェクトに。
        //変換が必要な場合、これをTransformationsクラスで単純に行うことができる。
        val projectRepository = ProjectRepository()
        readmeObservable = projectRepository.getReadme("Airbnb", reponame)
    }
}