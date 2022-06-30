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


@WebServlet("/myMusicLab/preview")
public class PreviewMusicServlet extends HttpServlet {

  protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
        String music = (request.getParameter("music") == null) ? ""
        		: (String) request.getParameter("music");
            //デバグ用メッセージ
            //System.out.println(music);

        boolean ret = false;
        if(music != null){

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
           *  友達の追加
           */

          ret = currentUser.removeMusic(music);
          //フレンドデータを更新するためにラボの更新
          lab.updateData();
          userList = lab.getUserList();

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
        //デバグ用メッセージ
        //System.out.print(json);

    		response.setContentType("application/json");
    		PrintWriter writer = response.getWriter();
    		writer.append(json);
    		writer.flush();
      }


  protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

    String music_select = (request.getParameter("music_select") == null) ? ""
    		: (String) request.getParameter("music_select");
    String interest_select = (request.getParameter("interest_select") == null) ? ""
    		: (String) request.getParameter("interest_select");
    String name = (request.getParameter("name") == null) ? ""
    		: (String) request.getParameter("name");

    boolean ret = true;

    Lab lab = new Lab();
    ArrayList<User> userList = null;
    User currentUser = null;
    ArrayList<Music> loadedMusicList = new ArrayList<Music>();
    ArrayList<Music> rtnMusicList = new ArrayList<Music>();

    if(music_select != null && interest_select != null){

        //ラボのuserListをセッションオブジェクトのuserListで更新
        ArrayList<User> sessionUserList = (ArrayList)request.getSession().getAttribute("userList");
        lab.setUserList(sessionUserList);

        //ラボのリストを持ってくる
        userList = lab.getUserList();

        //ラボの現在セッション中のユーザを持ってくる.
        currentUser = (User)request.getSession().getAttribute("currentUser");


      /*
       *  曲の閲覧
       */
       if(name.equals("")){
         loadedMusicList = currentUser.getMusicList();
       } else {
         String nName = name.substring(6);
         loadedMusicList = lab.getUserDataFromUserList(nName).getMusicList();
          }
          /*
           * ジャンル・興味度分けの処理
           */
           for(Music music: loadedMusicList){
             //デバグ用メッセージ
             //System.out.println(music_select);
             if((music.getGenre().equals(music_select)||music_select.equals("all")) && (music.getInterest().equals(interest_select)||interest_select.equals("all"))){
                rtnMusicList.add(music);
              }
           }
    }


		StringBuilder builder = new StringBuilder();

    builder.append('[').append("\n");

    for(int i = 0; i < rtnMusicList.size();i++){
      builder.append('{').append("\n");
      builder.append("\"music\":\"").append(rtnMusicList.get(i).getName()).append("\",").append("\n");
      builder.append("\"artist\":\"").append(rtnMusicList.get(i).getArtist()).append("\",").append("\n");
      builder.append("\"music_select\":\"").append(rtnMusicList.get(i).getGenre()).append("\",").append("\n");
      builder.append("\"interest_select\":\"").append(rtnMusicList.get(i).getInterest()).append("\"").append("\n");
      builder.append('}');
      if(i != rtnMusicList.size()-1){
        builder.append(',').append("\n");
      } else {
	    	builder.append("\n");
	      }
    }
    builder.append(']');

		String json = builder.toString();
    //デバグ用メッセージ
    //System.out.print(json);

		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}
}
