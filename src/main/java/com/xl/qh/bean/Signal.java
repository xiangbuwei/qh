package com.xl.qh.bean;

import com.xl.qh.enums.HandleEnum;
import com.xl.qh.enums.IndexEnum;
import com.xl.qh.enums.RankEnum;

public class Signal {
	public HandleEnum handle;
	public IndexEnum index;
	public RankEnum rank;
	public int score;
	
	public Signal(){
		
	}
	
	public Signal(HandleEnum handle, IndexEnum index, RankEnum rank, int score) {
		super();
		this.handle = handle;
		this.index = index;
		this.rank = rank;
		this.score = score;
	}

	public HandleEnum getHandle() {
		return handle;
	}

	public void setHandle(HandleEnum handle) {
		this.handle = handle;
	}

	public IndexEnum getIndex() {
		return index;
	}

	public void setIndex(IndexEnum index) {
		this.index = index;
	}

	public RankEnum getRank() {
		return rank;
	}

	public void setRank(RankEnum rank) {
		this.rank = rank;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Signal [handle=" + handle + ", index=" + index + ", rank=" + rank + ", score=" + score + "]";
	}
	
}

