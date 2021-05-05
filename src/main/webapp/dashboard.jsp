<%@ page import="java.util.ArrayList" %>
<%@ page import="pl.coderslab.model.SingleMeal" %><%--
  Created by IntelliJ IDEA.
  User: kalghor
  Date: 17.04.2021
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="appHeader.jsp" %>

<body>
<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <%@include file="leftMenu.jsp" %>
        <div class="m-4 p-4 width-medium">
            <div class="dashboard-header m-4">
                <div class="dashboard-menu">
                    <div class="menu-item border-dashed">
                        <a href="<c:url value = "/app/recipe/add"/>">
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj przepis</span>
                        </a>
                    </div>
                    <div class="menu-item border-dashed">
                        <a href="<c:url value = "/app/plan/add"/>">
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj plan</span>
                        </a>
                    </div>
                    <div class="menu-item border-dashed">
                        <a href="<c:url value = "/app/recipe/plan/add"/>">
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj przepis do planu</span>
                        </a>
                    </div>
                </div>

                <div class="dashboard-alerts">
                    <div class="alert-item alert-info">
                        <i class="fas icon-circle fa-info-circle"></i>
                        <span class="font-weight-bold">Liczba przepisów: ${numberOfRecipes}</span>
                    </div>
                    <div class="alert-item alert-light">
                        <i class="far icon-calendar fa-calendar-alt"></i>
                        <span class="font-weight-bold">Liczba planów: ${numberOfPlans}</span>
                    </div>
                </div>
            </div>
            <div class="m-4 p-4 border-dashed">
                <h2 class="dashboard-content-title">
                    <c:if test="${not empty planName}">
                        <span>Ostatnio dodany plan:</span> <c:out value="${planName}"/>
                    </c:if>
                    <c:if test="${empty planName}">
                        <span>Ostatnio dodany plan:</span> <c:out value="Nie dodano jeszcze planu"/>
                    </c:if>
                </h2>
                <c:if test="${not empty map}">
                    <c:forEach items="${map}" var="m">
                        <table class="table">

                            <c:set var="list" scope="application" value="${m.value}"/>

                            <thead>
                            <tr class="d-flex">
                                <th class="col-2"><c:out value="${list[0].dayName}"/></th>
                                <th class="col-8"></th>
                                <th class="col-2"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list}" var="l">
                                <tr class="d-flex">
                                    <td class="col-2">
                                            ${l.mealName}
                                    </td>
                                    <td class="col-8">
                                            ${l.recipeName}
                                    </td>
                                    <td class="col-2">
                                        <a href="/app/recipe/details?recipeId=${l.recipeId}">
                                            <button type="button" class="btn btn-primary rounded-0">Szczegóły</button>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:forEach>
                </c:if>
                <c:if test="${empty map}">
                <h6 class="dashboard-content-title">
                    <c:out value="Nie dodano jeszcze przepisu"/>
                </h6>
                </c:if>
            </div>
        </div>
    </div>
</section>
<%@include file="footer.jsp" %>