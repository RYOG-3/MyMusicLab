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


@WebServlet("/myMusicLab/music")
public class AddMusicServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {



    String music = (request.getParameter("music") == null) ? ""
				: (String) request.getParameter("music");
    String artist = (request.getParameter("artist") == null) ? ""
    		: (String) request.getParameter("artist");
    String music_select = (request.getParameter("music_select") == null) ? ""
    		: (String) request.getParameter("music_select");
    String interest_select = (request.getParameter("interest_select") == null) ? ""
    		: (String) request.getParameter("interest_select");

    boolean ret = true;

    if(music != null && artist != null && music_select != null && interest_select != null){

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

      currentUser.addMusic(new Music(music,artist,music_select,interest_select));


        //デバグ用メッセージ
      		for(int i = 0; i < currentUser.getMusicList().size();i++) {
      			/*
      			 * リスト上の音楽を表示
      			 */
             System.out.println("リスト内の"+i+"曲目の情報を表示します.");

      			System.out.println("曲名: "+currentUser.getMusicList().get(i).getName());
            System.out.println("曲名: "+currentUser.getMusicList().get(i).getArtist());
            System.out.println("曲名: "+currentUser.getMusicList().get(i).getGenre());
            System.out.println("曲名: "+currentUser.getMusicList().get(i).getInterest());
      		}


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
