package myMusicLab;

/**
 * 音楽の情報を管理するクラス
 */

public class Music {
	//フィールドの定義
	private String name;
	private String artist;
	private String genre;
	private String interest;

	//コンストラクタの定義
	public Music(String name, String artist, String genre, String interest) {
		this.name = name;
		this.artist = artist;
		this.genre = genre;
		this.interest = interest;
	}

	/**
	 * 各フィールドに対するセッター・ゲッター
	 */

	public void setName(String name) {
		this.name = name;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getName() {
		return name;
	}

	public String getArtist() {
		return artist;
	}

	public String getGenre() {
		return genre;
	}

	public String getInterest() {
		return interest;
	}

}
