package com.example.demo.controller;

import java.util.List;

import com.example.demo.model.Personne;
import com.example.demo.service.PersonneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("personne")
public class PersonneController {

	@Autowired
	private PersonneService personneService;

	@GetMapping
	public List<Personne> test(@RequestParam MultiValueMap<String, String> multiValueMap)
			throws ClassNotFoundException {
		return personneService.findAll(multiValueMap);
	}

}
