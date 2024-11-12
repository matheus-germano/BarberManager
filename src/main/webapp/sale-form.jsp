<%@ page import="models.Sale" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Nova venda</title>
  <script>
    function calculateTotal() {
      let total = 0;
      document.querySelectorAll('input[name="serviceIds"]:checked').forEach((serviceCheckbox) => {
        total += parseFloat(serviceCheckbox.getAttribute("data-price"));
      });
      document.getElementById("total").value = total.toFixed(2);
    }

    function validateForm() {
      const checkboxes = document.querySelectorAll("input[name='serviceIds']");
      const anyChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

      if (!anyChecked) {
        alert("Selecione pelo menos um serviço disponível");
        return false;
      }
    }
  </script>
</head>
<body>
<h1>Nova venda</h1>

<form action="sales" method="post" onsubmit="return validateForm()">
  <input type="hidden" name="action" value="insert"/>

  <label>Profissional:</label>
  <select name="professionalDocument" required>
    <c:forEach var="professional" items="${professionals}">
      <option value="${professional.getDocument()}">${professional.getName()}</option>
    </c:forEach>
  </select><br/>

  <label>Método de pagamento:</label>
  <select name="paymentMethodId" required>
    <c:forEach var="paymentMethod" items="${paymentMethods}">
      <option value="${paymentMethod.getId()}">${paymentMethod.getName()}</option>
    </c:forEach>
  </select><br/>

  <label>Serviços:</label><br/>
  <c:forEach var="service" items="${services}">
    <input type="checkbox" name="serviceIds" value="${service.getId()}" data-price="${service.getPrice()}" onchange="calculateTotal()"/>
    ${service.getName()} - $${service.getPrice()}<br/>
  </c:forEach><br/>

  <label>Total:</label>
  <input type="text" id="total" name="total" readonly/><br/>

  <input type="submit" value="Cadastrar"/>
</form>
<a href="sales">Cancelar</a>
</body>
</html>