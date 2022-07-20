package com.example.demo.service;

import static com.example.demo.utils.TransformationsUtils.filtrerParametresRequetes;
import static com.example.demo.utils.TransformationsUtils.getAttributsPersonne;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.example.demo.model.Personne;
import com.example.demo.repository.PersonneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class PersonneService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private PersonneRepository personneRepository;

	public List<Personne> findAll(MultiValueMap<String, String> multiValueMap)
			throws ClassNotFoundException {
		// si pas de critères de filtre, renvoie de toutes les personnes
		if (multiValueMap == null || multiValueMap.isEmpty()) {
			return personneRepository.findAll();
			// sinon utilisation des filtres
		} else {
			// récupération de la liste des attributs de Personne
			Set<String> set = getAttributsPersonne();
			// filtrage des paramètres pour ne garder que les attributs de Personne
			Map<String, Set<String>> attributs = filtrerParametresRequetes(multiValueMap, set);
			// construction de la requête
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Personne> query = builder.createQuery(Personne.class);
			Root<Personne> resourceRoot = query.from(Personne.class);
			// exécution de la requête
			return em.createQuery(query.where(creationPredicate(builder, resourceRoot, attributs)))
					.getResultList();
		}
	}

	private Predicate[] creationPredicate(CriteriaBuilder builder, Root<Personne> root,
			Map<String, Set<String>> attributs) {
		List<Predicate> clauses = new ArrayList<>();
		// ajout d'une clause where sur chacun des attributs de la map
		for (String key : attributs.keySet()) {
			// si une seule valeur, utilisation d'un EQUAL
			if (attributs.get(key).size() == 1) {
				clauses.add(builder.equal(root.get(key),
						attributs.get(key).stream().findFirst().get()));
				// si deux valeurs, utilisation d'un IN
			} else if (attributs.get(key).size() >= 2) {
				clauses.add(root.get(key).in(attributs.get(key)));
			}
		}
		return clauses.toArray(new Predicate[clauses.size()]);
	}

	// TODO : à voir, ne fonctionne pas pour le moment
	private Predicate[] creationPredicateSansAccents(CriteriaBuilder builder, Root<Personne> root,
			Map<String, Set<String>> attributs) {
		List<Predicate> clauses = new ArrayList<>();
		Expression<String> unaccent = builder.function("unaccent", String.class,
				builder.lower(root.get("prenom")));
		Predicate p = builder.like(unaccent, "%" + "toto" + "%");
		clauses.add(p);
		return clauses.toArray(new Predicate[clauses.size()]);
	}

}
