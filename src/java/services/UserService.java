/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import data_access_layer.RoleDB;
import data_access_layer.UserDB;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author BritishWaldo
 */
public class UserService
{
    public User get(String inputEmail) throws Exception 
    {
        UserDB userConnection = new UserDB();
        User tempUser = userConnection.get(inputEmail);
        return tempUser;
    }
    
    public List<User> getAll() throws Exception 
    {
        UserDB userConnection = new UserDB();
        List<User> allUsersList = userConnection.getAll();
        return allUsersList;
    }
    
    public void insert(String inputEmail, boolean inputActive, String inputFirstName, String inputLastName
                    , String inputPassword, int inputUserRole) throws Exception 
    {
        UserDB userConnection = new UserDB();
        User tempUser = new User(inputEmail, inputActive, inputFirstName, inputLastName, inputPassword);
        
        RoleDB roleConnection = new RoleDB();
        Role tempRole = roleConnection.get(inputUserRole);
        
        tempUser.setRole(tempRole);
        userConnection.insert(tempUser);
    }
    
    public void update(String originalEmail, String inputEmail, boolean inputActive, String inputFirstName, String inputLastName
                    , int inputUserRole) throws Exception 
    {
        UserDB userConnection = new UserDB();
        User tempUser = new User(inputEmail, inputActive, inputFirstName, inputLastName, "noPass");
        
        RoleDB roleConnection = new RoleDB();
        Role tempRole = roleConnection.get(inputUserRole);
        
        tempUser.setRole(tempRole);
        userConnection.update(originalEmail, tempUser);
    }
    
    public void delete(String inputEmail) throws Exception 
    {
        UserDB userConnection = new UserDB();
        User tempUser = new User(inputEmail);
        userConnection.delete(tempUser);
    }
}
