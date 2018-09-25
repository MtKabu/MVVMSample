package com.project.kabu.mvvmsample.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.kabu.mvvmsample.R
import com.project.kabu.mvvmsample.databinding.ListItemBinding
import com.project.kabu.mvvmsample.service.model.Repo
import com.project.kabu.mvvmsample.view.callback.ItemClickListener
import com.project.kabu.mvvmsample.view.viewholder.RepoListViewHolder
import java.util.*
import kotlin.collections.ArrayList

class RepoListAdapter( /** private val itemClickListener: ItemClickListener*/ ) : RecyclerView.Adapter<RepoListViewHolder>() {

    // RecyclerViewのアダプター設定
    private var mRecyclerView : RecyclerView? = null
    // データ
    private var repolist: ArrayList<Repo>? = null


    /**
     * 現状との差分をListとしてRecyclerViewにセットする
     */
    fun setRepoList(repoList: ArrayList<Repo>) {
        if (this.repolist == null) {
            this.repolist = repoList

            // positionStartの位置からitemCountの範囲において、データの変更があったことを登録されているすべてのobserverに通知する。
            notifyItemRangeInserted(0, repoList.size)
        } else {
            // 2つのListの差分を計算するユーティリティー。Support Library 24.2.0で追加された
            val result = DiffUtil.calculateDiff(DiffCallback(this.repolist!!, repoList))

            this.repolist = repoList

            // DiffUtilのメソッド=>差分を元にRecyclerViewAderpterのnotify系が呼ばれ、いい感じにアニメーションなどをやってくれます。
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // RecyclerViewの設定
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        // RecyclerViewの破棄
        mRecyclerView = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        // レイアウトの設定
        val binding: ListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item, parent, false)

//        binding.setCallback(itemClickListener)

        return RepoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        // データをビューに設定
        holder.binding.repo = repolist!!.get(position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        // アダプタが保持するデータの合計数
        return repolist!!.size
    }

    /**
     * 2つのListの差分を計算する
     */
    private class DiffCallback(
            val old: ArrayList<Repo>,
            val new: ArrayList<Repo>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return old.size
        }

        override fun getNewListSize(): Int {
            return new.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old.get(oldItemPosition).id == new.get(newItemPosition).id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val repo = new.get(newItemPosition)
            val old = old.get(oldItemPosition)

            return repo.id == old.id && Objects.equals(repo.name, old.name)
        }
    }

}