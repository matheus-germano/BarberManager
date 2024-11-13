<%@ page import="models.Sale" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Lista de vendas</title>

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