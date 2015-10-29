<%-- 
    Document   : Compras
    Created on : 28-oct-2015, 15:20:58
    Author     : YEISSON
--%>

<%@page import="org.coopeagro.servlets.CompraServlet"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/Head.jsp" />
        <!--<script type="text/javascript" src="js/ConsultasVentas.js"></script>-->
        <% List<Object[]> resultado = (List)CompraServlet.res; %>
        <title>Compras</title>
    </head>
    <body>
        <div class="container-fluid">
            <c:import url="/jsp/general/Alertas.jsp" />
            <div class="row">
                <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3" id="menu">
                    <c:import url="/jsp/general/Menu.jsp" />
                </div>
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9" id="contenido">
                    <form autocomplete="off" action="./Compra" method="POST" id="formularioCompras">
                        <h2>COMPRAS</h2>
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
                                    <% for(int i=1; i<= 12; i++){%> 
                                        <option value="<%= i %>"><%out.print(i);%></option> 
                                    <%}%>
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
                            <button class="btn btn-default" type="submit" name="accion" id="totalCompras" value="total">Total</button>
                            <button class="btn btn-default" type="submit" name="accion" id="promedioCompras" value="promedio">Promedio</button>
                            <button class="btn btn-default" type="submit" name="accion" id="filtrarAgricultor" value="agricultor">Agricultor</button>
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
