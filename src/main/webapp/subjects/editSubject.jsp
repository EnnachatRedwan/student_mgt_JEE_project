<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.isga.project_jee.model.Subject" %>
<jsp:include page="../layouts/header.jsp"/>
<%
    Subject subject = (Subject) request.getAttribute("subject");
%>
<div class="panel">
    <form action="subjects" method="post" class="max-w-sm mx-auto">
        <input type="hidden" name="id" value="<%= subject.getId() %>">
        <div class="mb-5">
            <label for="firstName" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">First
                Name</label>
            <input type="text" id="name" name="name" value="<%= subject.getName() %>"
                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                   required/>
        </div>
        <button type="submit"
                class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
            Update Student
        </button>
    </form>
</div>
<jsp:include page="../layouts/footer.jsp"/>
