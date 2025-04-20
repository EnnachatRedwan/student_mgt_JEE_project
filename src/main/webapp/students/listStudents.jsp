<%@ page import="org.isga.project_jee.model.Student" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<Student> students = (List<Student>) request.getAttribute("students");
%>
<jsp:include page="../layouts/header.jsp"/>
    <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
        <div class="p-4 flex justify-end">
            <a href="students/addStudent.jsp" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Add Student</a>
        </div>
        <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                <th scope="col" class="px-6 py-3">ID</th>
                <th scope="col" class="px-6 py-3">First Name</th>
                <th scope="col" class="px-6 py-3">Last Name</th>
                <th scope="col" class="px-6 py-3">Address</th>
                <th scope="col" class="px-6 py-3">Tel</th>
                <th scope="col" class="px-6 py-3"><span class="sr-only">Actions</span></th>
            </tr>
            </thead>
            <tbody>
            <%
                if (students != null) {
                    for (Student student : students) {
            %>
            <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 border-gray-200 hover:bg-gray-50 dark:hover:bg-gray-600">
                <td class="px-6 py-4"><%= student.getId() %>
                </td>
                <td class="px-6 py-4"><%= student.getFirstName() %>
                </td>
                <td class="px-6 py-4"><%= student.getLastName() %>
                </td>
                <td class="px-6 py-4"><%= student.getAddress() %>
                </td>
                <td class="px-6 py-4"><%= student.getTel() %>
                </td>
                <td class="px-6 py-4">
                    <a href="scores?studentId=<%= student.getId() %>">Scores</a> |
                    <a href="students?action=edit&id=<%= student.getId() %>">Edit</a> |
                    <a href="students?action=delete&id=<%= student.getId() %>"
                       onclick="return confirm('Are you sure you want to delete this student?')">Delete</a>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
<jsp:include page="../layouts/footer.jsp"/>
