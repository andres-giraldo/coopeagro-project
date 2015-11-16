package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.Empleado;

@Remote
public interface CompraSessionBeanRemote {

    long getTotalComprasTiempo(int anno, int mes);

    List<Object[]> getTotalComprasAgricultor();

    List<Object[]> getTotalComprasEmpleado();

    double getPromedioCompras();

    void create(Compra compra);

    void edit(Compra compra);

    void destroy(int id);

    Compra findCompra(int id);

    List<Compra> findCompraEntities(int maxResults, int firstResult);

    List<Agricultor> getAllAgricultors();

    List<Empleado> getAllEmployees();
    
}
