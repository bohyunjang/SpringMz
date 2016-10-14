package com.mzdev.security.board.repository;

import java.util.List;

import com.mzdev.security.board.model.BoardModel;


public interface BoardRepository {
	
	List<BoardModel> selectList();

	BoardModel selectOne(int idx);

	int selectIdx();

	void insert(BoardModel boardModel);

	void update(BoardModel boardModel, int idx);

	void delete(int idx);
	
	void updateCnt(BoardModel boardModel, int idx);
	
	//게시판 검색 추가.
	//List<BoardModel> search(BoardSearchModel boardSearchModel);

}
