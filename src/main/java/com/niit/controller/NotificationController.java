package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.NotificationDao;
import com.niit.model.ErrorClasss;
import com.niit.model.Notification;

@Controller
public class NotificationController {
@Autowired
	private NotificationDao notificationDao; 
@RequestMapping(value="/getnotification/{viewed}",method=RequestMethod.GET)
public ResponseEntity<?> getNotification(@PathVariable int viewed,HttpSession session){

String username=(String) session.getAttribute("username");
if(username==null)
{
	ErrorClasss error=new ErrorClasss(5,"Unauthorized Access");
	return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
}
List<Notification> notifications=notificationDao.getNotification(username, viewed);
return new ResponseEntity<List<Notification>>(notifications,HttpStatus.OK);
}
@RequestMapping(value="/updatenotification/{id}",method=RequestMethod.PUT)
public ResponseEntity<?> updateNotification(@PathVariable int id,HttpSession session){
	String username=(String) session.getAttribute("username");
	if(username==null)
	{
		ErrorClasss error=new ErrorClasss(5,"Unauthorized Access");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
Notification notification=notificationDao.updateNotification(id);
return new ResponseEntity<Notification>(notification,HttpStatus.OK);
}
}