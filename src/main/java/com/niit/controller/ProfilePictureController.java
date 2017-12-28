package com.niit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.dao.ProfilePicDao;
import com.niit.model.ErrorClasss;
import com.niit.model.ProfilePicture;
@Controller
public class ProfilePictureController {

	@Autowired
	private ProfilePicDao profilePicDao;
@RequestMapping(value="/uploadprofilepic",method=RequestMethod.POST)
	public ResponseEntity<?> uploadProfilePicture(@RequestParam CommonsMultipartFile image,HttpSession session){
	String username=(String) session.getAttribute("username");
	if(username==null)
	{
		ErrorClasss error=new ErrorClasss(5,"Unauthorized Access");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
	ProfilePicture profilePicture=new ProfilePicture();
	profilePicture.setImage(image.getBytes());
	profilePicture.setUsername(username);
profilePicDao.saveOrUpdateProfilePicture(profilePicture);
return new ResponseEntity<ProfilePicture>(profilePicture,HttpStatus.OK);
}

@RequestMapping(value="/getimage/{username}",method=RequestMethod.GET)
public @ResponseBody byte[] getProfilePicture(@PathVariable String username,HttpSession session){
	String loginId=(String) session.getAttribute("username");
	if(loginId==null){
		return null;
	}
	ProfilePicture profilePicture=profilePicDao.getProfilePicture(username);
	if(profilePicture==null)
		return null;
	else
		return profilePicture.getImage();
	
}
}
