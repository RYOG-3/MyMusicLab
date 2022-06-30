package myMusicLab;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.Writer;

import java.util.ArrayList;


@WebServlet("/myMusicLab/login")
public class LoginServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

    String name = (request.getParameter("name") == null) ? ""
				: (String) request.getParameter("name");
    String password = (request.getParameter("password") == null) ? ""
    		: (String) request.getParameter("password");

    boolean ret = false;

    ArrayList<User> str = (ArrayList)request.getSession().getAttribute("str");

    if(name != null && password != null){

      Lab lab = new Lab();
      ArrayList<User> userList;

      if(request.getSession().getAttribute("userList") == null){
        //ラボのリストを持ってくる
        userList = lab.getUserList();
        //セッションオブジェクトにuserListを追加
        request.getSession().setAttribute("userList",userList);
      } else {
        //ラボのuserListをセッションオブジェクトのuserListで更新
        ArrayList<User> sessionUserList = (ArrayList)request.getSession().getAttribute("userList");
        lab.setUserList(sessionUserList);
        //ラボのリストを持ってくる
        userList = lab.getUserList();
      }
      /*
       * 新規ユーザの作成
       */
      ret = lab.login(name, password);

      //ラボの現在セッション中のユーザを持ってくる.
      User currentUser = lab.getUser();
      //セッションオブジェクトにuserを追加
      request.getSession().setAttribute("currentUser",currentUser);

      		//for(User user: userList) {
      			/*
      			 * リスト上のユーザ名を表示
      			 */
            //デバグ用メッセージ
      			//System.out.println("ユーザ名: "+user.getName());
      		//}
    }


		StringBuilder builder = new StringBuilder();
		builder.append('{');
    if(ret){
      builder.append("\"result\":").append("true");
    } else {
      builder.append("\"result\":").append("false");
    }
		builder.append('}');
		String json = builder.toString();

		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}
}
