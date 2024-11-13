<%@ page import="models.Sale" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Nova venda</title>

  <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
  <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
  <script src="https://cdn.tailwindcss.com"></script>

  <script>
    function showToast(type, message) {
      let toastBackground = {
        "success": "linear-gradient(to right, #00b09b, #96c93d)",
        "error": "linear-gradient(to right, #ff0000, #ff6666)",
        "warning": "linear-gradient(to right, #ffdd00, #ffd700)"
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

    function calculateTotal() {
      let total = 0;
      document.querySelectorAll('input[name="serviceIds"]:checked').forEach((serviceCheckbox) => {
        total += parseFloat(serviceCheckbox.getAttribute("data-price"));
      });
      document.getElementById("total").value = total.toFixed(2);
    }

    function validateForm() {
      const professionalSelect = document.querySelector("select[name='professionalDocument']");
      const paymentMethodSelect = document.querySelector("select[name='paymentMethodId']");
      const checkboxes = document.querySelectorAll("input[name='serviceIds']");
      const anyChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

      if (professionalSelect.value.trim() === "") {
        showToast("warning", "Selecione um profissional disponível.");
        return false;
      }

      if (paymentMethodSelect.value.trim() === "") {
        showToast("warning", "Selecione um método de pagamento disponível.");
        return false;
      }

      if (!anyChecked) {
        showToast("warning", "Selecione pelo menos um serviço disponível.");
        return false;
      }
    }
  </script>
</head>
<body class="w-full h-[100vh] flex flex-col items-center justify-center p-4">
  <main class="w-full max-w-md rounded-lg p-6 space-y-6 bg-white shadow-md">
    <h1 class="text-2xl font-bold text-start text-gray-700">Nova venda</h1>

    <form action="sales" method="post" onsubmit="return validateForm()" class="space-y-4">
      <input type="hidden" name="action" value="insert"/>

      <div>
        <label class="block text-sm font-medium text-gray-600">Profissional:</label>
        <select name="professionalDocument" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-400">
          <c:forEach var="professional" items="${professionals}">
            <option value="${professional.getDocument()}">${professional.getName()}</option>
          </c:forEach>
        </select>
      </div>

      <div>
        <label class="block text-sm font-medium text-gray-600">Método de pagamento:</label>
        <select name="paymentMethodId" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-400">
          <c:forEach var="paymentMethod" items="${paymentMethods}">
            <option value="${paymentMethod.getId()}">${paymentMethod.getName()}</option>
          </c:forEach>
        </select>
      </div>

      <div>
        <label class="block text-sm font-medium text-gray-600 mb-2">Serviços:</label>
        <c:forEach var="service" items="${services}">
          <div class="flex items-center gap-2">
            <input type="checkbox" name="serviceIds" value="${service.getId()}" data-price="${service.getPrice()}" onchange="calculateTotal()" class="text-blue-600 focus:ring-blue-400 border-gray-300 rounded"/>
            <span class="text-gray-700">${service.getName()} - R$${service.getPrice()}</span>
          </div>
        </c:forEach>
      </div>

      <div>
        <label class="block text-sm font-medium text-gray-600">Total:</label>
        <input type="text" id="total" name="total" readonly class="w-full p-2 border border-gray-300 rounded focus:outline-none"/>
      </div>

      <div class="flex gap-4">
        <input type="submit" value="Cadastrar"
               class="w-full py-2 px-4 bg-amber-500 hover:bg-amber-600 text-white font-semibold rounded transition duration-200 cursor-pointer"/>
        <a href="sales"
           class="w-full py-2 px-4 bg-gray-300 hover:bg-gray-400 text-gray-700 font-semibold rounded text-center transition duration-200">Cancelar</a>
      </div>
    </form>
  </main>
</body>
</html>