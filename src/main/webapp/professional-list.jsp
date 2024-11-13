<%@ page import="models.Professional" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Lista de profissionais</title>

  <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
  <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">

  <script>
    function showToast(type, message) {
      let toastBackground = {
        "success": "linear-gradient(to right, #00b09b, #96c93d)",
        "error": "linear-gradient(to right, #ff0000, #ff6666)"
      };

      Toastify({
        text: message,
        duration: 3000,
        gravity: "top",
        position: "right",
        stopOnFocus: true,
        style: {
          background: toastBackground[type] ?? "linear-gradient(to right, #333333, #b3b3b3)",
        }
      }).showToast();
    }

    <%
        String toastMessage = (String) session.getAttribute("toastMessage");
        String toastType = (String) session.getAttribute("toastType");
        if (toastMessage != null && toastType != null) {
    %>
    document.addEventListener("DOMContentLoaded", () => {
      showToast("<%= toastType %>", "<%= toastMessage %>");
    });
    <% session.removeAttribute("toastMessage"); %>
    <% session.removeAttribute("toastType"); %>
    <% } %>
  </script>
</head>
<body>
<h1>Lista de profissionais</h1>
<a href="professionals?action=new">Adicionar novo profissional</a>

<c:choose>
  <c:when test="${not empty listProfessional}">
    <table border="1">
      <tr>
        <th>ID</th>
        <th>Documento</th>
        <th>Nome</th>
        <th>Ativo</th>
        <th>Ações</th>
      </tr>
      <c:forEach var="professional" items="${listProfessional}">
        <tr>
          <td>${professional.getId()}</td>
          <td>${professional.getDocument()}</td>
          <td>${professional.getName()}</td>
          <td>${professional.isActive() ? "Sim" : "Não"}</td>
          <td>
            <a href="professionals?action=edit&id=${professional.getId()}">Editar</a>
            <a href="professionals?action=delete&id=${professional.getId()}" onclick="return confirm('Are you sure?')">Deletar</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:when>
  <c:otherwise>
    <p>Nenhum profissional encontrado.</p>
  </c:otherwise>
</c:choose>

<a href="index.jsp">Voltar ao menu</a>
</body>
</html>