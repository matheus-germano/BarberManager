<%@ page import="models.Service" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title><c:if test="${service != null}">Edit Service</c:if><c:if test="${service == null}">Novo serviço</c:if></title>
</head>
<body>
<h1>
  <c:choose>
    <c:when test="${service != null}">Editar serviço</c:when>
    <c:otherwise>Novo serviço</c:otherwise>
  </c:choose>
</h1>

<form action="services" method="post">
  <input type="hidden" name="action" value="${service != null ? 'update' : 'insert'}"/>
  <input type="hidden" name="id" value="${service != null ? service.id : ''}"/>

  <label>Nome:</label>
  <input type="text" name="name" value="${service != null ? service.name : ''}" required/><br/>

  <label>Preço:</label>
  <input type="number" step="0.01" name="price" value="${service != null ? service.price : ''}" required/><br/>

  <label>Ativo:</label>
  <input type="checkbox" name="isActive" value="true" <c:if test="${service != null && service.isActive()}">checked</c:if> />

  <input type="submit" value="${service != null ? 'Atualizar' : 'Cadastrar'}"/>
</form>
<a href="services">Cancelar</a>
</body>
</html>