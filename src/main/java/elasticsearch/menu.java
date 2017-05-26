package elasticsearch;

import java.util.Scanner;

public class menu {
	private elastic e;
	private boolean salir;
	private int op;
	private Scanner sc;

	public menu() {
		e = new elastic();
		salir = false;
		op = 0;
		sc = new Scanner(System.in);
	}

	public void ejecucion() {

		while (!salir) {
			System.out.println("Seleccione una opcion");
			System.out.println(".......................... \n" + ".  1 Mostrar letras de medicamentos disponibles \n"
					+ ".  2 Mostrar querys \n" + ".  3 Salir \n" + "..........................");
			try {
				op = sc.nextInt(); // Se le da a la variable op el valor del
									// teclado
				System.out.println("OPCION SELECCIONADA:" + op);
				switch (op) {
				case 1:// Mostrar eventos
					System.out.println("[A]  [Z]");
					System.out.println("Introduzca la letra de medicamentos que desea introducir a ES:");
					char letra = sc.next().charAt(0);
					if (letra == 'Z') {
						System.out.println("Introduce el nombre que quieres darle a tu índice");
						String index = sc.next();
						e.createIndex(index);
						System.out.println("Introduce el tipo que quieres darle a tu índice");
						String type = sc.next();
						e.postElasticSearchZ(type);
					} else if (letra == 'A') {
						System.out.println("Introduce el nombre que quieres darle a tu índice");
						String index = sc.next();
						e.createIndex(index);
						System.out.println("Introduce el tipo que quieres darle a tu índice");
						String type = sc.next();
						e.postElasticSearchA(type);
					} else {
						System.out.println("[Opción invalida]");
					}
					break;
				case 2:// Mostrar query
					e.mostrarQuery();
					break;
				case 3:// Salir del programa
					salir = true;
					break;
				default:// No valido
					System.out.println("Opcion invalida: marque un numero de 1 a 3");
					break;
				}
			} catch (Exception e) {
				System.out.println("Excepcion por opcion invalida: marque un numero de 1 a 3");
				sc.next();
			}
		}

	}

}
