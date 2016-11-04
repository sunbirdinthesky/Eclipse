package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import sqlOperation.SqlOperation;

public class User {
	Vector<Blog> blogs = new Vector<>();
	String userName;
	SqlOperation sql = new SqlOperation();
	public User(String userName){
		// TODO Auto-generated constructor stub
		this.userName = userName;
		sql.SqlInit();
	}
	
	public Vector<Blog> getBlogs () {
		
		sql.SqlQuery("select * from " + userName);
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			blogs.clear();
			while (sql.rSet.next()) {
				Blog blog = new Blog();
				blog.id = sql.rSet.getInt(1);
				blog.publishTime = dateFormat.parse(sql.rSet.getString(2));
				blog.content = sql.rSet.getString(3);
				blogs.add(blog);
			}
		} catch (Exception e) {
		}
		
		return blogs;
	}
	
	public void deleteBlog (int blogId) {
		sql.SqlUpdate("delete from " + userName + " where id=" + blogId);
	}
	
	public void publishBlog(String content) {
		sql.SqlUpdate("insert into sunbird(publishTime, content) values(now(), \"" + content + "\")");
	}
}
