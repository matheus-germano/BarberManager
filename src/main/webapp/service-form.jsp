<%@ page import="models.Service" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title><c:if test="${service != null}">Edit Service</c:if><c:if test="${service == null}">Novo serviço</c:if></title>

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

    function validateForm() {
      const nameInput = document.querySelector("input[name='name']");
      const priceInput = document.querySelector("input[name='price']");

      if (nameInput.value.trim() === "") {
        showToast("warning", "Preencha o nome do serviço.");
        return false;
      }

      if (priceInput.value.trim() === "") {
        showToast("warning", "Preencha o valor do serviço.");
        return false;
      }
    }
  </script>
</head>
<body class="w-full h-[100vh] flex flex-col items-center justify-center p-4">
  <main class="w-full max-w-md rounded-lg p-6 space-y-6 bg-white shadow-md">
    <h1 class="text-2xl font-bold text-start text-gray-700">
      <c:choose>
        <c:when test="${service != null}">Editar serviço</c:when>
        <c:otherwise>Novo serviço</c:otherwise>
      </c:choose>
    </h1>

    <form action="services" method="post" onsubmit="return validateForm()" class="space-y-4">
      <input type="hidden" name="action" value="${service != null ? 'update' : 'insert'}"/>
      <input type="hidden" name="id" value="${service != null ? service.id : ''}"/>

      <div>
        <label class="block text-sm font-medium text-gray-600">Nome:</label>
        <input type="text" name="name" value="${service != null ? service.name : ''}"
               class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"/>
      </div>

      <div>
        <label class="block text-sm font-medium text-gray-600">Preço:</label>
        <input type="number" step="0.01" name="price" value="${service != null ? service.price : ''}"
               class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"/>
      </div>

      <div class="flex items-center gap-2">
        <input type="checkbox" name="isActive" value="true"
               class="text-blue-600 focus:ring-blue-400 border-gray-300 rounded"
               <c:if test="${service != null && service.isActive()}">checked</c:if> />
        <label class="text-sm text-gray-600">Ativo</label>
      </div>

      <div class="flex gap-4">
        <input type="submit" value="${service != null ? 'Atualizar' : 'Cadastrar'}"
               class="w-full py-2 px-4 bg-amber-500 hover:bg-amber-600 text-white font-semibold rounded transition duration-200 cursor-pointer"/>
        <a href="services"
           class="w-full py-2 px-4 bg-gray-300 hover:bg-gray-400 text-gray-700 font-semibold rounded text-center transition duration-200">Cancelar</a>
      </div>
    </form>
  </main>
</body>
</html>