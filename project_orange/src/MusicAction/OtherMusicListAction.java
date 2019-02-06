package MusicAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MusicDAO;
import util.Paging;
import vo.MusicVO;

/**
 * Servlet implementation class OtherMusicListAction
 */
@WebServlet("/music/other_list.korea")
public class OtherMusicListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
	
		
		String page = request.getParameter("page"); 
		int currentPage = 1; 
		if( page != null && page.equals("") == false ) {
			currentPage = Integer.parseInt(page);
		}
		int pageSize = 9;
		int totalSize = MusicDAO.getInstance().myselectCount(Integer.parseInt(request.getParameter("idx")) );
		Paging paging = new Paging( pageSize, totalSize, currentPage );
		
		HashMap<String, Integer> map = new HashMap<>();
		map.put("startNo", paging.getStartNo());
		map.put("endNo", paging.getEndNo());
		map.put("memberidx",   Integer.parseInt(request.getParameter("idx")) );
		List<MusicVO> list = MusicDAO.getInstance().myselect(map);
		request.setAttribute("paging", paging);
		
		request.setAttribute("idx", Integer.parseInt(request.getParameter("idx")) );
		request.setAttribute("url", "../music/other_list.korea");
		request.setAttribute("list", list);
		
		RequestDispatcher disp = request.getRequestDispatcher("music_list.jsp");
		disp.forward(request, response);
	}
}
