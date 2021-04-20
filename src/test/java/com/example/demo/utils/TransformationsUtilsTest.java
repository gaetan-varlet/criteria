package com.example.demo.utils;

import static com.example.demo.utils.TransformationsUtils.filtrerParametresRequetes;
import static com.example.demo.utils.TransformationsUtils.getAttributsPersonne;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TransformationsUtilsTest {

	@Test
	void testAttributsPersonne() throws Exception {
		Set<String> attributs = getAttributsPersonne();
		assertThat(attributs).containsExactlyInAnyOrder("id", "nom", "prenom");
	}

	@Test
	void testFiltrerParametresRequetes() throws Exception {
		Set<String> set = Set.of("a", "b");
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.put("a", List.of("1", "2"));
		multiValueMap.put("b", List.of("3", "4"));
		multiValueMap.put("c", List.of("5", "6"));
		Map<String, Set<String>> attributs = filtrerParametresRequetes(multiValueMap, set);
		assertThat(attributs).hasSize(2);
		assertThat(attributs.get("a")).isEqualTo(Set.of("1", "2"));
		assertThat(attributs.get("b")).isEqualTo(Set.of("3", "4"));
	}

}
