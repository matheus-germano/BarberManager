<%@ page import="models.PaymentMethod" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Lista de métodos de pagamento</title>
</head>
<body>
<h1>Lista de métodos de pagamento</h1>
<a href="payment-methods?action=new">Adicionar novo método de pagamento</a>

<c:choose>
  <c:when test="${not empty listPaymentMethod}">
    <table border="1">
      <tr>
        <th>Nome</th>
        <th>Ativo</th>
        <th>Ações</th>
      </tr>
      <c:forEach var="paymentMethod" items="${listPaymentMethod}">
        <tr>
          <td>${paymentMethod.getName()}</td>
          <td>${paymentMethod.isActive() ? "Sim" : "Não"}</td>
          <td>
            <a href="payment-methods?action=edit&id=${paymentMethod.getId()}">Editar</a>
            <a href="payment-methods?action=delete&id=${paymentMethod.getId()}" onclick="return confirm('Are you sure?')">Deletar</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:when>
  <c:otherwise>
    <p>Nenhum método de pagamento encontrado.</p>
  </c:otherwise>
</c:choose>

<a href="index.jsp">Voltar ao menu</a>
</body>
</html>