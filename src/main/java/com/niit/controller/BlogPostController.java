package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.BlogPostDao;
import com.niit.dao.UserDao;
import com.niit.model.BlogPost;
import com.niit.model.ErrorClasss;
import com.niit.model.User;

@Controller

public class BlogPostController {
	@Autowired
	private BlogPostDao blogPostDao;
	@Autowired
    private UserDao userDao;

@RequestMapping(value="/saveblog",method=RequestMethod.POST)
public  ResponseEntity<?> saveBlogPost(@RequestBody BlogPost blogPost,HttpSession session)
{
	String username=(String) session.getAttribute("username");
	if(username==null)
	{
		ErrorClasss error=new ErrorClasss(5,"Unauthorized Access");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
		
//	String username="sejal";
	User user=userDao.getUserByUsername(username);
blogPost.setPostedOn(new Date());
blogPost.setPostedBy(user);
try{
	blogPostDao.saveBlogPost(blogPost);
}catch(Exception e)
{
	ErrorClasss error=new ErrorClasss(6,"Unable to insert blog");
			return new ResponseEntity<ErrorClasss>(error,HttpStatus.INTERNAL_SERVER_ERROR);
}
return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
}

@RequestMapping(value="/getblogs/{approved}",method=RequestMethod.GET)
public ResponseEntity<?> getBlogs(@PathVariable int approved,HttpSession session)
{
	String username=(String) session.getAttribute("username");
	if(username==null)
	{
		ErrorClasss error=new ErrorClasss(5,"Unauthorized Access");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
	
//	String username="admin";
	if(approved==0){
		User user=userDao.getUserByUsername(username);
		if(!user.getRole().equals("Admin")){
			ErrorClasss error=new ErrorClasss(7,"Access Denied");
	return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
		}
	}

	List<BlogPost> blogPosts=blogPostDao.getBlogs(approved);
	return new ResponseEntity<List<BlogPost>>(blogPosts,HttpStatus.OK);
	
}
@RequestMapping(value="/getblog/{id}",method=RequestMethod.GET)
public ResponseEntity<?> getBlogPost(@PathVariable int id,HttpSession session){
	String username=(String) session.getAttribute("username");
	if(username==null)
	{
		ErrorClasss error=new ErrorClasss(5,"Unauthorized Access");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
	BlogPost blogPost=blogPostDao.getBlogById(id);
	return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
}
}

