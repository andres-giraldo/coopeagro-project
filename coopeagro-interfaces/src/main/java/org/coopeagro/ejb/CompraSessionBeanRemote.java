package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.DetalleCompra;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.excepciones.InexistenteException;

@Remote
public interface CompraSessionBeanRemote {

    long getTotalComprasTiempo(int anno, int mes);

    List<Object[]> getTotalComprasAgricultor();

    List<Object[]> getTotalComprasEmpleado();

    double getPromedioCompras();

    void create(Compra compra);

    void edit(Compra compra)throws InexistenteException, Exception;

    void destroy(int id)throws InexistenteException;

    Compra findCompra(int id);

    List<Compra> findCompraEntities(int maxResults, int firstResult);

    List<Agricultor> getAllAgricultors();

    List<Empleado> getAllEmployees();

    Compra getMaxOrder();

    List<DetalleCompra> getDetalles(int compra);
    
}
