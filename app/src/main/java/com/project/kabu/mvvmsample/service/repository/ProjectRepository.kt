package com.project.kabu.mvvmsample.service.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.project.kabu.mvvmsample.service.model.Readme
import com.project.kabu.mvvmsample.service.model.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ViewModelに対するデータプロバイダ
 * レスポンスをLiveData Objectにラップする
 *
 * LiveDataとは
 * ライフサイクルに応じて自動的に監視状態を解除してくれるもの
 * 今までUI側は、データの変更を検知して自身を書き換えていた。
 * そのため、その検知する処理や検知をやめる処理を書かなければならなかった。
 * それらが、(AACのLifecycleに深く結びつきながら、)LiveDataにより自動でやってくれる
 */
class ProjectRepository {

    // Retrofitインターフェース
    lateinit var githubService: GithubService

    // コンスタラクタでRetrofitインスタンス生成
    init {
        githubService = Retrofit.Builder()
                            .baseUrl(GithubService.HTTPS_API_GITHUB_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(GithubService::class.java)
    }

    // リポジトリー情報の一覧
    // GithubのAPIにリクエストし、レスポンスをLiveDataで返す
    fun getRepoList(username: String): LiveData<ArrayList<Repo>> {
        lateinit var data: MutableLiveData<ArrayList<Repo>>
        
        // Retrofitで非同期リクエスト
        // さらに、Callbackで(自分で実装したModel)型ListのMutableLiveDataにセット
        githubService.getRepo(username).enqueue(object : Callback<ArrayList<Repo>> {
            override fun onResponse(call: Call<ArrayList<Repo>>?, response: Response<ArrayList<Repo>>?) {
                data.value = response?.body()
            }

            override fun onFailure(call: Call<ArrayList<Repo>>?, t: Throwable?) {
                data.value = null
            }

        })
        return data
    }

    // readme情報
    // GithubのAPIにリクエストし、レスポンスをLiveDataで返す
    fun getReadme(username: String, reponame: String): LiveData<Readme> {
        lateinit var data: MutableLiveData<Readme>

        githubService.getReadme(username, reponame).enqueue(object : Callback<Readme> {
            override fun onResponse(call: Call<Readme>?, response: Response<Readme>?) {
                data.value = response?.body()
            }

            override fun onFailure(call: Call<Readme>?, t: Throwable?) {
                data.value = null
            }
        })
        return data
    }


}
