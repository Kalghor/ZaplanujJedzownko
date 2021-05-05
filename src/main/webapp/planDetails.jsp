<%--
  Created by IntelliJ IDEA.
  User: kalghor
  Date: 22.04.2021
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>
<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <%@include file="leftMenu.jsp" %>


        <div class="m-4 p-3 width-medium ">
            <div class="dashboard-content border-dashed p-3 m-4">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">SZCZEGÓŁY PLANU</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <a href="javascript:history.back()"
                           class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Powrót</a>
                    </div>
                </div>

                <div class="schedules-content">
                    <div class="schedules-content-header">
                        <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Nazwa planu
                                </span>
                            <div class="col-sm-10">
                                <p class="schedules-text">${plan.name}</p>
                            </div>
                        </div>
                        <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Opis planu
                                </span>
                            <div class="col-sm-10">
                                <p class="schedules-text">
                                    ${plan.description}
                                </p>
                            </div>
                        </div>
                    </div>

                    <c:if test="${not empty map}">
                        <c:forEach items="${map}" var="m">
                            <table class="table">

                                <c:set var="list" scope="application" value="${m.value}"/>

                                <thead>
                                <tr class="d-flex">
                                    <th class="col-2"><c:out value="${list[0].dayName}"/></th>
                                    <th class="col-7"></th>
                                    <th class="col-1"></th>
                                    <th class="col-2"></th>
                                </tr>
                                </thead>
                                <tbody class="text-color-lighter">
                                <c:forEach items="${list}" var="l">
                                    <tr class="d-flex">
                                        <td class="col-2">
                                                ${l.mealName}
                                        </td>
                                        <td class="col-7">
                                                ${l.recipeName}
                                        </td>
                                        <td class="col-1 center">
                                            <a href="/app/recipe/plan/delete/alert?recipeId=${l.recipeId}&planId=${plan.id}">
                                                <button type="button" class="btn btn-danger rounded-0 text-light m-1">Usuń
                                                </button>
                                            </a>
                                        </td>
                                        <td class="col-2 center">
                                            <a href="/app/recipe/details?recipeId=${l.recipeId}">
                                                <button type="button" class="btn btn-info rounded-0 text-light m-1">Szczegóły
                                                </button>
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
                            <c:out value="Nie dodano jeszcze przepisu do tego planu"/>
                        </h6>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="footer.jsp" %>