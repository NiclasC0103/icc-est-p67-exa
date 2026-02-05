package controllers;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import models.Pedido;

public class PedidoController {
    public Stack<Pedido> filtrarPorZona(List<Pedido> pedidos, int umbral) {
        Stack<Pedido> pilaResultado = new Stack<>();
        
        for (Pedido m : pedidos) {
            if (m.getZona() > umbral) {
                pilaResultado.push(m);
            }
        }
        return pilaResultado;
    }

    public TreeSet<Pedido> ordenarPorZona(Stack<Pedido> pila) {
        Comparator<Pedido> comparador = (p1, p2) -> {
            int cmpZona = Integer.compare(p2.getZona(), p1.getZona()); 
            
            if (cmpZona != 0) {
                return cmpZona;
            }
            
            return p1.getCliente().compareTo(p2.getCliente());
        };

        TreeSet<Pedido> setOrdenado = new TreeSet<>(comparador);
        setOrdenado.addAll(pila);
        
        return setOrdenado;
    }

    public TreeMap<Integer, Queue<Pedido>> agruparPorUrgencia(List<Pedido> pedidos) {
        TreeMap<Integer, Queue<Pedido>> mapa = new TreeMap<>();

        for (Pedido p : pedidos) {
            int urgencia = p.getUrgencia();

            if (!mapa.containsKey(urgencia)) {
                mapa.put(urgencia, new LinkedList<>());
            }
            
            mapa.get(urgencia).offer(p);
        }

        return mapa;
    }

    public Stack<Pedido> explotarGrupo(Map<Integer, Queue<Pedido>> mapa) {
        Queue<Pedido> grupoGanador = null;
        int maxUrgencia = -1;

        for (Map.Entry<Integer, Queue<Pedido>> entrada : mapa.entrySet()) {
            int urgenciaActual = entrada.getKey();
            Queue<Pedido> grupoActual = entrada.getValue();

            if (grupoGanador == null) {
                grupoGanador = grupoActual;
                maxUrgencia = urgenciaActual;
            } else {
                if (grupoActual.size() > grupoGanador.size()) {
                    grupoGanador = grupoActual;
                    maxUrgencia = urgenciaActual;
                }else if (grupoActual.size() == grupoGanador.size()) {
                    if (urgenciaActual > maxUrgencia) {
                        grupoGanador = grupoActual;
                        maxUrgencia = urgenciaActual;
                    }
                }
            }
        }

        Stack<Pedido> pilaFinal = new Stack<>();
        if (grupoGanador != null) {
            for (Pedido p : grupoGanador) {
                pilaFinal.push(p);
            }
        }
        return pilaFinal;
    }
}
