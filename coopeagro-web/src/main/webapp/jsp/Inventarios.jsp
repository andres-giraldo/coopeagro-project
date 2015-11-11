<%-- 
    Document   : Inventarios
    Created on : 29-oct-2015, 9:38:48
    Author     : YEISSON
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/Head.jsp" />
        <script type="text/javascript" src="js/Inventarios.js"></script>
        <title>Inventario</title>
    </head>
    <body>
        <div class="container-fluid">
            <c:import url="/jsp/general/Alertas.jsp" />
            <div class="row">
                <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3" id="menu">
                    <c:import url="/jsp/general/Menu.jsp" />
                </div>
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9" id="contenido">
                    <form autocomplete="off" action="./Inventario" method="POST" id="formularioInventarios">
                        <c:import url="/jsp/general/Eliminacion.jsp" />
                        <h2>INVENTARIO</h2>
                        Los campos marcados con (*) son obligatorios
                        <br>
                        <br>
                        <div class="row">
                            <input type="hidden" id="idInventario" name="idInventario" value="${idInventario}"/>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="fecha">* Fecha:</label> 
                                <input class="form-control" type="text" id="fecha" name="fecha" value="${fecha}"/>
                                <script type="text/javascript">
                                    $(function() {
                                        $('#fecha').datetimepicker({format: 'dd/mm/yyyy', language:'es', weekStart:true, todayBtn:true, autoclose:true, todayHighlight:true, startView:2, minView:2});
                                    });
                                </script>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="producto">* Producto:</label> 
                                <select id="producto" class="form-control" name="producto">
                                    <option value="">Seleccione</option>
                                    <c:forEach items="${productos}" var = "p">
                                        <c:choose>
                                            <c:when test="${p.getId() == producto}">
                                                <option value="<c:out value='${p.getId()}'/>" selected><c:out value='${p.getNombre()}'/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="<c:out value='${p.getId()}'/>"><c:out value='${p.getNombre()}'/></option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="cantidadComprometida">* Cantidad comprometida:</label> 
                                <input class="form-control" type="text" id="cantidadComprometida" name="cantidadComprometida" value="${cantidadComprometida}" />
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="cantidadTotal">* Cantidad total:</label> 
                                <input class="form-control" type="text" id="cantidadTotal" name="cantidadTotal" value="${cantidadTotal}" />
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="disponibilidad"> Disponibilidad:</label> 
                                <input class="form-control" type="text" id="disponibilidad" name="disponibilidad" value="${disponibilidad}" readonly="true"/>
                            </div>
                        </div>
                        <div class="row form-group" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="consultar" value="listar" onclick="listarDatos();">Listar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="disponibilidadProducto" value="disponibilidad">Disponibilidad</button>
                            <button class="btn btn-default" type="button" name="accion" id="limpiar" value="limpiar" onclick="limpiarDatos();">Limpiar</button>
                        </div>
                        <div id="tablaInventarios"></div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
