<%@ page import="org.isga.project_jee.model.Subject" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
%>
<jsp:include page="../layouts/header.jsp"/>
<div class="relative overflow-x-auto shadow-md sm:rounded-lg">
    <div class="p-4 flex justify-end">
        <a href="subjects/addSubject.jsp" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Add Subject</a>
    </div>
    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
        <tr>
            <th scope="col" class="px-6 py-3">ID</th>
            <th scope="col" class="px-6 py-3">Name</th>
            <th scope="col" class="px-6 py-3"><span class="sr-only">Actions</span></th>
        </tr>
        </thead>
        <tbody>
        <%
            if (subjects != null) {
                for (Subject subject : subjects) {
        %>
        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 border-gray-200 hover:bg-gray-50 dark:hover:bg-gray-600">
            <td class="px-6 py-4"><%= subject.getId() %>
            </td>
            <td class="px-6 py-4"><%= subject.getName() %>
            </td>
            <td class="px-6 py-4">
                <a href="subjects?action=edit&id=<%= subject.getId() %>">Edit</a> |
                <a href="subjects?action=delete&id=<%= subject.getId() %>"
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
