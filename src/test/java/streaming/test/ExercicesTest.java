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

/**
 *
 * @author romua
 */
public class ExercicesTest {

    @Test
    public void req1() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query =em.createQuery("SELECT f FROM Film f WHERE f.id=4");
        Film film = (Film) query.getSingleResult();
        
        System.out.println( film.getTitre() );
        
    }
}