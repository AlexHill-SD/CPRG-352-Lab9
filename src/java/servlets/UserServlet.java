/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.RoleService;
import services.UserService;

/**
 *
 * @author BritishWaldo
 */
public class UserServlet extends HttpServlet
{
    private List<User> userList = null;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {   
        HttpSession session = request.getSession();
        try
        {
            this.userList = new UserService().getAll();

            session.setAttribute("userList", this.userList);
            request.setAttribute("userList", this.userList);
        }
        catch (Exception ex)
        {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/userManager.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        try
        {
            this.userList = new UserService().getAll();

            session.setAttribute("userList", this.userList);
        }
        catch (Exception ex)
        {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String action = request.getParameter("action");
        
        boolean changesMade = false;
        
        switch (action)
        {
            case "addUser":
                                        String inputEmail = request.getParameter("newEmail");
                                        String inputFirstName = request.getParameter("newFirstName");
                                        String inputLastName = request.getParameter("newLastName");
                                        String inputPassword = request.getParameter("newPassword");
                                        String rawActive = request.getParameter("newStatus");
                                        String rawUserRole = request.getParameter("newRole");
                                        
                                        ArrayList<String> errorChecking = new ArrayList<String>();
                                        
                                        errorChecking.add(inputEmail);
                                        errorChecking.add(inputFirstName);
                                        errorChecking.add(inputLastName);
                                        errorChecking.add(inputPassword);

                                        for(String input: errorChecking)
                                        {
                                            if (input.equals("") || input == null)
                                            {
                                                request.setAttribute("server_message", "All fields must be filled out in order to add a user to the database.<br>Please fill out all the add user fields.");
                                                
                                                request.setAttribute("showAddForm", true);
                                                
                                                getServletContext().getRequestDispatcher("/WEB-INF/userManager.jsp").forward(request, response);
                                                return;
                                            }
                                        }

                                        boolean inputActive = false;
                                        if (rawActive.equals("Active"))
                                        {
                                            inputActive = true;
                                        }

                                        int inputUserRole = -1;
                                        
                                        try
                                        {
                                            inputUserRole = new RoleService().roleIDLookup(rawUserRole);
                                        }
                                        catch (Exception ex)
                                        {
                                            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        
                                        try
                                        {
                                            new UserService().insert(inputEmail, inputActive, inputFirstName, inputLastName, inputPassword, inputUserRole);

                                            request.setAttribute("server_message", "User " + inputEmail + " was successfully added to the database.");

                                            changesMade = true;
                                        }
                                        catch (Exception ex)
                                        {
                                            if (ex.getMessage().contains("for key 'PRIMARY'"))
                                            {
                                                request.setAttribute("server_message", "User " + inputEmail + " already exists within the database");
                                            }
                                            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
            case "updateUser":
                                        if (session.getAttribute("selectedUser") == null)
                                        {
                                            request.setAttribute("server_message", "Use the edit button within the Current Users pane to update a user.");
                                        }
                                        else if (request.getParameter("resetUpdate") != null)
                                        {
                                            // DO NOTHING IF THE USER RESET THE FORM AND JUST RELOAD THE PAGE.
                                        }
                                        else
                                        {
                                            User userToUpdate = (User) session.getAttribute("selectedUser");

                                            String updatedEmail = request.getParameter("editEmail");
                                            String updatedFirstName = request.getParameter("editFirstName");
                                            String updatedLastName = request.getParameter("editLastName");
                                            String rawStatus = request.getParameter("editStatus");
                                            String rawRole = request.getParameter("editRole");

                                            if (updatedEmail.equals("") || updatedEmail == null)
                                            {
                                                updatedEmail = userToUpdate.getEmail();
                                            }

                                            if (updatedFirstName.equals("") || updatedFirstName == null)
                                            {
                                                updatedFirstName = userToUpdate.getFirstName();
                                            }

                                            if (updatedLastName.equals("") || updatedLastName == null)
                                            {
                                                updatedLastName = userToUpdate.getLastName();
                                            }

                                            boolean updatedActive = false;
                                            if (rawStatus.equals("Active"))
                                            {
                                                updatedActive = true;
                                            }

                                            int updatedRole = -1;
                                        
                                            try
                                            {
                                                updatedRole = new RoleService().roleIDLookup(rawRole);
                                            }
                                            catch (Exception ex)
                                            {
                                                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                                            }

                                            try
                                            {
                                                new UserService().update(userToUpdate.getEmail(), updatedEmail, updatedActive
                                                                            , updatedFirstName, updatedLastName, updatedRole);

                                                request.setAttribute("server_message", "User " + userToUpdate.getEmail() + " was successfully updated in the database.");

                                                changesMade = true;
                                            }
                                            catch (Exception ex)
                                            {
                                                if (ex.getMessage().contains("for key 'PRIMARY'"))
                                                {
                                                    request.setAttribute("server_message", "User " + updatedEmail + " already exists within the database, please choose another e-mail to assign to " + userToUpdate.getEmail());
                                                }
                                            
                                                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        break;
            case "edit":
                                        String selectedUser = request.getParameter("selectedUser");
                                        try
                                        {
                                            User userToEdit = new UserService().get(selectedUser);

                                            session.setAttribute("selectedUser", userToEdit);
                                            request.setAttribute("showEditForm", true);
                                        }
                                        catch (Exception ex)
                                        {
                                            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
            case "delete":
                                        String userToDelete = request.getParameter("selectedUser");
                                        try
                                        {
                                            new UserService().delete(userToDelete);

                                            request.setAttribute("server_message", "User " + userToDelete + " was successfully deleted from the database.");

                                            changesMade = true;
                                        }
                                        catch (Exception ex)
                                        {
                                            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
            case "displayAddForm":
                                        request.setAttribute("showAddForm", true);
                                        break;
        }

        if (changesMade)
        {
            try
            {
                this.userList = new UserService().getAll();

                session.setAttribute("userList", this.userList);
                
                getServletContext().getRequestDispatcher("/WEB-INF/userManager.jsp").forward(request, response);
            }
            catch (Exception ex)
            {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            getServletContext().getRequestDispatcher("/WEB-INF/userManager.jsp").forward(request, response);
            return;
        }
    }
}
