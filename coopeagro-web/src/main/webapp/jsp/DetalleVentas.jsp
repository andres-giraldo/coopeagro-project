<%-- 
    Document   : DetalleVentas
    Created on : 28-nov-2015, 23:09:15
    Author     : YEISSON
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/Head.jsp" />
        <title>Detalle Ventas</title>
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
                        <h2>DETALLE VENTA</h2>
                        <br>
                        <br>
                        <div class="row">
                            <input type="hidden" id="idVenta" name="idVenta" value="${idVenta}"/>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="fecha">* Fecha de compra:</label> 
                                <input class="form-control" type="text" id="fecha" name="fecha" readonly="true" value="${fecha}" />
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="fechaEntrega">* Fecha de entrega:</label> 
                                <input class="form-control" type="text" id="fechaEntrega" name="fechaEntrega" readonly="true" value="${fechaEntrega}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="direccion">* Dirección:</label> 
                                <input class="form-control" type="text" id="direccion" name="direccion" readonly="true" value="${direccion}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="remitente">* Remitente:</label> 
                                <input class="form-control" type="text" id="direccion" name="remitente" readonly="true" value="${remitente}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="totalPedido">Total:</label> 
                                <div class="input-group">
                                    <span class="input-group-addon">$</span>
                                    <input class="form-control" type="text" id="totalPedido" name="totalPedido" readonly="true" value="${totalPedido}" />
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="cliente">* Cliente:</label> 
                                <input class="form-control" type="text" id="cliente" name="cliente" readonly="true" value="${cliente}" />
                            </div>
                        </div>
                        <div class="row form-group" align="center">
                            <button class="btn btn-default" type="submit" name="accion" id="regresarVenta" value="regresarVenta">Volver</button>
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
