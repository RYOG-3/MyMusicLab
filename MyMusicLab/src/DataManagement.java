package myMusicLab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * csvファイルを用いて, ユーザーのデータを読み書きするクラス
 */

public class DataManagement {

	public static ArrayList<User> loadUserData(){

		String filename = "DataOfMyMusicLab.csv";

		/*
		 * 戻り値となるリストを作成
		 */
		ArrayList<User> userList = new ArrayList<User>();

		/*
		 * ファイルの読み込み
		 */
		Path path = Paths.get(filename);

		try(BufferedReader reader = Files.newBufferedReader(path)){

			String line;



			while((line = reader.readLine()) != null) {
				String[] data = line.split(",");


			/*
			 * 音楽リストを作成
			 */
			ArrayList<Music> musicList = new ArrayList<Music>();

			int count = 2;
			while(!data[count].equals("{startFriend}")) {
				musicList.add(new Music(data[count], data[count+1], data[count+2], data[count+3]));
				count = count+4;
			}


			/*
			 * 友達リストを作成
			 */

			ArrayList<User> friendList = new ArrayList<User>();

			for(int i = count+1; i < data.length; i++) {
				/*
				 * 友達の名前をバッファに格納
				 */
				String name = data[i];

				/*
				 * 友達の名前と一致するユーザデータが存在するか確かめる.
				 */
				Path path2 = Paths.get(filename);
				try(BufferedReader reader2 = Files.newBufferedReader(path2)){
					String line2;

					while((line2 = reader2.readLine()) != null) {
						String[] data2 = line2.split(",");

						if(data2[0].equals(name)) {
							ArrayList<Music> friendMusicList = new ArrayList<Music>();


							int j = 2;
							while(!data2[j].equals("{startFriend}")) {
								friendMusicList.add(new Music(data2[j], data2[j+1], data2[j+2], data2[j+3]));
								j = j+4;
							}
							friendList.add(new User(data[i], friendMusicList));
							break;
						}
					}
				}catch(IOException e) {
					System.out.println(e);
				}
			}

			//ユーザーリストに追加
			userList.add(new User(data[0], data[1], musicList, friendList));
			}
		} catch(IOException e) {
			System.out.println(e);
		}
		return userList;
	}

	/**
	 * ユーザーデータをcsvに出力するメソッド
	 */
	public static void dumpUserData(ArrayList<User> userList) {

		String filename = "DataOfMyMusicLab.csv";

		Path path = Paths.get(filename);

		try(BufferedWriter writer = Files.newBufferedWriter(path)){

			for(User user: userList) {
				writer.write(user.toStringData()+("\n"));
			}


		} catch(IOException e) {
			System.out.println(e);
		}
	}

}
