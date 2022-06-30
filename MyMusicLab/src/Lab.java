package myMusicLab;

import java.util.ArrayList;

/**
 * myMusicLabのメインとなるクラス
 */

public class Lab {
	/**
	 * フィールドの定義
	 */
	/*
	 * 現在セッション中のユーザを示す
	 */
	private User user;
	/*
	 * DateManagementから受け取ったユーザのリストを格納する
	 */
	private ArrayList<User> userList;


	/**
	 * コンストラクタの定義
	 */
	public Lab() {
		userList = new ArrayList<User>();
		userList.addAll(DataManagement.loadUserData());
	}

	/**
	 * userListの更新
	 */
	public void updateData() {
		DataManagement.dumpUserData(userList);
		userList = new ArrayList<User>();
		userList.addAll(DataManagement.loadUserData());
	}

	/**
	 * フィールドuserのゲッター
	 */
	public User getUser() {
		return user;
	}

	/**
	 * フィールドuserListのゲッター
	 */
	public ArrayList<User> getUserList(){
		return userList;
	}

    public void setUserList(ArrayList<User> userList){
	this.userList = userList;
    }

	/**
	 * ユーザリストの中に, 引数で指定された名前が存在するかを確かめる.
	 * 存在する場合真, 存在しない場合偽を返す.
	 */
	public boolean checkNameExisting(String name) {
		boolean ret = false;
		for(User user: userList) {
			if(user.getName().equals(name)) {
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * ユーザリストの中の, 引数で指定された名前のオブジェクトを返す.
	 */
	public User getUserDataFromUserList(String name) {
		User ret = null;
		for(User user: userList) {
			if(user.getName().equals(name)) {
				ret = user;
			}
		}
		return ret;
	}

	/**
	 * 友達のオブジェクトを返すメソッド
	 */




	/**
	 * ユーザの新規登録についての処理を行う.
	 * 新規登録に成功した場合真, 失敗した場合偽を返す.
	 */
	public boolean register(String name, String password) {
		//戻り値の変数
		boolean ret;
		//与えられた引数の名前がすでにリストに存在するかを確かめる.
		boolean exist = checkNameExisting(name);
		//existの値が真である時, すでに登録されているので, 登録失敗
		if(exist) {
			/*
			 * 登録失敗の処理
			 */
			ret = false;
		} else {
			/*
			 * 登録成功の処理
			 */
			//新規ユーザの作成
			this.user = new User(name, Encryption.encryptStr(password));
			//ユーザリストに追加
			this.userList.add(this.user);
			ret = true;
		}
		return ret;
	}

	/**
	 * ユーザのログインについての処理を行う.
	 * ログインに成功した場合真, 失敗した場合偽を返す.
	 */
	public boolean login(String name, String password) {
		//戻り値の変数
		boolean ret = false;
		//与えられた引数の名前がすでにリストに存在するかを確かめる.
		boolean exist = checkNameExisting(name);
		//existの値が真である時, そのユーザが存在しないので, ログイン失敗
		if(!exist) {
			/*
			 * ログイン失敗の処理
			 */
			ret = false;
		} else {
			/*
			 * ログイン成功の処理
			 */
			//ユーザをリストから持ってくる

			for(User user : userList){
				if(getUserDataFromUserList(name).getPassword().equals(Encryption.encryptStr(password))){
					ret = true;
				}
			}

			this.user = getUserDataFromUserList(name);
		}
		return ret;
	}

	/**
	 * ユーザのログアウトについての処理を行う.
	 * データを保存し, 外部ファイルへ出力する.
	 */
	public void logout() {
		/*
		 * ファイル名を決める
		 */
		DataManagement.dumpUserData(userList);
	}
}
