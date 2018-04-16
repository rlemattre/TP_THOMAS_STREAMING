/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streaming.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.junit.Test;
import streaming.entity.Film;

/**
 *
 * @author romua
 */
public class AccesBase {
    
    @Test
    public void ajouter() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
     
        em.getTransaction().begin();
        
        Film f = new Film();
        
        em.persist(f);
        
        f.setTitre("5eme Element");
        f.setAnnee(1997);
        f.setSynopsis("Deux savants découvrent l'existence, outre l'eau, l'air, la terre et le feu, d'un cinquième élément.");
       
        em.getTransaction().commit();
    }
    
}
