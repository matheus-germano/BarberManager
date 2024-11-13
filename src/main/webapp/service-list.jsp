<%@ page import="models.Service" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Lista de serviços</title>

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
<h1>Lista de serviços</h1>
<a href="services?action=new">Adicionar novo serviço</a>

<c:choose>
  <c:when test="${not empty listService}">
    <table border="1">
      <tr>
        <th>Nome</th>
        <th>Preço</th>
        <th>Ativo</th>
        <th>Ações</th>
      </tr>
      <c:forEach var="service" items="${listService}">
        <tr>
          <td>${service.getName()}</td>
          <td>${service.getPrice()}</td>
          <td>${service.isActive() ? "Sim" : "Não"}</td>
          <td>
            <a href="services?action=edit&id=${service.getId()}">Editar</a>
            <a href="services?action=delete&id=${service.getId()}" onclick="return confirm('Are you sure?')">Deletar</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:when>
  <c:otherwise>
    <p>Nenhum serviço encontrado.</p>
  </c:otherwise>
</c:choose>

<a href="index.jsp">Voltar ao menu</a>
</body>
</html>