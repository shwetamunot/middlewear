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

import com.niit.dao.JobDao;
import com.niit.dao.UserDao;
import com.niit.model.ErrorClasss;
import com.niit.model.Job;
import com.niit.model.User;

@Controller
public class JobController {
@Autowired
	private UserDao userDao;
@Autowired
private JobDao jobDao;

@RequestMapping(value="/savejob",method=RequestMethod.POST)
public ResponseEntity<?> saveJob(@RequestBody Job job,HttpSession session){
	String username=(String) session.getAttribute("username");
if(username==null){
		ErrorClasss error=new ErrorClasss(5,"Unauthorized access");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
	User user=userDao.getUserByUsername(username);
	if(!user.getRole().equals("Admin")){
		ErrorClasss error=new ErrorClasss(6,"Access Denied");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
	job.setPostedOn(new Date());
	try{
		jobDao.saveJob(job);
	}catch(Exception e)
	{
		ErrorClasss error=new ErrorClasss(7,"Unable to insert job"+e.getMessage());
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return new ResponseEntity<Job>(job,HttpStatus.OK);
}
@RequestMapping(value="/getalljob",method=RequestMethod.GET)
public ResponseEntity<?> getAllJobs(HttpSession session)
{
	String username=(String) session.getAttribute("username");
	if(username==null){
		ErrorClasss error=new ErrorClasss(5,"Unauthorized access");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
List<Job> jobs=jobDao.getAllJobs();
return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
}

@RequestMapping(value="/getjob/{id}",method=RequestMethod.GET)
public ResponseEntity<?> getJob(@PathVariable int id,HttpSession session)
{
	String username=(String) session.getAttribute("username");
	if(username==null){
		ErrorClasss error=new ErrorClasss(5,"Unauthorized access");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
	Job job=jobDao.getJob(id);
	return new ResponseEntity<Job>(job,HttpStatus.OK);
}
}
