/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streaming.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.Test;
import streaming.entity.Film;
import streaming.entity.Genre;
import streaming.entity.Pays;

/**
 *
 * @author romua
 */
public class AccesBase {
    
    //@Test
    public void ajouterUnFilm() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
     
        em.getTransaction().begin(); // début de la transaction
        
        Genre g = em.find(Genre.class, 3L); //récupération du genre Fantastique
        //Query query = em.createQuery("SELECT g FROM Genre g WHERE g.nom='Fantastique'"); // même récupération que précédemmnent mais avec une requête
        //Genre g = (Genre) query.getSingleResult();
        
        Pays france = em.find(Pays.class, 2L);
        Pays usa = em.find(Pays.class, 3L);
        
        Film f = new Film(); // Création d'un nouvel objet
        
        em.persist(f); // on attache l'élément dans l'entity manager
        
        f.setTitre("5eme Element");
        f.setAnnee(1997);
        f.setSynopsis("Deux savants découvrent l'existence, outre l'eau, l'air, la terre et le feu, d'un cinquième élément.");
        
        //configuration avec un manytomany
        f.getPays().add(france);
        france.getFilmsProduits().add(f);
        
        f.getPays().add(usa);
        usa.getFilmsProduits().add(f);
        
        //configuration avec un manytoone
        f.setGenre(g); //configuration du genre dans notre objet
        g.getFilms().add(f); // obligation dans les cas de relations bidirectionnnelles
       
        em.getTransaction().commit(); // on pousse les modifications et on les valide
    }
    
    //@Test
    public void ajouterGenre()  {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        
        Genre g = new Genre();
        
        em.persist(g);
        
        g.setNom("Documentaire");
               
        em.getTransaction().commit();
    }
    
    //@Test
    public void modificationFilmParSet()   {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        
        Film f = em.find(Film.class, 14L);
        
        f.setTitre("*** bla bla bla ***");
        
        em.getTransaction().commit();
    }
    
    //@Test
    public void modificationFilmParMerge()  {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        
        Film f = new Film();
        
        f.setId(16L);
        f.setTitre("*** NO NO NO ****");
        
        em.merge(f);
        
        em.getTransaction().commit();
    }
    
    //@Test
    public void effacerMethode1()   {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        
        Genre g = em.find(Genre.class, 8L);
        
        em.remove(g);
        
        em.getTransaction().commit();
    }
    
    //@Test
    public void effacerMethode2()   { // méthode à priviligier pour les effacements
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        
        Query query = em.createQuery("DELETE FROM Genre g WHERE g.id=9");
        
        query.executeUpdate();
               
        em.getTransaction().commit();
    }
}
