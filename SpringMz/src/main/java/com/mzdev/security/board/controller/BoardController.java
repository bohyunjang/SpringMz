package com.mzdev.security.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mzdev.security.board.model.BoardModel;
import com.mzdev.security.board.service.BoardService;


@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/board",method=RequestMethod.GET)
	public String BoardList(Model model, HttpServletRequest request, HttpServletResponse response){
		System.out.println("move List controller");
		
		List<BoardModel> list = this.boardService.selectList();
		model.addAttribute("list", list);
		
		return "board_list";
	}
	
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String BoardSearch(Model model, HttpServletRequest request, HttpServletResponse response){
		System.out.println("Search controller");
		
		// search 부분 입력
		String searchType = request.getParameter("searchType");
		String searchValue= request.getParameter("searchValue");
		
		/*BoardSearchModel searchModel = new BoardSearchModel();
		
		searchModel.setSearchType(searchType);
		searchModel.setSearchValue(searchValue);
		
		System.out.println("Search Excute...");
		
		// 결과 값을 List로 출력함
		List<BoardModel> searchList = this.boardService.search(searchModel);
		model.addAttribute("list", searchList);*/
		
		return "board_list";
	}
	
	@RequestMapping(value="/{idx}")
	public String BoardView(@PathVariable int idx, Model model){
		
		System.out.println("boardView Controller..");
		
		BoardModel boardModel = this.boardService.selectOne(idx);
		
		//뷰페이지에 출력되는 cnt값은 증가하지 않기 때문에 setCnt 를 통해 증가시켜준다.
		boardModel.setCnt(boardModel.getCnt()+1);
		// DB에 저장되는 cnt 값을 +1 시켜준다.
		boardService.updateCnt(boardModel, idx);
		
		// jsp 화면에서 뿌려질때 어떤 이름의 객체로 뿌려줄것인지 설정
		// model.addAttribute("객체명", db에서 받아온 객체);
		model.addAttribute("board", boardModel);
		
		return "board_view";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String BoardDelete(@RequestParam(value="idx") int idx){
		
		System.out.println("BoardController... Board Delete..");
		this.boardService.delete(idx);
		
		System.out.println("게시판"+idx+" 삭제완료..");
		return "redirect:/";
	}
	
	@RequestMapping(value="/write",method=RequestMethod.POST)
	public String BoardWrite(BoardModel boardModel){
		System.out.println("boardController.... Board Write..");

		boardModel.setIdx(boardService.selectIdx()+1);
		System.out.println("getboardModel:: "+boardModel.getIdx());
		this.boardService.insert(boardModel);
		System.out.println("입력된 게시판 제목:: "+boardModel.getTitle());
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String BoardUpdate(BoardModel boardModel, @PathVariable int idx){
		
		System.out.println("boardCotnroller.... board update..");
		
		System.out.println("선택된 idx:: "+idx);
		
		this.boardService.update(boardModel, idx);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/write_move",method=RequestMethod.GET)
	public String moveBoardWrite(@RequestParam(value="idx", defaultValue="0") int idx){
		System.out.println("boardController.... move Write..");
		
		return "board_write";
	}
	
	@RequestMapping(value="/update_action",method=RequestMethod.GET)
	public String moveBoardUpdate(Model model,@RequestParam(value="idx", defaultValue="0")int idx
			){
		
		System.out.println("boardController... move update...");
		
		BoardModel boardModel = this.boardService.selectOne(idx);
		model.addAttribute("board", boardModel);
		
		return "board_update";
	}
	
	
}