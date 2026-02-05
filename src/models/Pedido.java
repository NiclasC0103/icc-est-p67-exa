package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pedido {
    private String cliente;
    private String codigoPostal;
    private List<Integer> prioridades;

    public Pedido(String cliente, String codigoPostal, List<Integer> prioridades) {
        this.cliente = cliente;
        this.codigoPostal = codigoPostal;
        this.prioridades = prioridades;
    }

    public String getCliente() {
        return cliente;
    }


    public void setCliente(String cliente) {
        this.cliente = cliente;
    }


    public String getCodigoPostal() {
        return codigoPostal;
    }


    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }


    public List<Integer> getPrioridades() {
        return prioridades;
    }


    public void setPrioridades(List<Integer> prioridades) {
        this.prioridades = prioridades;
    }


    public int getZona() {
        String[] zona = this.codigoPostal.split("-");
        return Integer.parseInt(zona[1]);
    }

    public int getUrgencia() {
        int sumaPrioridades = 0;
        int divisor = 3;
        
        for (Integer prioridad : prioridades) {
            if (prioridad % divisor == 0) {
                sumaPrioridades += prioridad;
            }
        }

        String nombreSinEspacios = this.cliente.replace(" ", "");
        Set<Character> vocalesUnicas = new HashSet<>();
        for (char c : (nombreSinEspacios.toLowerCase()).toCharArray()) {
            if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'){
                vocalesUnicas.add(c);
            }
        }
        int cantidadVocales = vocalesUnicas.size();

        return sumaPrioridades * cantidadVocales;
    }

    @Override
    public String toString() {
        return "Pedido [cliente=" + cliente + ", codigoPostal=" + codigoPostal + ", prioridades=" + prioridades + "]";
    }

    

}
