<%-- 
    Document   : users
    Created on : 20-Oct-2021, 10:29:44
    Author     : BritishWaldo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/userManager_style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>User Management Dashboard</title>
</head>

<body>
    <h1>User Management Dashboard</h1>
    
    <h2>${server_message}</h2>

    <table id='outerTable'>
        <c:if test="${showEditForm}">
            <c:out  value=   "<tr><th>Edit User</th></tr>"
                    escapeXml = "false">
            </c:out>
        </c:if>
        <c:if test="${showAddForm}">
            <c:out  value=   "<tr><th>Add User</th></tr>"
                    escapeXml = "false">
            </c:out>
        </c:if>
        <c:if test="${!showAddForm and !showEditForm}">
            <c:out  value=   ""
                    escapeXml = "false">
            </c:out>
        </c:if>
        <c:if test="${showAddForm}">
            <c:out value=   "
                                <tr>
                                    <td>
                                        <form action='' method='post' autocomplete='off'>
                                            <table id='newTable'>   
                                                <tr>
                                                    <td>
                                                        <input type='text' name='newEmail' id='newEmail' placeholder='E-mail Address' autocomplete='nope'>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <input type='text' name='newFirstName' id='newFirstName' placeholder='First Name' autocomplete='nope'>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <input type='text' name='newLastName' id='newLastName' placeholder='Last Name' autocomplete='nope'>
                                                    </td>
                                                </tr>                         
                                                <tr>
                                                    <td>
                                                        <input type='text' name='newPassword' id='newPassword' placeholder='Password' autocomplete='nope'>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <select name='newStatus' id='newStatus'>
                                                            <option value='Active'>Active</option>
                                                            <option value='Inactive'>Inactive</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <select name='newRole' id='newRole'>
                                                            <option value='Regular User' ${newRole.equals('Regular User') ? 'selected' : ''}>Standard User</option>
                                                            <option value='Company Administrator' ${newRole.equals('Company Administrator') ? 'selected' : ''}>Company Administrator</option>
                                                            <option value='System Administrator' ${newRole.equals('System Administrator') ? 'selected' : ''}>System Administrator</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <input type='submit' value='Add User' class='modifyButton'>
                                                        <input type='hidden' name='action' value='addUser'>
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </td>
                                </tr>
                                " 
                       escapeXml = "false">
                </c:out>
            </c:if>                
            <c:if test="${showEditForm}">
                <c:out value=   "
                                <tr>
                                    <td>
                                        <form action='' method='post' autocomplete='off'>
                                            <table id='editTable'>
                                                    <tr>
                                                            <td>
                                                                    <input type='text' name='editEmail' id='editEmail' value='${editEmail}' placeholder='${selectedUser.getEmail()}' autocomplete='nope'>
                                                            </td>
                                                    </tr>
                                                    <tr>
                                                            <td>
                                                                    <input type='text' name='editFirstName' id='editFirstName' value='${editFirstName}' placeholder='${selectedUser.getFirstName()}' autocomplete='nope'>
                                                            </td> 
                                                    </tr>
                                                    <tr>
                                                            <td>
                                                                    <input type='text' name='editLastName' id='editLastName' value='${editLastName}' placeholder='${selectedUser.getLastName()}' autocomplete='nope'>
                                                            </td>
                                                    </tr>                         
                                                    <tr>
                                                            <td>
                                                                    <select name='editStatus' id='editStatus'>
                                                                            <option value='Active' ${selectedUser.getActive() ? 'selected' : ''}>Active</option>
                                                                            <option value='Inactive' ${!selectedUser.getActive() ? 'selected' : ''}>Inactive</option>
                                                                    </select>
                                                            </td>
                                                    </tr>
                                                    <tr>
                                                            <td>
                                                                    <select name='editRole' id='editRole'>
                                                                            <option value='Regular User' ${selectedUser.getRole().getRoleId() == 2 ? 'selected' : ''}>Standard User</option>          
                                                                            <option value='Company Administrator' ${selectedUser.getRole().getRoleId() == 3 ? 'selected' : ''}>Company Administrator</option>
                                                                            <option value='System Administrator' ${selectedUser.getRole().getRoleId() == 1 ? 'selected' : ''}>System Administrator</option>
                                                                    </select>
                                                            </td>
                                                    </tr>
                                                    <tr>
                                                            <td>
                                                                    <input type='submit' name='resetUpdate' value='Reset' class='resetButton'>
                                                            </td>
                                                    </tr>
                                                    <tr>
                                                            <td>
                                                                    <input type='submit' value='Update User' class='modifyButton'>
                                                                    <input type='hidden' name='action' value='updateUser'>
                                                            </td>
                                                    </tr>
                                            </table>
                                        </form>
                                    </td>
                                </tr>
                                " 
                       escapeXml = "false">
                </c:out>
            </c:if>  
        <tr class="largeFont">    
            <th>Current Users</th> 
        </tr>
        <tr>    
            <td>
                <table id='displayTable'>
                    <tr class="headerRow">
                        <th>Active</th>
                        <th>E-mail Address</th>
                        <th>First Name</th>

                        <th>Last Name</th>
                        <th>User Role</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    <c:if test="${!showAddForm}">
                        <c:out value=   "
                                           <tr class='secondHeaderRow'>
                                                <td colspan='7'>
                                                        <form action='' method='post'>
                                                                <label for='displayAddForm' class='material-icons textAligned largeIcon'>person_add</label>
                                                                <input type='submit' id='addUserButton' name='displayAddForm' value ='Add User'>
                                                                <input type='hidden' name='action' value='displayAddForm'>
                                                        </form>
                                                </td>
                                            </tr>
                                        " 
                               escapeXml = "false">
                        </c:out>
                    </c:if>
                    <c:forEach items="${userList}" var="user"  varStatus="userCount">
                        <c:out value=   " 
                                            <tr>
                                                <form action='' method='post'>    
                                                    <td>
                                        " 
                                        escapeXml = "false">
                        </c:out>
                        <c:if test='${user.getActive()}'> 
                                                        <c:out value =  "
                                                                            <span class='material-icons green'>check</span>
                                                                        " 
                                                                        escapeXml = "false">
                                                        </c:out>
                        </c:if>
                        <c:if test='${!user.getActive()}'> 
                                                        <c:out value =  "
                                                                            <span class='material-icons red'>clear</span
                                                                        " 
                                                                        escapeXml = "false">
                                                        </c:out>
                        </c:if>
                        <c:out value =  "                               
                                                </td>
                                                <td>${user.getEmail()}</td>
                                                <td>${user.getFirstName()}</td>
                                                <td>${user.getLastName()}</td>
                                                <td>${user.getRole().getRoleName()}</td>
                                                <td>
                                                    <input type='submit' name='action' id='edit' class='material-icons' value='edit'>
                                                </td>
                                                <td>
                                                    <input type='submit' name='action' id='delete' class='material-icons red' value='delete'>
                                                </td>
                                                <input type='hidden' name='selectedUser' id='selectedUser' value='${user.getEmail()}'>
                                            </form>
                                        </tr>
                                    "
                            escapeXml = "false">
                        </c:out>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>
</body>
</html>