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


@WebServlet("/myMusicLab/myPage")
public class MyPageServlet extends HttpServlet {
  protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {


      Lab lab = new Lab();
      ArrayList<User> userList;

        //ラボのuserListをセッションオブジェクトのuserListで更新
        ArrayList<User> sessionUserList = (ArrayList)request.getSession().getAttribute("userList");
        lab.setUserList(sessionUserList);

        //ラボのリストを持ってくる
        userList = lab.getUserList();

        //ラボの現在セッション中のユーザを持ってくる.
        User currentUser = (User)request.getSession().getAttribute("currentUser");

      /*
       *  曲の追加
       */

		StringBuilder builder = new StringBuilder();

    builder.append('{').append("\n");
    builder.append("\"username\":\"").append(currentUser.getName()).append("\",").append("\n");
    builder.append("\"music_count\":\"").append(currentUser.getMusicList().size()).append("\",").append("\n");
    builder.append("\"friend_count\":\"").append(currentUser.getFriendList().size()).append("\"").append("\n");
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
