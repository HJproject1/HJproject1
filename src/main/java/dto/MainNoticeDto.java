package dto;

	/*BNO
	TITLE
	W_DATE
	CONTENT*/
public class MainNoticeDto {
	private int bno;
	private String title;
	private String w_date;
	private String content;
	
	public MainNoticeDto(int bno, String title, String w_date, String content) {
		super();
		this.bno = bno;
		this.title = title;
		this.w_date = w_date;
		this.content = content;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getW_date() {
		return w_date;
	}

	public void setW_date(String w_date) {
		this.w_date = w_date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
