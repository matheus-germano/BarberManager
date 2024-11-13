<%@ page import="models.Sale" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Vendas</title>

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
<body class="w-full h-[100vh] flex flex-col justify-between">
  <main class="w-full max-w-[1200px] max-h-[calc(100vh-12rem)] flex flex-col gap-8 p-4 mx-auto">
    <header class="w-full flex flex-col gap-8">
      <nav class="w-full h-24 flex gap-2 justify-between items-center">
        <img class="w-12 h-12" src="./resources/images/hairstyle.png" alt="Barber">
        <ul class="w-fit flex gap-2">
          <li class="flex items-center justify-center"><a class="p-2 rounded-lg hover:bg-gray-200 transition-all" href="index.jsp">Home</a></li>
          <li class="flex items-center justify-center"><a class="p-2 rounded-lg hover:bg-gray-200 transition-all" href="professionals">Profissionais</a></li>
          <li class="flex items-center justify-center"><a class="p-2 rounded-lg hover:bg-gray-200 transition-all" href="services">Serviços</a></li>
          <li class="flex items-center justify-center"><a class="p-2 rounded-lg hover:bg-gray-200 transition-all" href="payment-methods">Meios de pagamento</a></li>
          <li class="flex items-center justify-center"><a class="p-2 rounded-lg hover:bg-gray-200 transition-all" href="sales">Vendas</a></li>
        </ul>
      </nav>
      <div class="w-full flex justify-between">
        <h1 class="text-3xl font-black">Vendas</h1>
        <a class="p-2 bg-amber-500 hover:bg-amber-600 text-white rounded transition-all" href="sales?action=new">Nova venda</a>
      </div>
    </header>
    <section class="w-full overflow-auto">
      <c:choose>
        <c:when test="${not empty listSale}">
          <table class="w-full table-auto border-collapse bg-white shadow-lg rounded-lg overflow-hidden">
            <thead>
            <tr class="bg-gray-200 text-left text-gray-700">
              <th class="p-4 border-b-2">Profissional</th>
              <th class="p-4 border-b-2">Método de pagamento</th>
              <th class="p-4 border-b-2">Serviços</th>
              <th class="p-4 border-b-2">Total</th>
              <th class="p-4 border-b-2">Vendido em</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="sale" items="${listSale}">
              <tr class="hover:bg-gray-50">
                <td class="p-4 border-b">${sale.getProfessional().getName()}</td>
                <td class="p-4 border-b">${sale.getPaymentMethod().getName()}</td>
                <td class="p-4 border-b">
                  <c:forEach var="service" items="${sale.getServices()}">
                    ${service.getName()}<br/>
                  </c:forEach>
                </td>
                <td class="p-4 border-b">${sale.getTotal()}</td>
                <td class="p-4 border-b">${sale.getSoldAt()}</td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
          <p class="text-gray-500">Nenhuma venda encontrada.</p>
        </c:otherwise>
      </c:choose>
    </section>
  </main>
  <footer class="w-full h-12 flex items-center justify-center">
    <p>© 2024 Barber Manager. Todos os direitos Reservados</p>
  </footer>
</body>
</html>