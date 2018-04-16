/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streaming.test;

import java.util.List;
import static org.junit.Assert.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.Test;
import streaming.entity.Film;
import streaming.entity.Lien;
import streaming.entity.Personne;
import streaming.entity.Serie;

/**
 *
 * @author romua
 */
public class ExercicesTest {

    @Test
    public void req1() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT f "
                                    + "FROM Film f "
                                    + "WHERE f.id=4");
        Film film = (Film) query.getSingleResult();
        
        assertEquals("Fargo", film.getTitre());
    }
    
    @Test
    public void req2() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT COUNT (f) "
                                    + "FROM Film f");
        long r = (long) query.getSingleResult();
     
        assertEquals(4L, r);
    }
    
    @Test
    public void req3() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT MIN (f.annee) "
                                    + "FROM Film f");
        int d = (int) query.getSingleResult();
        
        assertEquals(1968, d);
    }
    
    @Test
    public void req4()  {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT COUNT (l) "
                                    + "FROM Lien l "
                                        + "JOIN l.film f "
                                    + "WHERE f.titre='Big Lebowski (The)'");
        long nbUrl = (long) query.getSingleResult();
        
        assertEquals(1L, nbUrl);
    }
    
    @Test
    public void req5()  {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT COUNT(f) "
                                    + "FROM Film f "
                                        + "JOIN f.realisateurs r "
                                    + "WHERE r.nom='Polanski'");
        long filmsReal = (long) query.getSingleResult();
        
        assertEquals(2L, filmsReal);
    }
    
    @Test
    public void req6()  {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT COUNT (f) "
                                    + "FROM Film f "
                                        + "JOIN f.acteurs a "
                                    + "WHERE a.nom='Polanski'");
        long filmsJoue = (long) query.getSingleResult();
        
        assertEquals(1L, filmsJoue);
    }
    
    @Test
    public void req7 () {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT COUNT (f) "
                                    + "FROM Film f "
                                        + "JOIN f.realisateurs r "
                                        + "JOIN f.acteurs a "
                                    + "WHERE a.nom='Polanski' AND r.nom='Polanski'");
        long filmsRealEtJoue = (long) query.getSingleResult();
        
        assertEquals(1L, filmsRealEtJoue);
    }
    
    @Test
    public void req8()  {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.genre g "
                                        + "JOIN f.pays p "
                                    + "WHERE g.nom='Horreur' AND p.nom='UK'");
        Film filmReq8 = (Film) query.getSingleResult();
        
        assertEquals("Le bal des vampires", filmReq8.getTitre());
    }
    
    @Test
    public void req9()  {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT COUNT (f) "
                                    + "FROM Film f "
                                        + "JOIN f.realisateurs r "
                                    + "WHERE r.nom='Coen' AND r.prenom='Joel'");
        long filmsRealReq9 = (long) query.getSingleResult();
        
        assertEquals(2L, filmsRealReq9);
    }
    
    @Test
    public void req10() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.realisateurs r "
                                    + "WHERE r.nom='Coen' AND r.prenom='Joel' "
                                    + "INTERSECT SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.realisateurs r "
                                    + "WHERE r.nom='Coen' AND r.prenom='Ethan'");
        List<Film> filmsRealReq10 = (List) query.getResultList();
                
        assertEquals(2L, filmsRealReq10.size());
    }
    
    @Test
    public void req11()    {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.realisateurs r "
                                    + "WHERE r.nom='Coen' AND r.prenom='Joel' "
                                    + "INTERSECT SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.realisateurs r "
                                    + "WHERE r.nom='Coen' AND r.prenom='Ethan' "
                                    + "INTERSECT SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.acteurs a "
                                    + "WHERE a.nom='Buscemi' AND a.prenom='Steve'");
        List<Film> filmsRealReq11 = (List) query.getResultList();
        
        assertEquals(2L, filmsRealReq11.size());
    }
    
    @Test
    public void req12() {
       
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.genre g "
                                    + "WHERE g.nom='Policier' "
                                    + "INTERSECT SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.realisateurs r "
                                    + "WHERE r.nom='Coen' AND r.prenom='Joel' "
                                    + "INTERSECT SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.realisateurs r "
                                    + "WHERE r.nom='Coen' AND r.prenom='Ethan' "
                                    + "INTERSECT SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.acteurs a "
                                    + "WHERE a.nom='Buscemi' AND a.prenom='Steve'");
        List<Film> filmsRealReq12 = (List) query.getResultList();
        
        assertEquals(1, filmsRealReq12.size());
   }
    
    @Test
    public void req13() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT COUNT (s) "
                                    + "FROM Saison s "
                                        + "JOIN s.serie se "
                                    + "WHERE se.titre='Dexter'");
        long serieReq13 = (Long) query.getSingleResult();
        
        assertEquals(8L, serieReq13);
    }
    
    @Test
    public void req14() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT COUNT (e) "
                                    + "FROM Episode e "
                                        + "JOIN e.saison s "
                                        + "JOIN s.serie se "
                                    + "WHERE se.titre='Dexter'");
        long serieReq14 = (Long) query.getSingleResult();
        
        assertEquals(96L, serieReq14);        
    }
    
    @Test
    public void req15() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT COUNT (e) "
                                    + "FROM Episode e "
                                        + "JOIN e.saison s "
                                        + "JOIN s.serie se "
                                    + "WHERE se.titre='Dexter' AND s.numSaison=8");
        long serieReq15 = (long) query.getSingleResult();
        
        assertEquals(12L, serieReq15);
    }
    
    @Test
    public void req16() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT COUNT (l) "
                                    + "FROM Lien l "
                                        + "JOIN l.film f "
                                        + "JOIN f.genre g "
                                    + "WHERE g.nom='Policier'");
        long seriesReq16 = (long) query.getSingleResult();
        
        assertEquals(3L, seriesReq16);
    }
    
    @Test
    public void req17() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT l "
                                    + "FROM Lien l "
                                        + "JOIN l.film f "
                                        + "JOIN f.genre g "
                                    + "WHERE g.nom='Horreur' "
                                    + "INTERSECT SELECT l "
                                        + "FROM Lien l "
                                        + "JOIN l.film f "
                                        + "JOIN f.realisateurs r "
                                    + "WHERE r.nom='Polanski'");
        List<Lien> filmsReq17 = (List) query.getResultList();
        
        assertEquals(1 , filmsReq17.size());
    }
    
    @Test
    public void req18() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
 
        Query query = em.createQuery("SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.genre g "
                                    + "WHERE g.nom='Horreur' "
                                    + "EXCEPT SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.realisateurs r "
                                    + "WHERE r.nom='Polanski'");
        List<Film> filmsReq18 = (List) query.getResultList();
        
        assertEquals(0, filmsReq18.size());
    }
    
    @Test
    public void req19() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.acteurs a "
                                    + "WHERE a.nom='Polanski'");
        List<Film> filmsReq19 = (List) query.getResultList();
        
        assertEquals(1 , filmsReq19.size());
    }
    
    @Test
    public void req20(){
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.acteurs a "
                                    + "WHERE a.nom='Polanski' "
                                    + "UNION SELECT f "
                                    + "FROM Film f "
                                        + "JOIN f.genre g "
                                    + "WHERE g.nom='Horreur'");
        List<Film> filmsReq20 = (List) query.getResultList();
        
        assertEquals(1 , filmsReq20.size());
    }
    
    @Test
    public void req21() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT g.nom, COUNT (f) "
                                    + "FROM Genre g "
                                        + "LEFT JOIN g.films f "
                                    + "GROUP BY g");
        List<Object[]> req21 = query.getResultList();
        
        for (Object[] tableauReq21 : req21) {
            
            String genreReq21 = (String) tableauReq21[0];
            long nbFilmsReq21 = (long) tableauReq21[1];
            
            System.out.println(genreReq21+" "+nbFilmsReq21);
        }
       
    }
        
    @Test
    public void req22() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT p.nom, COUNT (p) "
                                    + "FROM Personne p "
                                        + "JOIN p.filmsRealises fr "
                                    + "GROUP BY p "
                                    + "ORDER BY p.nom ASC");
        List<Object[]> req22 = query.getResultList();
        
        for (Object[] tableauReq22 : req22) {
            
            String realReq22 = (String) tableauReq22[0];
            long nbFilmsReq22 = (long) tableauReq22[1];
            
            System.out.println(nbFilmsReq22+"  "+realReq22);
        }
    }
        
    @Test
    public void req23() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT p.nom, COUNT (p) AS total "
                                    + "FROM Personne p "
                                        + "JOIN p.filmsRealises fr "
                                    + "GROUP BY p "
                                    + "HAVING COUNT (p) >= 2 "
                                    + "ORDER BY total, p.nom ASC");
        List<Object[]> req23 = query.getResultList();
        
        for (Object[] tableauReq23 : req23) {
            
            String realReq23 = (String) tableauReq23[0];
            long nbFilmsReq23 = (long) tableauReq23[1];
            
            System.out.println(nbFilmsReq23+"  "+realReq23);
        }
    }
    
    @Test
    public void req24() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT s.titre, COUNT (sa) AS total "
                                    + "FROM Serie s "
                                        + "JOIN s.saisons sa "
                                    + "GROUP BY s "
                                    + "ORDER BY total, s.titre ASC");
        List<Object[]> req24 = query.getResultList();
        
        for (Object[] tableauReq24 : req24) {

            String nomReq24 = (String) tableauReq24[0];
            long nbEpisodeReq24 = (long) tableauReq24[1];
            
            System.out.println(nomReq24+" # "+nbEpisodeReq24+" épisodes");
        }
    }
    
    @Test
    public void req25() {
        
       EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU");
       EntityManager em = factory.createEntityManager();

       Query query = em.createQuery("SELECT COUNT(e) AS total, s.titre "
                                    + "FROM     Serie s "
                                        + "JOIN s.saisons sa "
                                        + "JOIN sa.episodes e "
                                    + "GROUP BY s "
                                    + "HAVING total>5 "
                                    + "ORDER BY total");

        List<Object[]> req25 = query.getResultList();
        for (Object[] tableauReq25 : req25) {

            System.out.println(String.format("%s %d", tableauReq25[1], tableauReq25[0])); // autre façon de présenter les résultats 
        
        }
    }
}