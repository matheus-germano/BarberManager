<%@ page import="models.Professional" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title><c:if test="${professional != null}">Editar profissional</c:if><c:if test="${professional == null}">Novo profissional</c:if></title>
</head>
<body>
<h1>
  <c:choose>
    <c:when test="${professional != null}">Editar profissional</c:when>
    <c:otherwise>Novo profissional</c:otherwise>
  </c:choose>
</h1>

<form action="professionals" method="post">
  <input type="hidden" name="action" value="${professional != null ? 'update' : 'insert'}"/>
  <input type="hidden" name="id" value="${professional != null ? professional.getId() : ''}"/>

  <label>Documento:</label>
  <input type="text" name="document" value="<c:choose><c:when test='${professional != null}'>${professional.getDocument()}</c:when></c:choose>" required/><br/>

  <label>Nome:</label>
  <input type="text" name="name" value="<c:choose><c:when test='${professional != null}'>${professional.getName()}</c:when></c:choose>" required/><br/>

  <label>Ativo:</label>
  <input type="checkbox" name="isActive" value="true" <c:if test="${professional != null && professional.isActive()}">checked</c:if> />

  <input type="submit" value="${professional != null ? 'Atualizar' : 'Cadastrar'}"/>
</form>
<a href="professionals">Cancelar</a>
</body>
</html>