<%@ page import="models.Sale" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Lista de vendas</title>
</head>
<body>
<h1>Lista de vendas</h1>
<a href="sales?action=new">Nova venda</a>

<c:choose>
  <c:when test="${not empty listSale}">
    <table border="1">
      <tr>
        <th>Profissional</th>
        <th>Método de pagamento</th>
        <th>Serviços</th>
        <th>Total</th>
        <th>Vendido em</th>
      </tr>
      <c:forEach var="sale" items="${listSale}">
        <tr>
          <td>${sale.getProfessional().getName()}</td>
          <td>${sale.getPaymentMethod().getName()}</td>
          <td>
            <c:forEach var="service" items="${sale.getServices()}">
              ${service.getName()}<br/>
            </c:forEach>
          </td>
          <td>${sale.getTotal()}</td>
          <td>${sale.getSoldAt()}</td>
        </tr>
      </c:forEach>
    </table>
  </c:when>
  <c:otherwise>
    <p>Nenhuma venda encontrada.</p>
  </c:otherwise>
</c:choose>

<a href="index.jsp">Voltar ao menu</a>
</body>
</html>