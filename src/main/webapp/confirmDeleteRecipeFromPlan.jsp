<%--
  Created by IntelliJ IDEA.
  User: kalghor
  Date: 23.04.2021
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="header.jsp" %>
<body>
<section class="dashboard-section">
    <div class="container pt-4 pb-4">
        <p>Czy na pewno chcesz usunąć przepis z planu?</p>
        <div>

            <a href="/app/recipe/plan/delete?recipeId=${recipeId}&planId=${planId}">
                <button type="submit" class="btn btn-danger rounded-0 text-light m-1">Usuń
                </button>
            </a>
            <a href="/app/plan/details?id=${planId}">
                <button type="submit" class="btn btn-info rounded-0 text-light m-1">Anuluj
                </button>
            </a>
        </div>
    </div>
</section>


</body>
<%@include file="footer.jsp" %>

</html>
