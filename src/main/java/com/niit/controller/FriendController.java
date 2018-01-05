package com.niit.controller;

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

import com.niit.dao.FriendDao;
import com.niit.model.ErrorClasss;
import com.niit.model.Friend;
import com.niit.model.User;

@Controller
public class FriendController {
@Autowired
private FriendDao friendDao;

@RequestMapping(value="/suggestedusers",method=RequestMethod.GET)	
public ResponseEntity<?> getSuggestedUsers(HttpSession session){
	//String username="sejal";
String username=(String) session.getAttribute("username");
	if(username==null)
	{
		ErrorClasss error=new ErrorClasss(5,"Unauthorized Access");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
	List<User> suggestedUser=friendDao.suggestedUserList(username);
	return new ResponseEntity<List<User>>(suggestedUser,HttpStatus.OK);
}
@RequestMapping(value="/addfriendrequest/{ToId}",method=RequestMethod.POST)
public ResponseEntity<?> addFriendRequest(@PathVariable String ToId,HttpSession session){
	String username=(String) session.getAttribute("username");
	if(username==null)
	{
		ErrorClasss error=new ErrorClasss(5,"Unauthorized Access");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
Friend friend=new Friend();
friend.setFromId(username);
friend.setToId(ToId);
friend.setStatus('P');
friendDao.addFriendRequest(friend);
return new ResponseEntity<Friend>(friend,HttpStatus.OK);
}
@RequestMapping(value="/pendingrequests",method=RequestMethod.GET)
public ResponseEntity<?> pendingRequests(HttpSession session){
	//String username="punam";
	String username=(String) session.getAttribute("username");
	if(username==null)
	{
		ErrorClasss error=new ErrorClasss(5,"Unauthorized Access");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
	List<Friend> pendingRequests=friendDao.pendingRequests(username);
	return new ResponseEntity<List<Friend>>(pendingRequests,HttpStatus.OK);
}
@RequestMapping(value="/updatependingrequest",method=RequestMethod.PUT)
public ResponseEntity<?> updatePendingRequest(@RequestBody Friend friend,HttpSession session)
{
	String username=(String) session.getAttribute("username");
	if(username==null)
	{
		ErrorClasss error=new ErrorClasss(5,"Unauthorized Access");
		return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
	}
	friendDao.updatePendingRequests(friend);
	return new ResponseEntity<Void>(HttpStatus.OK);
}

@RequestMapping(value="/getfriend",method=RequestMethod.GET)
public ResponseEntity<?> getListOfFriends(HttpSession session)
{
	String username=(String) session.getAttribute("username");
    if(username==null)
    {
	ErrorClasss error=new ErrorClasss(5,"Unauthorized Access");
	return new ResponseEntity<ErrorClasss>(error,HttpStatus.UNAUTHORIZED);
    }
    List<User> friends=friendDao.listOfFriends(username);
    return new ResponseEntity<List<User>>(friends,HttpStatus.OK);
}
}
