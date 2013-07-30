package br.com.mauriciotsilva.jogo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Util {

	public static int random() {
		return random(10);
	}

	public static int random(int base) {
		return (int) (Math.random() * base);
	}

	public static <T extends Comparable<T>> List<List<T>> groupList(List<T> list) {

		Collections.sort(list);
		return coreGroupList(list);
	}

	private static <T extends Comparable<T>> List<List<T>> coreGroupList(
			List<T> list) {

		int index = 0;
		int lastIndex = 0;
		List<Integer> inicio = new ArrayList<Integer>();
		List<Integer> termino = new ArrayList<Integer>();

		inicio.add(index);
		for (T type : list) {

			if (index != 0) {
				lastIndex = index - 1;

			}

			if (!type.equals(list.get(lastIndex))) {
				inicio.add(index);
				termino.add(index);
			}

			index++;
		}

		termino.add(list.size());
		return createGroupList(list, inicio, termino);
	}

	private static <T> List<List<T>> createGroupList(List<T> list,
			List<Integer> inicio, List<Integer> termino) {

		int index = 0;
		List<List<T>> listReturn = new ArrayList<List<T>>();
		for (Integer ini : inicio) {

			listReturn.add(new ArrayList<T>(list.subList(ini,
					termino.get(index))));

			index++;
		}

		return listReturn;
	}

}
