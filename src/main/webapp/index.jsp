<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>

    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="w-full h-[100vh] flex flex-col justify-between">
    <main class="w-full max-w-[1200px] flex flex-col gap-4 p-4">
        <div class="w-full flex gap-4 justify-start items-center">
            <h1 class="text-3xl font-black"><%= "Barber Manager" %></h1>
            <img class="w-12 h-12" src="./resources/images/hairstyle.png" alt="Barber">
        </div>
        <nav class="flex gap-2">
            <a class="p-2 rounded-lg hover:bg-gray-200 transition-all" href="professionals">Profissionais</a>
            <a class="p-2 rounded-lg hover:bg-gray-200 transition-all" href="services">Serviços</a>
            <a class="p-2 rounded-lg hover:bg-gray-200 transition-all" href="payment-methods">Meios de pagamento</a>
            <a class="p-2 rounded-lg hover:bg-gray-200 transition-all" href="sales">Vendas</a>
        </nav>
    </main>
    <footer class="w-full h-12 flex items-center justify-center">
        <p>© 2024 Barber Manager. Todos os direitos Reservados</p>
    </footer>
</body>
</html>