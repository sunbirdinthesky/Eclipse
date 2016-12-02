package com.yang.control;

public class BookBean {
	private String bookid="";
	private String title="";
	private String author="";
	private String publisher="";
	private double price=0.0;
	public BookBean(){
	}
	public BookBean(String bookid, String author,String title,String publisher,double price){
		this.bookid=bookid;
		this.title=title;
		this.author=author;
		this.publisher=publisher;
		this.price=price;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}
