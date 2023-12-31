package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import News.NewsDAO;
import News.NewsDTO;
import dao.MainNewsDao;
import dao.MainNoticeDao;
import dao.MainPhotoDao;
import dao.MaingoodsDao;
import dao.MatchInfoDao;
import dao.RankDao;
import dto.GoodsDto;
import dto.MainNewsDto;
import dto.MainNoticeDto;
import dto.MainPhotoDto;
import dto.MatchDBDto;
import dto.RankDto;

public class NewsDetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		NewsDAO newsdao = new NewsDAO();
		int pageNum = 0;
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
			if(pageNum==0){
				pageNum = 1;
			}
		} catch(NumberFormatException e) {
			pageNum = 1;
		}
		String search = "";
		try {
			search = (String)request.getParameter("searchValue");
		} catch(NumberFormatException e) {
			search = "";
		}
		if(search==null){
			search = "";
		}
		String searchType = "";
		try {
			searchType = (String)request.getParameter("searchType");
		} catch(NumberFormatException e) {
			searchType = "";
		}
		if(searchType==null){
			searchType = "";
		}
		boolean searchB = search.equals("")?false:true;
		String searchT = 
				searchB==true?
				searchType.equals("all")?" title like'%"+search+"%' OR content like '%"+search+"%'"
				:searchType.equals("title")?" title like'%"+search+"%'"
				:" content like'%"+search+"%'":"";
		ArrayList<NewsDTO> NDTO = null;
		int pageFull = 1;
		try {
			pageFull = newsdao.pageNation(searchB,searchT);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			pageFull = 1;
			e1.printStackTrace();
		}
		try {
			NDTO = newsdao.getBoardPage(pageNum, searchT, searchB);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int seq = Integer.parseInt(request.getParameter("seq"));
		NewsDTO nDTO = null;
		try {
			nDTO = newsdao.getBoardDto(seq);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainPhotoDao pdao= new MainPhotoDao();
		ArrayList <MainPhotoDto> pdto = pdao.getPhotoimg();
		MainNewsDao ndao = new MainNewsDao();
		ArrayList <MainNewsDto> ndto = ndao.getNews();
		MainNoticeDao ntdao = new MainNoticeDao();
		ArrayList <MainNoticeDto> ntdto = ntdao.getNotice();
		RankDao radao = new RankDao();
		ArrayList <RankDto> radto = radao.getFcRank();
		
		MatchInfoDao midao = new MatchInfoDao();
		ArrayList<MatchDBDto> prevdto = midao.prevMatch();
		ArrayList<MatchDBDto> nextdto = midao.nextMatch();
		ArrayList<MatchDBDto> nextnextdto = midao.nextnextMatch();
		
		MaingoodsDao mgdao = new MaingoodsDao();
		ArrayList<GoodsDto> goodsdto = mgdao.getbannergoods();
		ArrayList<GoodsDto> goodsdto2 = mgdao.getbannergoods2();
		ArrayList<GoodsDto> goodsdto3 = mgdao.getbannergoods3();
		
		request.setAttribute("pdto", pdto);
		request.setAttribute("ndto", ndto);
		request.setAttribute("ntdto", ntdto);
		request.setAttribute("radto", radto);
		request.setAttribute("prevdto", prevdto);
		request.setAttribute("nextdto", nextdto);
		request.setAttribute("nextnextdto", nextnextdto);
		request.setAttribute("goodsdto", goodsdto);
		request.setAttribute("goodsdto2", goodsdto2);
		request.setAttribute("goodsdto3", goodsdto3);
		request.setAttribute("selectNews", nDTO);
		request.setAttribute("newsList", NDTO);
		request.setAttribute("pageFull", pageFull);
		request.setAttribute("currPage", pageNum);
		request.getRequestDispatcher("NewsDetailV2.jsp").forward(request,  response);
	}

}
