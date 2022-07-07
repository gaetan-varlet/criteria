package com.example.demo.utils;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.util.MultiValueMap;

public class TransformationsUtils {

	private TransformationsUtils() {
	}

	public static Map<String, Set<String>> filtrerParametresRequetes(MultiValueMap<String, String> multiValueMap,
			Set<String> parametres) {
		// transformation en map Java
		Map<String, Set<String>> map = multiValueMap.entrySet().stream()
				.collect(toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(toSet())));
		// conservation des Entry dont les clés sont dans la collection 'parametres'
		Map<String, Set<String>> newMap = map.entrySet().stream()
				.filter(e -> parametres.contains(e.getKey()))
				.collect(toMap(e -> e.getKey(), e -> e.getValue()));
		return newMap;
	}

	@SuppressWarnings("rawtypes")
	public static Set<String> getAttributsPersonne() throws ClassNotFoundException {
		Class cl = Class.forName("com.example.demo.model.Personne");
		Field[] attributsPrives = cl.getDeclaredFields();
		Set<String> retour = new HashSet<>();
		for (Field att : attributsPrives) {
			retour.add(att.getName());
		}
		return retour;
	}

}
