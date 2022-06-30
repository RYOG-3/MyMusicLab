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


@WebServlet("/myMusicLab/friend")
public class FriendServlet extends HttpServlet {

  protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
        String friend = (request.getParameter("friend") == null) ? ""
        		: (String) request.getParameter("friend");


        boolean ret = false;



        if(friend != null){

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

          ret = currentUser.removeFriend(friend);
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

    		response.setContentType("application/json");
    		PrintWriter writer = response.getWriter();
    		writer.append(json);
    		writer.flush();
      }


  protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

    String friend = (request.getParameter("friend") == null) ? ""
    		: (String) request.getParameter("friend");


    boolean ret = false;

    if(friend != null){

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

      ret = currentUser.addFriend(friend, userList);
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

		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}

  protected void doGet(HttpServletRequest request,
  			HttpServletResponse response) throws ServletException, IOException{
          Lab lab = new Lab();
          ArrayList<User> userList = null;
          User currentUser = null;

          //ラボのuserListをセッションオブジェクトのuserListで更新
          ArrayList<User> sessionUserList = (ArrayList)request.getSession().getAttribute("userList");
          lab.setUserList(sessionUserList);

          //System.out.println(sessionUserList.get(7).getName());

          //ラボのリストを持ってくる
          userList = lab.getUserList();

          //ラボの現在セッション中のユーザを持ってくる.
          currentUser = (User)request.getSession().getAttribute("currentUser");

          StringBuilder builder = new StringBuilder();

          builder.append('[').append("\n");

          for(int i = 0; i < currentUser.getFriendList().size();i++){
            builder.append('{').append("\n");
            builder.append("\"friend\":\"").append(currentUser.getFriendList().get(i).getName()).append("\"").append("\n");
            builder.append('}');
            if(i != currentUser.getFriendList().size()-1){
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
