package com.niit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.UserDao;
import com.niit.model.ErrorClasss;
import com.niit.model.User;

@Controller
public class UserController {
@Autowired
	private UserDao userDao;
@RequestMapping(value="/registeruser",method=RequestMethod.POST)
public ResponseEntity<?> registerUser(@RequestBody User user)
{
	try
	{
	
		if(!userDao.isUsernameValid(user.getUsername()))
		{
			ErrorClasss error=new ErrorClasss(2,"username already exists,please choose different username");
			return new ResponseEntity<ErrorClasss>(error,HttpStatus.CONFLICT);
		}
		if(!userDao.isEmailValid(user.getEmail()))
		{
			ErrorClasss error=new ErrorClasss(3,"Emailid already exists please enter different email");
			return new ResponseEntity<ErrorClasss>(error,HttpStatus.CONFLICT);
		}	
		System.out.println("acsmkk111");
		userDao.registerUser(user);
		System.out.println("acsmkk");
	}catch(Exception e)
	{
		ErrorClasss error=new ErrorClasss(1,"Unable to register");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return new ResponseEntity<User>(user,HttpStatus.OK);
}
}
