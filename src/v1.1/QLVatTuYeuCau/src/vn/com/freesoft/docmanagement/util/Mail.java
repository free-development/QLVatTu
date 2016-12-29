package vn.com.freesoft.docmanagement.util;

public class Mail {
	private String subject;
	private String content;
	private String from;
	private String to;

	/**
	 * contructor
	 */
	public Mail() {
		this.subject = "";
		this.content = "";
		this.from = "";
		this.to = "";
	}

	/**
	 * @param subject
	 * @param content
	 * @param from
	 * @param to
	 */
	public Mail(String subject, String content, String from, String to) {
		this.subject = subject;
		this.content = content;
		this.from = from;
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setContent(String content2, String string) {

	}

}
