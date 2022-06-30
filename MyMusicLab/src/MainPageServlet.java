package myMusicLab;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.lang.Object;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.Writer;

import java.util.ArrayList;


@WebServlet("/myMusicLab/main")
public class MainPageServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

        Lab lab = new Lab();
        ArrayList<User> userList = null;
        User currentUser = null;

        //ラボのuserListをセッションオブジェクトのuserListで更新
        ArrayList<User> sessionUserList = (ArrayList)request.getSession().getAttribute("userList");
        lab.setUserList(sessionUserList);

        //ラボのリストを持ってくる
        userList = lab.getUserList();

        //ラボの現在セッション中のユーザを持ってくる.
        currentUser = (User)request.getSession().getAttribute("currentUser");

      /*
       *  ログアウトする, データの保存も行なっている.
       */

      lab.logout();
      //デバグ用メッセージ
      //System.out.println("データを保存しました");
	}

  protected void doGet(HttpServletRequest request,
  			HttpServletResponse response) throws ServletException, IOException{
          Lab lab = new Lab();
          ArrayList<User> userList = null;
          User currentUser = null;

          //ラボのuserListをセッションオブジェクトのuserListで更新
          ArrayList<User> sessionUserList = (ArrayList)request.getSession().getAttribute("userList");
          lab.setUserList(sessionUserList);

          //ラボのリストを持ってくる
          userList = lab.getUserList();

          //ラボの現在セッション中のユーザを持ってくる.
          currentUser = (User)request.getSession().getAttribute("currentUser");

          StringBuilder builder = new StringBuilder();

          builder.append('{');
          builder.append("\"username\":\"").append(currentUser.getName()).append("\"");
      		builder.append('}');

      		String json = builder.toString();

          //デバグ用メッセージ
          //System.out.println(json);

      		response.setContentType("application/json");
      		PrintWriter writer = response.getWriter();
      		writer.append(json);
      		writer.flush();
        }


}
