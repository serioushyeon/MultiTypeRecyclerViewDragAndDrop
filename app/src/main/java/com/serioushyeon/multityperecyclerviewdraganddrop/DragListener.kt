package com.serioushyeon.multityperecyclerviewdraganddrop

import android.util.Log
import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DragListener() : View.OnDragListener {

    private var isDropped = false //드래그된 항목이 드롭되었는지 여부

    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            // DragEvent.ACTION_DROP,
            // DragEvent.ACTION_DRAG_EXITED
            DragEvent.ACTION_DROP -> {
                Log.d("MyLog", "ACTION_DROP")
                isDropped = true
                var positionTarget: Int //드롭 대상 위치, 드래그 이벤트가 발생한 뷰의 위치를 나타내기 위해 사용

                val viewSource = event.localState as? View //내가 드래그 한 원본 뷰, 이벤트의 localState에서 가져옴
                val viewId = v.id //드롭 대상이 되는 뷰, 이벤트가 발생한 뷰의 ID를 가져옴

                val btnRVBreathItem = R.id.cl_breath_btn
                val btnRVPPTItem = R.id.cl_ppt_btn
                val sentenceClItem = R.id.cl_sentence
                val rvTop = R.id.topDivider
                val rvBottom = R.id.bottomDivider

                val btnBreathItem = R.id.btn_flow_controller_breath
                val btnPPTItem = R.id.btn_flow_controller_ppt

                Log.d("MyLog", "viewId $viewId")
                Log.d("MyLog", "viewSource $viewSource")
                if (viewSource != null) {
                    when (viewSource.id) {
                        btnBreathItem, btnPPTItem -> {
                            when (viewId) {
                                btnRVBreathItem, btnRVPPTItem, sentenceClItem, rvTop, rvBottom -> {
                                    Log.d("MyLog", "btnClItem, sentenceClItem, rvTop, rvBottom")
                                    val target: RecyclerView =
                                        v.parent as RecyclerView // 드롭 대상 뷰의 부모는 타겟, 리사이클러 뷰

                                    positionTarget = v.tag as Int //드롭 대상인 뷰의 위치 인덱스
                                    Log.d("MyLog", "positionTarget = $positionTarget")

                                    Log.d("MyLog", "viewSource $viewSource")

                                    val adapterTarget =
                                        target.adapter as RecyclerViewAdapter // 어댑터 가져옴
                                    Log.d("MyLog", "target $target")
                                    val customListTarget =
                                        adapterTarget.getList().toMutableList() //리스트 가져옴
                                    Log.d("MyLog", "customListTarget $customListTarget")
                                    if(viewSource.id == btnBreathItem) {
                                        if (positionTarget < 0) { //만약 드롭 할 위치가 0보다 작다면
                                            customListTarget.add(MultiTypeItem.BreathButtonItem) //드래그 한 아이템을 리스트의 마지막에 추가
                                        } else {
                                            customListTarget.add(
                                                positionTarget,
                                                MultiTypeItem.BreathButtonItem
                                            ) //드래그한 아이템을 알맞은 위치에 추가
                                        }
                                    }
                                    else {
                                        if (positionTarget < 0) { //만약 드롭 할 위치가 0보다 작다면
                                            customListTarget.add(MultiTypeItem.PPTButtonItem) //드래그 한 아이템을 리스트의 마지막에 추가
                                        } else {
                                            customListTarget.add(
                                                positionTarget,
                                                MultiTypeItem.PPTButtonItem
                                            ) //드래그한 아이템을 알맞은 위치에 추가
                                        }
                                    }
                                    adapterTarget?.updateList(customListTarget) //리스트 업데이트
                                    adapterTarget?.notifyDataSetChanged() // 어댑터에 알림
                                }
                            }
                        }
                        else -> {
                            when (viewId) { //드롭 대상 뷰 아이디
                                btnRVBreathItem, btnRVPPTItem, sentenceClItem, rvTop, rvBottom -> {
                                    Log.d("MyLog", "btnClItem, sentenceClItem, rvTop, rvBottom")
                                    val target: RecyclerView = v.parent as RecyclerView //타겟은 리사이클러 뷰

                                    positionTarget = v.tag as Int //드롭 대상인 뷰의 포지션
                                    Log.d("MyLog", "positionTarget = $positionTarget")

                                    Log.d("MyLog", "viewSource $viewSource")
                                    if (viewSource != null) {
                                        val source = viewSource.parent as RecyclerView //리사이클러뷰
                                        val adapterSource = source.adapter as RecyclerViewAdapter //어댑터
                                        val positionSource = viewSource.tag as Int //드래그한 아이템의 포지션
                                        Log.d("MyLog", "positionSource $positionSource")
                                        val list = adapterSource.getList()[positionSource] //드래그한 아이템 가져옴
                                        val listSource = adapterSource.getList().toMutableList() //전체 리스트

                                        listSource.removeAt(positionSource) //전체 리스트에서 드래그한 아이템의 포지션 삭제
                                        adapterSource.updateList(listSource) //전체 리스트 업데이트
                                        adapterSource.notifyDataSetChanged() //어댑터에 알림

                                        val adapterTarget = target.adapter as RecyclerViewAdapter //다시 어댑터 가져옴
                                        Log.d("MyLog", "target $target")
                                        val customListTarget = adapterTarget.getList().toMutableList() //다시 리스트 가져옴
                                        Log.d("MyLog", "customListTarget $customListTarget")
                                        if (positionTarget < 0) { //만약 드롭 할 위치가 0보다 작다면
                                            customListTarget.add(list) //드래그 한 아이템을 리스트의 마지막에 추가
                                        } else {
                                            customListTarget.add(positionTarget, list) //드래그한 아이템을 알맞은 위치에 추가
                                        }
                                        adapterTarget?.updateList(customListTarget) //리스트 업데이트
                                        adapterTarget?.notifyDataSetChanged() // 어댑터에 알림
                                    }
                                }
                            }
                        }
                    }
                }
            }

            DragEvent.ACTION_DRAG_EXITED -> {
                Log.d("MyLog", "Enter ACTION_DRAG_EXITED")
            }
        }

        if (!isDropped && event.localState != null) {
            (event.localState as? View)?.visibility = View.VISIBLE
        }
        return true
    }
}
