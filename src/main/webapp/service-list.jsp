<%@ page import="models.Service" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Lista de serviços</title>
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