package com.tp.queries.repository;

import com.tp.queries.model.Reservation;
import com.tp.queries.model.Salle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SalleRepositoryImpl implements SalleRepository {

    private EntityManager entityManager;

    public SalleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Salle> findAvailableRooms(LocalDateTime debutt, LocalDateTime finn) {
        String jpql = "SELECT s FROM Salle s WHERE s.id NOT IN (" +
                      "SELECT r.salle.id FROM Reservation r WHERE " +
                      "(:debut < r.finn AND :fin > r.debutt))";

        return entityManager.createQuery(jpql, Salle.class)
                .setParameter("debut", debutt)
                .setParameter("fin", finn)
                .getResultList();
    }

    @Override
    public List<Salle> searchRooms(String nomm, Integer capaciteeMin, LocalDateTime debutt, LocalDateTime finn, int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Salle> cq = cb.createQuery(Salle.class);
        Root<Salle> salle = cq.from(Salle.class);

        List<Predicate> predicates = new ArrayList<>();

        if (nomm != null && !nomm.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(salle.get("nom")), "%" + nomm.toLowerCase() + "%"));
        }

        if (capaciteeMin != null) {
            predicates.add(cb.ge(salle.get("capacite"), capaciteeMin));
        }

        if (debutt != null && finn != null) {
            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<Reservation> reservation = subquery.from(Reservation.class);
            subquery.select(reservation.get("salle").get("id"));

            Predicate chevauchement = cb.and(
                    cb.lessThan(reservation.get("debut"), finn),
                    cb.greaterThan(reservation.get("fin"), debutt)
            );

            subquery.where(chevauchement);
            predicates.add(cb.not(salle.get("id").in(subquery)));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        cq.select(salle);

        TypedQuery<Salle> query = entityManager.createQuery(cq);
        if (page > 0 && size > 0) {
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
        }

        return query.getResultList();
    }

    @Override
    public long countRooms(String nomm, Integer capaciteeMin, LocalDateTime debutt, LocalDateTime finn) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Salle> salle = cq.from(Salle.class);

        List<Predicate> predicates = new ArrayList<>();

        if (nomm != null && !nomm.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(salle.get("nom")), "%" + nomm.toLowerCase() + "%"));
        }

        if (capaciteeMin != null) {
            predicates.add(cb.ge(salle.get("capacite"), capaciteeMin));
        }

        if (debutt != null && finn != null) {
            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<Reservation> reservation = subquery.from(Reservation.class);
            subquery.select(reservation.get("salle").get("id"));

            Predicate chevauchement = cb.and(
                    cb.lessThan(reservation.get("debut"), finn),
                    cb.greaterThan(reservation.get("fin"), debutt)
            );

            subquery.where(chevauchement);
            predicates.add(cb.not(salle.get("id").in(subquery)));
        }

        cq.select(cb.count(salle));

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        return entityManager.createQuery(cq).getSingleResult();
    }
}
