<%@ page import="org.isga.project_jee.model.Subject" %>
<%@ page import="java.util.List" %>
<%@ page import="org.isga.project_jee.model.Score" %>
<%@ page import="org.isga.project_jee.services.SubjectService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<Score> scores = (List<Score>) request.getAttribute("scores");
    double averageScore = (double) request.getAttribute("averageScore");
    SubjectService subjectService = new SubjectService();
%>
<jsp:include page="../layouts/header.jsp"/>
<div class="relative overflow-x-auto shadow-md sm:rounded-lg">
    <div class="p-4 flex justify-between">
        <p id="averageScoreText">Average score: <%= averageScore%>
        </p>
        <div >
            <a href="${pageContext.request.contextPath}/scores?action=add&studentId=${param.studentId}"
               class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Add
                Score</a>
            <button onclick="downloadScoresAsPDF()"
                    class="text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2">
                Download PDF
            </button>

            <button onclick="downloadScoresAsExcel()"
                    class="text-white bg-yellow-500 hover:bg-yellow-600 focus:ring-4 focus:ring-yellow-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2">
                Download Excel
            </button>
        </div>
    </div>
    <table id="scoresTable" class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
        <tr>
            <th scope="col" class="px-6 py-3">ID</th>
            <th scope="col" class="px-6 py-3">Subject</th>
            <th scope="col" class="px-6 py-3">Score</th>
            <th scope="col" class="px-6 py-3"><span class="sr-only">Actions</span></th>
        </tr>
        </thead>
        <tbody>
        <%
            if (scores != null) {
                for (Score score : scores) {
        %>
        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 border-gray-200 hover:bg-gray-50 dark:hover:bg-gray-600">
            <td class="px-6 py-4"><%= score.getId() %>
            </td>
            <td class="px-6 py-4"><%= subjectService.getSubject(score.getSubjectId()).getName() %>
            </td>
            <td class="px-6 py-4"><%= score.getScore() %>
            </td>
            <td class="px-6 py-4">
                <a href="scores?action=edit&id=<%= score.getId() %>">Edit</a> |
                <a href="scores?action=delete&id=<%= score.getId() %>"
                   onclick="return confirm('Are you sure you want to delete this score?')">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>
<script>
    function downloadScoresAsPDF() {
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        const averageText = document.querySelector("#averageScoreText")?.innerText || "Average score: N/A";

        doc.setFontSize(16);
        doc.text("Scores Table", 14, 16);

        doc.setFontSize(12);
        doc.text(averageText, 14, 26);

        const rows = [];
        document.querySelectorAll("#scoresTable tbody tr").forEach(tr => {
            const cells = tr.querySelectorAll("td");
            if (cells.length >= 3) {
                rows.push([
                    cells[0].innerText,
                    cells[1].innerText,
                    cells[2].innerText
                ]);
            }
        });

        doc.autoTable({
            head: [['ID', 'Subject', 'Score']],
            body: rows,
            startY: 32,
            styles: { fontSize: 10 },
        });

        doc.save("scores.pdf");
    }


    function downloadScoresAsExcel() {
        const table = document.getElementById("scoresTable");
        const rows = [];

        const headers = Array.from(table.querySelectorAll("thead th"))
            .slice(0, 3)
            .map(th => th.innerText);
        rows.push(headers);

        table.querySelectorAll("tbody tr").forEach(tr => {
            const cells = Array.from(tr.querySelectorAll("td"))
                .slice(0, 3)
                .map(td => td.innerText);
            rows.push(cells);
        });

        const worksheet = XLSX.utils.aoa_to_sheet(rows);
        const workbook = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(workbook, worksheet, "Scores");
        XLSX.writeFile(workbook, "scores.xlsx");
    }

</script>
<jsp:include page="../layouts/footer.jsp"/>
