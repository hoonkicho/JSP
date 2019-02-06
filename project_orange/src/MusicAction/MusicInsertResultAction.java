package MusicAction;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.MusicDAO;
import vo.MusicVO;

/**
 * Servlet implementation class MusicInsertResultAction
 */
@WebServlet("/music/insert.korea")
public class MusicInsertResultAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String path = request.getServletContext().getRealPath("/musicfile/");
		
		int max_size = 1024*1024*100; 
		
		MultipartRequest mr = new MultipartRequest(
								request,path, max_size, "utf-8",
								new DefaultFileRenamePolicy()
								);
		String m_musicfile = "no_file";
		String m_image = "no_file";
		
		File f = mr.getFile("m_musicfile"); 
		if( f != null ) {
			m_musicfile = f.getName(); 
		}
		
		f = mr.getFile("m_image");
		if( f != null ) {
			m_image = f.getName();
		}
		String 	m_title 	  = mr.getParameter("m_title");
		String 	m_content = mr.getParameter("m_content");
		
		MusicVO vo = new MusicVO();
		vo.setM_title(m_title);
		vo.setM_content(m_content);
		vo.setM_musicfile(m_musicfile);
		vo.setM_image(m_image);
		vo.setM_goodpoint(0);
		
		if(request.getSession().getAttribute("idx")==null) {
			vo.setMemberidx(0);
			vo.setName("NO register");
		}
		else {
			vo.setMemberid((String)request.getSession().getAttribute("id")); 
			vo.setMemberidx((int)request.getSession().getAttribute("idx")); 
			vo.setName((String)request.getSession().getAttribute("name")); 
		}
		MusicDAO.getInstance().insert( vo );
		response.sendRedirect("list.korea");
	}
}
