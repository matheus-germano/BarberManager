<%@ page import="models.PaymentMethod" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title><c:if test="${paymentMethod != null}">Editar método de pagamento</c:if><c:if test="${paymentMethod == null}">Novo método de pagamento</c:if></title>
</head>
<body>
<h1>
  <c:choose>
    <c:when test="${paymentMethod != null}">Editar método de pagamento</c:when>
    <c:otherwise>Novo método de pagamento</c:otherwise>
  </c:choose>
</h1>

<form action="payment-methods" method="post">
  <input type="hidden" name="action" value="${paymentMethod != null ? 'update' : 'insert'}"/>
  <input type="hidden" name="id" value="${paymentMethod != null ? paymentMethod.getId() : ''}"/>

  <label>Name:</label>
  <input type="text" name="name" value="${paymentMethod != null ? paymentMethod.getName() : ''}" required/><br/>

  <label>Ativo:</label>
  <input type="checkbox" name="isActive" value="true" <c:if test="${paymentMethod != null && paymentMethod.isActive()}">checked</c:if> />

  <input type="submit" value="${paymentMethod != null ? 'Atualizar' : 'Cadastrar'}"/>
</form>
<a href="payment-methods">Cancelar</a>
</body>
</html>