package com.myfence.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/gps")
public class GpsController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	 public String getDefault() {
	  String result="Welcome to GPS Controller ";  
	  return result;
	 }

}
