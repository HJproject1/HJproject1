package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MainNoticeDao;
import dao.MainPhotoDao;
import dao.MaingoodsDao;
import dao.MatchInfoDao;
import dao.MainNewsDao;
import dao.PhotoDAO;
import dao.RankDao;
import dto.GoodsDto;
import dto.MainNoticeDto;
import dto.MainPhotoDto;
import dto.MatchDBDto;
import dto.MainNewsDto;
import dto.PhotoDTO;
import dto.RankDto;

public class PhotoMainAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int pageNum = 0;
		try{
			pageNum = Integer.parseInt(request.getParameter("page"));
			if(pageNum == 0){
				pageNum = 1;
			}
		} catch(NumberFormatException e){
			pageNum = 1;
		}
		String category ="";
		String searchValue = "";
		String searchType = "";
		try{
			category = request.getParameter("category");
			searchValue = request.getParameter("searchValue");
			searchType = request.getParameter("searchType");
			if(category == null){
				category = "all";
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		int endNum = pageNum * 15;
		int startNum = endNum - 14;

		PhotoDAO photoDao = new PhotoDAO();
		
		ArrayList<PhotoDTO> photoList = photoDao.photoSelect(category, searchValue, startNum, endNum);
		PhotoDTO photoCount = photoDao.photoCount(category, searchValue);
		int count = photoCount.getCount();
		
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
		request.setAttribute("photoList", photoList);
		request.setAttribute("count", count);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("category", category);
		request.setAttribute("searchValue", searchValue);
		
		RequestDispatcher rd = request.getRequestDispatcher("Photo.jsp");
		rd.forward(request, response);
	}

}
