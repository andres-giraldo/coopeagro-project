<%-- 
    Document   : DetalleCompras
    Created on : 24-nov-2015, 10:20:50
    Author     : YEISSON
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/Head.jsp" />
        <title>Detalle Compras</title>
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
                        <h2>DETALLE COMPRA</h2>
                        <br>
                        <br>
                        <div class="row">
                            <input type="hidden" id="idCompra" name="idCompra" value="${idCompra}"/>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="fecha">* Fecha de compra:</label> 
                                <input class="form-control" type="text" id="fecha" name="fecha" readonly="true" value="${fecha}" />
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="totalPedido">Total:</label> 
                                <div class="input-group">
                                    <span class="input-group-addon">$</span>
                                    <input class="form-control" type="text" id="totalPedido" name="totalPedido" readonly="true" value="${totalPedido}" />
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="agricultor">* Agricultor:</label> 
                                <input class="form-control" type="text" id="agricultor" name="agricultor" readonly="true" value="${agricultor}" />
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="empleado">* Empleado:</label> 
                                <input class="form-control" type="text" id="empleado" name="empleado" readonly="true" value="${empleado}" />
                            </div>
                        </div>
                        <div class="row form-group" align="center">
                            <button class="btn btn-default" type="submit" name="accion" id="regresarCompra" value="regresarCompra">Volver</button>
                        </div>
                    </form>
                    <table class="table table-striped table-hover table-condensed bordo-tablas">
                        <thead>
                            <th>Código</th>
                            <th>Nombre</th>
                            <th>Cantidad</th>
                            <th>Precio</th>
                        </thead>
                        <tbody>
                            <c:forEach items="${detalles}" var = "detalle">
                                <tr>
                                    <td><c:out value="${detalle.getProducto().getCodigo()}"/></td>
                                    <td><c:out value="${detalle.getProducto().getNombre()}"/></td>
                                    <td><c:out value="${detalle.getCantidad()}"/></td>
                                    <td>$<c:out value="${detalle.getPrecio()}"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
