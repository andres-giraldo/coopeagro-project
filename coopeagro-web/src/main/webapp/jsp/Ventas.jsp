<%-- 
    Document   : Ventas
    Created on : 28-oct-2015, 9:45:19
    Author     : YEISSON
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="org.coopeagro.servlets.VentaServlet"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/Head.jsp" />
        <!--<script type="text/javascript" src="js/ConsultasVentas.js"></script>-->
        <% List<Object[]> resultado = (List)VentaServlet.res; 
           Map<Integer, String> meses = new HashMap<Integer, String>();
           meses.put(1, "Enero");
           meses.put(2, "Febrero");
           meses.put(3, "Marzo");
           meses.put(4, "Abril");
           meses.put(5, "Mayo");
           meses.put(6, "Junio");
           meses.put(7, "Julio");
           meses.put(8, "Agosto");
           meses.put(9, "Septiembre");
           meses.put(10, "Octubre");
           meses.put(11, "Noviembre");
           meses.put(12, "Diciembre");
           request.setAttribute("meses", meses);
        %>
        <title>Ventas</title>
    </head>
    <body>
        <div class="container-fluid">
            <c:import url="/jsp/general/Alertas.jsp" />
            <div class="row">
                <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3" id="menu">
                    <c:import url="/jsp/general/Menu.jsp" />
                </div>
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9" id="contenido">
                    <form autocomplete="off" action="./Venta" method="POST" id="formularioVentas">
                        <h2>VENTAS</h2>
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="year"> Año:</label> 
                                <input class="form-control" type="text" id="year" name="year" value="${year}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="month"> Mes:</label> 
                                <select class="form-control" id="month" name="month">
                                    <option value="0">Seleccione</option>
                                    <c:forEach items="${meses}" var="mes"> 
                                        <option value="${mes.key}" <c:if test="${month == mes.key}">selected</c:if> >${mes.value}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="total"> Total:</label> 
                                <input class="form-control" type="text" id="total" name="total" value="${total}" readonly="true"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="promedio">$ Promedio:</label> 
                                <input class="form-control" type="text" id="promedio" name="promedio" value="${promedio}" readonly="true"/>
                            </div>
                        </div>
                        <div class="row form-group" align="center">
                            <button class="btn btn-default" type="submit" name="accion" id="totalVentas" value="total">Total</button>
                            <button class="btn btn-default" type="submit" name="accion" id="promedioVentas" value="promedio">Promedio</button>
                            <button class="btn btn-default" type="submit" name="accion" id="filtrarEmpleado" value="empleado">Empleado</button>
                            <button class="btn btn-default" type="submit" name="accion" id="filtrarCliente" value="cliente">Cliente</button>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Limpiar</button>
                        </div>
                    </form>
                    <table class="table table-striped table-hover table-condensed bordo-tablas">
                        <thead>
                            <th>Documento</th>
                            <th>Tipo documento</th>
                            <th>Nombre</th>
                            <th>Primer apellido</th>
                            <th>Segundo apellido</th>
                            <th>Total</th>
                        </thead>
                        <tbody>
                            <% if(resultado.size() > 0){
                                    for (Object[] r : resultado) { %>
                                        <tr>			
                                            <td><%out.println(r[0]);%></td>
                                            <td><%out.println(r[1]);%></td>
                                            <td><%out.println(r[2]);%></td>
                                            <td><%out.println(r[3]);%></td>
                                            <td><%out.println(r[4]);%></td>
                                            <td><%out.println(r[5]);%></td>
                                        </tr>
                                    <% }
                             }else{ %>
                                <tr>			
                                    <td colspan="6">No se encontraron registros</td>
                                </tr>
                            <%}%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
