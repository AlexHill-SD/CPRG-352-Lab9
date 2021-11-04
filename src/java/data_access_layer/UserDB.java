/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_access_layer;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Role;
import models.User;

/**
 *
 * @author BritishWaldo
 */
public class UserDB
{
    public List<User> getAll() throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        
        try
        {
           List<User> allRoleList = entityManager.createNamedQuery("User.findAll", User.class).getResultList();
           return allRoleList;
        }
        finally
        {
            entityManager.close();
        }
    }

    public User get(String inputUserEmail) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        
        try
        {
            User tempUser = entityManager.find(User.class, inputUserEmail);
            return tempUser;
        }
        finally
        {
            entityManager.close();
        }
    }

    public void insert(User inputUser) throws Exception
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            Role userRole = inputUser.getRole();
            
            //make sure the role object now contains the new user
            userRole.getUserList().add(inputUser);
            
            entityTransaction.begin();
            
            entityManager.persist(inputUser);
            
            //merge the role (aka update the role so that it contains the new user)
            entityManager.merge(userRole);
            
            entityTransaction.commit();
        }
        catch (Exception e)
        {
            entityTransaction.rollback();
        }
        finally
        {
            entityManager.close();
        }
    }

    public void update(String originalEmail, User inputUser) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            entityTransaction.begin();
            
            entityManager.merge(inputUser);
            
            entityTransaction.commit();
        }
        catch (Exception e)
        {
            entityTransaction.rollback();
        }
        finally
        {
            entityManager.close();
        }
    }

    public void delete(User inputUser) throws Exception 
    {        
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            Role userRole = inputUser.getRole();
            
            //make sure the role object no longer contains the new user
            userRole.getUserList().remove(inputUser);
            
            entityTransaction.begin();
            
            User confirmedUser = entityManager.merge(inputUser);
            
            entityManager.remove(confirmedUser);
            
            //merge the role (aka update the role so that it doesn't contain the new user)
            entityManager.merge(userRole);
            
            entityTransaction.commit();
        }
        catch (Exception e)
        {
            entityTransaction.rollback();
        }
        finally
        {
            entityManager.close();
        }
    }
}
