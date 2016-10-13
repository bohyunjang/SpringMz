package com.mzdev.security.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mzdev.security.board.model.BoardModel;
import com.mzdev.security.board.repository.BoardRepository;
import com.mzdev.security.board.service.BoardService;

public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public List<BoardModel> selectList() {
		// TODO Auto-generated method stub
		System.out.println("boardService... selectList");
		return this.boardRepository.selectList();
	}

	@Override
	public BoardModel selectOne(int idx) {
		// TODO Auto-generated method stub
		System.out.println("boardService... selectOne. idx:: "+idx);
		return this.boardRepository.selectOne(idx);
	}

	@Override
	public int selectIdx() {
		// TODO Auto-generated method stub
		System.out.println("boardService...selectIdx");
		return this.boardRepository.selectIdx();
	}

	@Override
	public void insert(BoardModel boardModel) {
		// TODO Auto-generated method stub
		System.out.println("boardService...getTitle:: "+boardModel.getTitle()+"getNickName:: "+boardModel.getNickName());
		this.boardRepository.insert(boardModel);
	}

	@Override
	public void update(BoardModel boardModel, int idx) {
		// TODO Auto-generated method stub
		System.out.println("boardService... boardUpdate. title:: "+boardModel.getTitle());
		this.boardRepository.update(boardModel, idx);
	}

	@Override
	public void delete(int idx) {
		// TODO Auto-generated method stub
		System.out.println("boardService... idx :: "+idx);
		this.boardRepository.delete(idx);
	}

	@Override
	public void updateCnt(BoardModel boardModel, int idx) {
		// TODO Auto-generated method stub
		System.out.println("boardService... updateCnt idx::"+idx);
		this.boardRepository.updateCnt(boardModel, idx);
	}
}
