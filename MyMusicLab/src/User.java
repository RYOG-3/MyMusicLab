package myMusicLab;

import java.util.ArrayList;

/**
 * ユーザーの情報を管理するクラス
 */

public class User {
	/**
	 * フィールドの定義
	 */
	private String name;
	private String password;
	private ArrayList<Music> musicList;
	private ArrayList<User> friendList;

	/**
	 * コンストラクタの定義
	 */
	public User(String name, String password, ArrayList<Music> musicList, ArrayList<User> friendList) {
		this.name = name;
		this.password = password;
		this.musicList = musicList;
		this.friendList = friendList;
	}


	/**
	 * 新規登録のユーザ
	 */
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.musicList = new ArrayList<Music>();
		this.friendList = new ArrayList<User>();
	}


	/**
	 * 友達の際のユーザー
	 */
	public User(String name, ArrayList<Music> friendMusicList) {
		this.name = name;
		this.musicList = friendMusicList;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<Music> getMusicList() {
		return musicList;
	}

	public ArrayList<User> getFriendList() {
		return friendList;
	}

	/**
	 * 音楽リストに音楽を追加
	 */
	public void addMusic(Music music) {
		musicList.add(music);
	}

	public boolean removeMusic(String name) {

		boolean ret = false;

		//User friendData = null;
		for(int i = 0; i < musicList.size(); i++) {
			if(musicList.get(i).getName().equals(name)) {
				musicList.remove(i);
				ret = true;
			}
		}
		return ret;
	}



	/**
	 * 友達リストに友達を追加
	 */
	public boolean addFriend(String name, ArrayList<User> userList) {

		boolean ret = false;
		boolean judge1 = true;
		boolean judge2 = false;
		/*
		 * 自分の名前の時false
		 */
		 if(this.name.equals(name)){
			 return ret;
		 }


		/*
		 * 友達リストの友達と名前がかぶっていないかチェック
		 */
		//User friendData = null;
		for(User friend: friendList) {
			if(friend.getName().equals(name)) {
				judge1 = false;
			}
		}
		/*
		 * ユーザーリスト上に存在するかチェック
		 */
		for(User user: userList){
			if(user.getName().equals(name)){
				judge2 = true;
			}
		}

		/*
		 * 友達リストに同じ名前のユーザがいないとき, リストに追加
		 */

		//友達の音楽リストを持ってくる.
		if(judge1 && judge2) {
			friendList.add(new User(name, (new ArrayList<Music>())));
			ret = true;
		}
		return ret;
	}

	/**
	 * 友達リストに友達を追加
	 */
	public boolean removeFriend(String name) {

		boolean ret = false;

		for(int i = 0; i < friendList.size(); i++) {
			if(friendList.get(i).getName().equals(name)) {
				friendList.remove(i);
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * ユーザーデータの文字列を返すメソッド
	 */
	public String toStringData() {
		String data = "";
		data += name+","+password+",";
		for(Music music: musicList) {
			data += music.getName()+",";
			data += music.getArtist()+",";
			data += music.getGenre()+",";
			data += music.getInterest()+",";
		}
		data += "{startFriend}";
		for(User friend: friendList) {
			data += ","+friend.getName();
		}
		return data;
	}

}
