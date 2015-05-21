package com.myfence.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.myfence.model.Login;

@RestController
public class LoginController {
	
	MongoClient mongoClient = new MongoClient("localhost", 27017);
	MongoDatabase db = mongoClient.getDatabase("test");
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	 public String getDefault() {
	  String result="Welcome to My-Fence ";  
	  return result;
	 }
	
	@RequestMapping(value = "/register/{name}/{password}", method = RequestMethod.GET)
	 public void registerAction(@PathVariable String name,@PathVariable String password) {
		db.getCollection("login").insertOne(new Document().append("username",name).append("password",password));
	  
	 }
	
	@RequestMapping(value = "/login/{name}/{password}", method = RequestMethod.GET)
	 public Login loginAction(@PathVariable String name,@PathVariable String password) {
		final Login cred=new Login();
	  FindIterable<Document> iterable = db.getCollection("login").find(
		        new Document("username",name).append("password",password));
	  iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {    	
		        cred.setUserName(document.getString("username"));
		        cred.setPassword(document.getString("password"));
		    }
		});
	  return cred;
	 }
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	 public List<Login> getLogin() {
		final List<Login> users=new ArrayList<Login>();
		FindIterable<Document> iterable = db.getCollection("login").find();
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	Login cred=new Login();
		    	cred.setUserName(document.getString("username"));
		        cred.setPassword(document.getString("password"));
		        users.add(cred);
		    }
		});
		return users;
	 }
	@RequestMapping(value = "/update/{name}/{password}", method = RequestMethod.GET)
	 public void updateRecords(@PathVariable String name,@PathVariable String password) {
		db.getCollection("login").updateOne(new Document("username",name),
		        new Document("$set", new Document("password",password)));
	 }
	@RequestMapping(value = "/delete/{name}", method = RequestMethod.GET)
	 public void deleteRecords(@PathVariable String name) {
		db.getCollection("login").deleteMany(new Document("username",name));
	 }
	@RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
	 public void deleteAllRecords() {
		db.getCollection("login").deleteMany(new Document());
	 }
	
	
	
	

}

