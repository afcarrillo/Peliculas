package mx.com.gm.peliculas.negocio;

import mx.com.gm.peliculas.datos.*;
import mx.com.gm.peliculas.domain.Pelicula;
import mx.com.gm.peliculas.excepciones.*;

public class CatalogoPeliculasImpl implements ICatalogoPeliculas {

    private final IAccesoDatos datos;

    public CatalogoPeliculasImpl() {
        this.datos = new AccesoDatosImpl();
    }

    @Override
    public void agregarPelicula(String nombrePelicula) {
        Pelicula pelicula = new Pelicula(nombrePelicula);
        boolean anexar = false;
        try {
            anexar = this.datos.existe(NOMBRE_RECURSO);
            this.datos.escribir(pelicula, NOMBRE_RECURSO, anexar);
        } catch (EscrituraDatosEx ex) {
            System.out.println("Error de acceso datos");
            ex.printStackTrace(System.out);
        } catch (AccesoDatosEx ex) {
            System.out.println("Error de escritura datos");
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public void listarPeliculas() {
        try {
            var peliculas = this.datos.listar(NOMBRE_RECURSO);
            if (peliculas.isEmpty()) {
                System.out.println("No hay ninguna pelicula en el catalogo");
            } else {
                peliculas.forEach(pelicula -> {
                    System.out.println("Pelicula: " + pelicula.toString());
                });
            }
        } catch (LecturaDatosEx ex) {
            System.out.println("Error de lectura datos");
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public void buscarPelicula(String buscar) {
        String resultado = null;
        try {
            resultado = this.datos.buscar(NOMBRE_RECURSO, buscar);
        } catch (LecturaDatosEx ex) {
            System.out.println("Error de lectura datos");
            ex.printStackTrace(System.out);
        }
        if (resultado == null){
            resultado = "No se ha encontrado la pelicula";
        }
        System.out.println(resultado);
    }

    @Override
    public void iniciarCatalogoPeliculas() {
        try {
            if (this.datos.existe(NOMBRE_RECURSO)) {
                this.datos.borrar(NOMBRE_RECURSO);
            }
            this.datos.crear(NOMBRE_RECURSO);
        } catch (AccesoDatosEx ex) {
            System.out.println("Error de acceso datos");
            ex.printStackTrace(System.out);
        }
    }
}
