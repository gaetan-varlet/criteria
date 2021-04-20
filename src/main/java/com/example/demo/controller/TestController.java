package com.example.demo.controller;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("test")
	public void test(@RequestParam Map<String, String> map) {
		System.out.println("test :" + map);
		// http://localhost:8080/test?toto=1&tata=2&toto=3&titi=4
		// test :{toto=1, tata=2, titi=4}

	}

	@GetMapping("test2")
	public void test2(@RequestParam Map<String, List<String>> map) {
		System.out.println("test2 :" + map);
		// http://localhost:8080/test2?toto=1&tata=2&toto=3&titi=4
		// test2 :{toto=1, tata=2, titi=4}
		// => ne fonctionne pas
	}

	@GetMapping("test3")
	public void test3(@RequestParam MultiValueMap<String, String> multiValueMap) {
		// MultiValueMap<K,V> extends Map<K,List<V>>
		// Extension of the Map interface that stores multiple values
		System.out.println("test3 :" + multiValueMap);
		// http://localhost:8080/test3?toto=1&tata=2&toto=3&titi=4
		// test3 :{toto=[1, 3], tata=[2], titi=[4]}
		Map<String, Set<String>> map = multiValueMap.entrySet().stream()
				.collect(toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(toSet())));
		System.out.println("test3 après transo :" + map);
		// test3 après transo :{toto=[1, 3], tata=[2], titi=[4]}

	}

}
