package com.mzdev.security.board.repository.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.mzdev.security.board.model.BoardModel;
import com.mzdev.security.board.repository.BoardRepository;

public class BoardRepositoryImpl implements BoardRepository{

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<BoardModel> selectList() {
		// TODO Auto-generated method stub
		System.out.println("baordRepository.. selectList");
		List<BoardModel> list = this.sqlSession.selectList("BoardMapper.selectList");
		return list;
	}

	@Override
	public BoardModel selectOne(int idx) {
		// TODO Auto-generated method stub
		System.out.println("boardRepository.. selectOne idx:: "+idx);
		return this.sqlSession.selectOne("BoardMapper.selectOne", idx);
	}

	@Override
	public int selectIdx() {
		// TODO Auto-generated method stub
		System.out.println("boardRepository.. selectIdx");
		return this.sqlSession.selectOne("BoardMapper.selectidx");
	}

	@Override
	public void insert(BoardModel boardModel) {
		// TODO Auto-generated method stub
		System.out.println("boardRepository... insert");
		this.sqlSession.insert("BoardMapper.insert",boardModel);
	}

	@Override
	public void update(BoardModel boardModel, int idx) {
		// TODO Auto-generated method stub
		System.out.println("boardRepository.. update");
		this.sqlSession.update("BoardMapper.update",idx);
	}

	@Override
	public void delete(int idx) {
		// TODO Auto-generated method stub
		System.out.println("boardRepository.. delete");
		this.sqlSession.delete("BoardMapper.delete", idx);
	}

	@Override
	public void updateCnt(BoardModel boardModel, int idx) {
		// TODO Auto-generated method stub
		System.out.println("boardRepository... updateCnt");
		this.sqlSession.update("Boardmapper.updateCnt", idx);
	}

	
}
