// package services;

// import org.springframework.stereotype.Service;
// import model.Hecho;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// @Service
// public class HechoService {
//     private List<Hecho> hechos = new ArrayList<>(); // Simulando una base de datos

//     public Hecho crearHecho(Hecho hecho) {
//         hechos.add(hecho); // Agregar el hecho a la "base de datos"
//         return hecho;
//     }

//     public Optional<Hecho> obtenerHecho(String factID) {
//         // Buscar un hecho por su ID
//         return hechos.stream().filter(h -> h.getFactID().equals(factID)).findFirst();
//     }

//     public Optional<Hecho> actualizarHecho(String factID, Hecho hechoActualizado) {
//         for (int i = 0; i < hechos.size(); i++) {
//             Hecho hecho = hechos.get(i);
//             if (hecho.getFactID().equals(factID)) {
//                 hechos.set(i, hechoActualizado); // Actualizar el hecho en la "base de datos"
//                 return Optional.of(hechoActualizado);
//             }
//         }
//         return Optional.empty(); // Hecho no encontrado
//     }
// }
